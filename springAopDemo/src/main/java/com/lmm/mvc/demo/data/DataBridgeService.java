package com.lmm.mvc.demo.data;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

@Service
@Lazy(value = false)
public class DataBridgeService implements InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(DataBridgeService.class);

    @Autowired
    private DataBridgeConfig config;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    //private DraftOrderService draftOrderService;

    @Autowired
    private ExecutorService executors;

    private static final int NUM_DB = 9;  // equals to physical db count
    private static final int NUM_TB = 100;
    private static final int REDIS_EXPIRE_TIME_SECOND = 60 * 24 * 3600;

    private volatile boolean initialized = false;
    private volatile boolean isClearProcessMode = false;
    private Date startTime;
    private Date endTime;


    private static final String TAG_DB_INDEX = "DB_INDEX";//同步到第几库
    private static final String TAG_TB_INDEX = "TB_INDEX";//同步到第几表
    private static final String TAG_HANDLING_ORDER_ID = "HANDLING_ORDER_ID";//开始处理的startOrderId
    private static final String TAG_LAST_FINISHED_ORDER_ID = "LAST_FINISHED_ORDER_ID";//成功处理后的lastOrderId
    private static final String TAG_HANDLED_COUNT = "HANDLED_COUNT";//当前同步库同步的总数据量
    private static final String TAG_CURRENT_TABLE_HANDLED_COUNT = "CURRENT_TABLE_HANDLED_COUNT";//当前同步库当前表同步的总数据量

    private AtomicLong totalCount = new AtomicLong(0);

    private List<HandleDataBridgeTask> dbHandleTasks = new ArrayList<HandleDataBridgeTask>();

    private Map<String, Long> dbTablesHandledOrderId = new ConcurrentHashMap<>();

    private static final String TOTAL_COUNT_REDIS_KEY = "DraftOrder.DataBridge.Total";//一共同步的总数据量 redis key

    @Scheduled(cron = "0 0/10 * * * ? ")
    public void executeTasks() {
        if (!initialized) {
            return;
        }

        for (HandleDataBridgeTask task : dbHandleTasks) {
            if (!task.isAlive && !task.finished) {
                logger.info("task is not alive, dbIndex {}", task.dbIndex);
                executors.execute(task);
            } else {
                logger.info("task is still alive, dbIndex {}", task.dbIndex);
            }
        }
    }

    @Scheduled(cron = "0/30 * *  * * ? ")
    public void printState() {
        logger.info("current state : ");
        logger.info("  total totalCount  : " + totalCount.get());

        logger.info("  initialized: " + initialized);
        logger.info("  isClearProcessMode: " + isClearProcessMode);
        logger.info("  config.resetProcess: " + config.getResetProcess());
        logger.info("  config.process: " + config.getProcess());
        logger.info("  config.pagesize: " + config.getQueryPageSize());
        logger.info("  config.startTime: " + config.getStartTime() + ", " + startTime);
        logger.info("  config.endTime: " + config.getEndTime() + ", " + endTime);


        for (HandleDataBridgeTask task : dbHandleTasks) {
            boolean isRunning = task.isRunning;
            Map<String, String> stateMap = task.getState();
            logger.info("  dbIndex : " + task.dbIndex + " isRunning : " + isRunning + " finished: " + task.finished + " state : " + stateMap);

        }

        logger.info("-------------------------------split----------------------");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        for (int i = 0; i < NUM_DB; i++) {
            dbHandleTasks.add(new HandleDataBridgeTask(i));
        }

        executors.submit(new InitializeTask());
    }


    private class HandleDataBridgeTask implements Runnable {

        private int dbIndex;

        private volatile boolean isRunning;
        private volatile boolean isAlive;
        private volatile boolean finished = false;

        private Map<String, String> stateMap = new HashMap<String, String>();

        public HandleDataBridgeTask(int dbIndex) {
            this.dbIndex = dbIndex;
            stateMap.put(TAG_DB_INDEX, String.valueOf(dbIndex));
        }

        @Override
        public void run() {
            logger.info("start to handle data bridge " + dbIndex);

            if (!isAlive) {
                try {
                    isAlive = true;
                    isRunning = true;
                    doDataBridge(dbIndex);
                } catch (Exception e) {
                    logger.info("Oooooooops!  something wrong with task:" + dbIndex);
                    logger.info(e.getMessage());
                } finally {
                    isAlive = false;
                }
            } else {
                logger.info("handle data bridge is still in processing " + dbIndex);
            }
            logger.info("end to handle data bridge " + dbIndex);
        }

        private Map<String, String> getState() {
            return Collections.unmodifiableMap(stateMap);
        }

        private void doDataBridge(int dbIndex) {
            for (int tbIndex = 0; tbIndex < NUM_TB; tbIndex++) {
                stateMap.put(TAG_CURRENT_TABLE_HANDLED_COUNT, "0");
                Long lastOrderId = getTableLastHandledOrderId(dbIndex, tbIndex);
                long startOrderId = lastOrderId == null ? 0 : lastOrderId;

                int pageSize = Integer.valueOf(config.getQueryPageSize());
                while (true) {
                    while (!config.isShouldProcess()) {
                        logger.info("should not process in this time" + dbIndex + "," + tbIndex + "," + startOrderId);
                        isRunning = false;
                        try {
                            TimeUnit.SECONDS.sleep(10);
                        } catch (Exception e) {
                            // ignore this one
                        }
                    }
                    isRunning = true;
                    boolean hasMoreData = handlePage(startOrderId, pageSize, dbIndex, tbIndex);
                    if (!hasMoreData) {
                        break;
                    }
                    startOrderId = getTableLastHandledOrderId(dbIndex, tbIndex);
                }

                if ((tbIndex % 50) == 0) {
                    try {
                        logger.info("busy time, waiting for 3 seconds per 50 tables");
                        TimeUnit.SECONDS.sleep(3);
                    } catch (Exception e) {
                    }
                }
            }
            finished = true;
        }

        private boolean handlePage(Long startOrderId, int pageSize, int dbIndex, int tbIndex) {
            logger.info("handling : " + dbIndex + "," + tbIndex + "," + startOrderId + " page size: " + pageSize);
            stateMap.put(TAG_TB_INDEX, String.valueOf(tbIndex));
            stateMap.put(TAG_HANDLING_ORDER_ID, String.valueOf(startOrderId));

            List sourceData = getSourceData(startOrderId, pageSize, dbIndex, tbIndex);//得到数据

            boolean notEmptyResult = CollectionUtils.isNotEmpty(sourceData);

            if (notEmptyResult) {

                if (isClearProcessMode) {

                    logger.info("del from new order sys for: " + dbIndex + "," + tbIndex + "," + startOrderId + " keys: " + sourceData.size());

                    //批量删除
                    deleteSourceData(sourceData);
                } else {

                    logger.info("to save to new order sys " + dbIndex + "," + tbIndex + "," + startOrderId + " keys: " + sourceData.size());

                    //批量保存批量保存
                    saveSourceData(sourceData);
                }

                int size = sourceData.size();
                long lastOrderId = 0;//sourceData.get(size - 1).getId();//sourceData.get(size - 1);

                totalCount.addAndGet(size);

                saveLastHandledOrderId(dbIndex, tbIndex, lastOrderId);

                saveState(lastOrderId, size);

                int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
                int insertPerPage = Integer.valueOf(config.getPerPageDay());
                if (hour >= 1 && hour <= 7) {
                    insertPerPage = Integer.valueOf(config.getPerPageNight());
                }

                int currentTableHandledCount = stateMap.containsKey(TAG_CURRENT_TABLE_HANDLED_COUNT) ? Integer.parseInt(stateMap.get(TAG_CURRENT_TABLE_HANDLED_COUNT)) : 0;
                if (currentTableHandledCount % (insertPerPage * pageSize) == 0) {
                    try {
                        logger.info("wait a moment per page, totalCount {}, dbIndex {}, tableName {} lastOrderId {}", totalCount.get(), dbIndex, tbIndex, lastOrderId);
                        TimeUnit.SECONDS.sleep(1);
                    } catch (Exception e) {
                    }
                }
            }

            stateMap.put(TAG_LAST_FINISHED_ORDER_ID, String.valueOf(startOrderId));
            return notEmptyResult;
        }

        private void saveState(long maxOrderId, int savedSize) {
            int handledCount = stateMap.containsKey(TAG_HANDLED_COUNT) ? Integer.parseInt(stateMap.get(TAG_HANDLED_COUNT)) : 0;
            handledCount += savedSize;
            stateMap.put(TAG_HANDLED_COUNT, String.valueOf(handledCount));

            int currentTableHandledCount = stateMap.containsKey(TAG_CURRENT_TABLE_HANDLED_COUNT) ? Integer.parseInt(stateMap.get(TAG_CURRENT_TABLE_HANDLED_COUNT)) : 0;
            currentTableHandledCount += savedSize;
            stateMap.put(TAG_CURRENT_TABLE_HANDLED_COUNT, String.valueOf(currentTableHandledCount));

            stateMap.put(TAG_LAST_FINISHED_ORDER_ID, String.valueOf(maxOrderId));
        }
    }

    private void saveSourceData(List sourceData) {
        //draftOrderService.saveNewDraftOrder(sourceData);
    }

    private void deleteSourceData(List sourceData) {
        //draftOrderService.deleteNewDraftOrder(sourceData);
    }

    private List getSourceData(Long startOrderId, int pageSize, int dbIndex, int tbIndex) {
        //return draftOrderService.getOldDraftOrders(dbIndex, tbIndex, startOrderId, startTime, endTime, pageSize);
    }


    private Long getTableLastHandledOrderId(int dbIndex, int tbIndex) {
        String key = getCacheKey(dbIndex, tbIndex);
        Long lastOrderId = dbTablesHandledOrderId.get(key);
        if (lastOrderId != null) {
            return lastOrderId;
        }

        String value = (String) redisTemplate.opsForValue().get(key);
        if (value != null) {
            try {
                return Long.valueOf(value);
            } catch (Exception e) {
                // swallow this one
            }
            return null;
        }
        return null;
    }

    private void saveLastHandledOrderId(int dbIndex, int tbIndex, long lastOrderId) {

        String key = getCacheKey(dbIndex, tbIndex);

        dbTablesHandledOrderId.put(key, lastOrderId);

        redisTemplate.opsForValue().set(key, String.valueOf(lastOrderId), REDIS_EXPIRE_TIME_SECOND, TimeUnit.SECONDS);
    }

    //此表最大的orderId
    private String getCacheKey(int dbIndex, int tbIndex) {
        return "DraftOrder.DataBridge.Db_b_." + dbIndex + ".Table." + tbIndex;
    }


    private class InitializeTask implements Runnable {
        @Override
        public void run() {
            while (!initialized) {
                try {
                    logger.info("config ready ? " + config.isReady());
                    if (config.isReady()) {
                        initialize();//从redis获取同步的总数

                        executors.submit(new SaveTotalCountTask());//保存同步的总数到redis

                        executeTasks();
                        return;
                    }
                    logger.info("wait for 5");
                    TimeUnit.SECONDS.sleep(5);
                } catch (Exception e) {
                }
            }
        }

        private void initialize() {
            logger.info("should reset process ? " + config.getResetProcess());
            if (config.shouldResetProcess()) {
                resetProcess();
            }

            try {
                String totalCountInRedis = (String) redisTemplate.opsForValue().get(TOTAL_COUNT_REDIS_KEY);
                if (totalCountInRedis != null) {
                    totalCount.set(Long.valueOf(totalCountInRedis));
                }
            } catch (Exception e) {
            }

            isClearProcessMode = config.shouldClearProcess();
            startTime = parseUpdateFrom(config.getStartTime());
            endTime = parseUpdateFrom(config.getEndTime());

            logger.info("Initialize done");
            initialized = true;
        }

        private Date parseUpdateFrom(String updateFromStr) {
            Date updateFromDate = null;
            if (updateFromStr != null && updateFromStr.length() > 0) {
                SimpleDateFormat dtf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    updateFromDate = dtf.parse(updateFromStr);
                    return updateFromDate;
                } catch (ParseException e) {
                    //e.printStackTrace(); // ignore this
                }

                dtf = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    updateFromDate = dtf.parse(updateFromStr);
                    return updateFromDate;
                } catch (ParseException e) {
                    //e.printStackTrace(); // ignore this
                }

                try {
                    long time = Long.parseLong(updateFromStr);
                    updateFromDate = new Date(time);
                    return updateFromDate;
                } catch (Exception e) {
                    //e.printStackTrace(); // ignore this
                }
            }
            return updateFromDate;
        }
    }

    private void resetProcess() {
        logger.info("reset process");
        for (int dbIndex = 0; dbIndex < NUM_DB; dbIndex++) {
            for (int tbIndex = 0; tbIndex < NUM_TB; tbIndex++) {
                String key = getCacheKey(dbIndex, tbIndex);
                redisTemplate.delete(key);
            }
        }
        redisTemplate.delete(TOTAL_COUNT_REDIS_KEY);
    }

    private class SaveTotalCountTask implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    long total = totalCount.get();
                    logger.info("save total count to redis, total {}", total);

                    redisTemplate.opsForValue().set(TOTAL_COUNT_REDIS_KEY, String.valueOf(total), REDIS_EXPIRE_TIME_SECOND, TimeUnit.SECONDS);
                    TimeUnit.SECONDS.sleep(5);
                } catch (Exception e) {
                }
            }
        }
    }
}
