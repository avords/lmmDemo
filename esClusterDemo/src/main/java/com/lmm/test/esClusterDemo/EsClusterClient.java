package com.lmm.test.esClusterDemo;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.ActionFuture;
import org.elasticsearch.action.admin.indices.exists.types.TypesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.types.TypesExistsResponse;
import org.elasticsearch.action.admin.indices.get.GetIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.get.GetIndexResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.cluster.node.DiscoveryNode;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

/**
 * es简单封装
 */
public class EsClusterClient {

    private final static Logger LOGGER = LoggerFactory.getLogger(EsClusterClient.class);

    private static EsClusterClient esClusterClient = null;
    private boolean esEnable = false;
    private TransportClient transportClient;
    private final static int BULK_SIZE = 4000;

    private Properties properties;
    private String hostAndPorts;
    private Integer shardNum = 3;
    private Integer replicaNum = 2;

    /**
     * 单例模式
     *
     * @return
     */
    public static EsClusterClient get() {
        if (esClusterClient == null) {
            synchronized (EsClusterClient.class) {
                if (esClusterClient == null) {
                    esClusterClient = new EsClusterClient();
                }
            }
        }
        return esClusterClient;
    }

    /**
     * 私有的构造函数
     */
    private EsClusterClient() {
        this.init();
        this.checkEs();
    }

    /**
     * 初始化
     */
    private void init() {
        LOGGER.info(" EsClusterClient init start .... ");
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("elasticsearch.properties");
            this.properties = new Properties();
            this.properties.load(inputStream);

            this.hostAndPorts = properties.getProperty("cluster.es.server");
            String tempEnable = properties.getProperty("cluster.es.enable");
            String clusterName = properties.getProperty("cluster.es.name");

            if (!"true".equalsIgnoreCase(tempEnable)
                    && !"ok".equalsIgnoreCase(tempEnable)
                    && !"y".equalsIgnoreCase(tempEnable)) {
                LOGGER.error(" The cluster.es.enable is not enable ... ");
                return;
            }
            if (StringUtils.isEmpty(clusterName)) {
                LOGGER.error(" The cluster.es.name is not enable ... ");
                return;
            }

            List<InetSocketTransportAddress> transportAddressList = this.getTransportAddresses();
            if (CollectionUtils.isEmpty(transportAddressList)) {
                LOGGER.error(" transportAddressList is null ... ");
                return;
            }

            Settings settings = Settings.settingsBuilder()
                    .put("cluster.name", clusterName)
                    .put("client.transport.sniff", true)
                    .build();
            transportClient = TransportClient.builder().settings(settings).build();
            for (InetSocketTransportAddress transportAddress : transportAddressList) {
                transportClient.addTransportAddress(transportAddress);
            }
        } catch (Exception e) {
            LOGGER.error(" EsClusterClient init failed ... ");
        }
        LOGGER.info(" EsClusterClient init end .... ");
    }

    /**
     * 拼接端口
     *
     * @return
     */
    private List<InetSocketTransportAddress> getTransportAddresses() {
        List<InetSocketTransportAddress> transportAddressList = new ArrayList<InetSocketTransportAddress>();
        if (StringUtils.isEmpty(this.hostAndPorts)) {
            LOGGER.error(" The hostAndPorts must not be empty ... ");
            return transportAddressList;
        }
        String[] hostAndPortArr = this.hostAndPorts.split(",");
        if (hostAndPortArr != null && hostAndPortArr.length > 0) {
            for (String hostAndPort : hostAndPortArr) {
                if (StringUtils.isEmpty(hostAndPort)) {
                    continue;
                }
                String[] hostAndPortPair = hostAndPort.split(":");
                if (hostAndPortPair == null || hostAndPortPair.length != 2) {
                    continue;
                }
                String host = hostAndPortPair[0];
                Integer port = Integer.valueOf(hostAndPortPair[1]);
                if (StringUtils.isEmpty(host) || port == null || port <= 0) {
                    continue;
                }
                try {
                    transportAddressList.add(new InetSocketTransportAddress(InetAddress.getByName(host), port));
                } catch (UnknownHostException e) {
                    LOGGER.error(e.toString());
                }
            }
        }
        return transportAddressList;
    }

    /**
     * 检查es客户端是否初始化成功,初始化一些参数.
     */
    private void checkEs() {
        try {
            List<DiscoveryNode> discoveryNodeList = this.transportClient.listedNodes();
            if (CollectionUtils.isEmpty(discoveryNodeList)) {
                LOGGER.error(" no Node discovered ... ");
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            for (DiscoveryNode discoveryNode : discoveryNodeList) {
                stringBuilder.append("{ nodeId: " + discoveryNode.getId());
                stringBuilder.append(", nodeName: " + discoveryNode.getName());
                stringBuilder.append(", hostName: " + discoveryNode.getHostName());
                stringBuilder.append(", hostAddress: " + discoveryNode.getHostAddress());
                stringBuilder.append(", version: " + discoveryNode.getVersion());
                stringBuilder.append(" }      ");
            }
            LOGGER.info("============  checkEs info start   ============");
            LOGGER.info(" discoveryNodeList ===> : " + stringBuilder.toString());
            LOGGER.info("============  checkEs info end   ============");
            this.esEnable = true;
        } catch (Exception e) {
            LOGGER.error(e.toString());
        }
    }

    public String getStrProp(String key) {
        if (StringUtils.isEmpty(key)) {
            return null;
        }
        return this.properties.getProperty(key);
    }

    public Integer getIntegerProp(String key) {
        if (StringUtils.isEmpty(key)) {
            return null;
        }
        String tempInteger = properties.getProperty(key);
        return str2Integer(tempInteger);
    }

    /**
     * 销毁对象
     */
    public void distroy() {
        if (this.transportClient != null) {
            this.transportClient.close();
            this.transportClient = null;
        }
        LOGGER.info(" distroy .... ");
    }

    /**
     * 创建  index  setting mapping
     */
    public void createIndex(String indexName, Integer shardNum, Integer replicaNum) throws IOException {

        /** 参数校验 */
        if (StringUtils.isEmpty(indexName)) {
            LOGGER.error(" The pams indexName | shardNum | replicaNum are empty ！！！");
            return;
        }

        LOGGER.info("indexName: " + indexName + ",shardNum: " + shardNum + ",replicaNum: " + replicaNum);

        /** 获取所有的索引 */
        IndicesAdminClient indicesAdminClient = transportClient.admin().indices();

        /** 检查index是否存在 */
        GetIndexRequestBuilder getIndexRequestBuilder = indicesAdminClient.prepareGetIndex();
        GetIndexResponse getIndexResponse = getIndexRequestBuilder.get();
        String[] indices = getIndexResponse.getIndices();
        Set<String> indexSet = new HashSet<String>();
        if (indices != null && indices.length > 0) {
            for (String index : indices) {
                indexSet.add(index);
            }
        }
        if (indexSet.contains(indexName)) {
            LOGGER.info(" The index: " + indexName + " is already exist!!!");
            return;
        }

        /** 如果不存在index，就创建一个 */
        indicesAdminClient.prepareCreate(indexName)
                .setSettings(Settings.builder()
                        .put("index.number_of_replicas", (replicaNum == null || replicaNum < 0) ? this.replicaNum : replicaNum)
                        .put("number_of_shards", (shardNum == null || shardNum <= 0) ? this.shardNum : shardNum)
                        .put("index.max_result_window", 100000000))
                .get();

    }

    /**
     * 创建type
     */
    public void createType(String indexName, String typeName, String mapping) {
        /** 参数校验 */
        if (StringUtils.isEmpty(indexName) || StringUtils.isEmpty(mapping)) {
            LOGGER.error(" The pams indexName | typeName | mapping are empty ！！！");
            return;
        }

        LOGGER.info("indexName: " + indexName + ",typeName: " + typeName + ",mapping: " + mapping);

        /** 获取所有的索引 */
        IndicesAdminClient indicesAdminClient = transportClient.admin().indices();
        TypesExistsRequest typesExistsRequest = new TypesExistsRequest(new String[]{indexName}, typeName);
        ActionFuture<TypesExistsResponse> actionFuture = indicesAdminClient.typesExists(typesExistsRequest);
        try {
            boolean isExists = actionFuture.get().isExists();
            if (isExists) {
                LOGGER.info(" The type " + typeName + " is existed ...");
            }
        } catch (Exception e) {
            LOGGER.error(e.toString());
        }
        indicesAdminClient.preparePutMapping(indexName).setType(typeName).setSource(mapping).get();
    }


    /**
     * 将字 String 转换成 Integer
     *
     * @param strVal
     * @return
     */
    private Integer str2Integer(String strVal) {
        Integer intVal = null;
        if (StringUtils.isEmpty(strVal)) {
            return intVal;
        }
        try {
            intVal = Integer.valueOf(strVal);
        } catch (Exception e) {
            LOGGER.error(e.toString());
        }
        return intVal;
    }

    /**
     * index一个文档到 ES 中
     */
    public IndexResponse index(String indexName, String typeName, String document) {
        if (!this.esEnable) {
            LOGGER.error(" The esEnable is false ... ");
            return null;
        }
        if (StringUtils.isEmpty(indexName) || StringUtils.isEmpty(typeName) || StringUtils.isEmpty(document)) {
            LOGGER.error(" The indexName | typeName | document is empty ... ");
            return null;
        }
        IndexResponse indexResponse = transportClient.prepareIndex(indexName, typeName).setSource(document).get();
        return indexResponse;
    }

    /**
     * 保存数据,不存在就新建一个,存在就更新一个
     */
    public IndexResponse index(String indexName, String typeName, String docId, String document) {
        if (!this.esEnable) {
            return null;
        }
        IndexRequest indexRequest = new IndexRequest();
        indexRequest.index(indexName);
        indexRequest.type(typeName);
        indexRequest.source(document);
        indexRequest.id(docId);
        IndexResponse indexResponse = this.transportClient.index(indexRequest).actionGet();
        return indexResponse;
    }

    /**
     * 批量插入数据
     *
     * @param indexName
     * @param typeName
     * @param documentList
     * @return
     */
    public void bulkOne(String indexName, String typeName, List<String> documentList) {
        if (!this.esEnable) {
            LOGGER.error(" The esEnable is false ... ");
            return;
        }
        if (StringUtils.isEmpty(indexName) || StringUtils.isEmpty(typeName) || CollectionUtils.isEmpty(documentList)) {
            LOGGER.error(" The indexName | typeName | documentList is empty ... ");
            return;
        }

        //计算
        int factDocSize = documentList.size();
        int modules = factDocSize / BULK_SIZE;
        int reminder = factDocSize % BULK_SIZE;
        if (reminder > 0) {
            modules += 1;
        }

        //拆分成多个小的 list
        List<List<String>> docList = new ArrayList<List<String>>();
        for (int i = 0; i < modules; i++) {
            int start = i * BULK_SIZE;
            int end = (i + 1) * BULK_SIZE > factDocSize ? factDocSize : (i + 1) * BULK_SIZE;
            docList.add(documentList.subList(start, end));
        }

        //通过循环， 批量插入
        LOGGER.info(" bulk start ....");
        for (List<String> tempDoc : docList) {
            BulkRequestBuilder bulkRequestBuilder = transportClient.prepareBulk();
            for (String document : tempDoc) {
                if (StringUtils.isNotEmpty(document)) {
                    bulkRequestBuilder.add(transportClient.prepareIndex(indexName, typeName).setSource(document));
                }
            }
            bulkRequestBuilder.get();
        }
        LOGGER.info(" bulk end ....");
    }

    /**
     * 带分页的搜索
     */
    public SearchResponse search(String typeName, QueryBuilder queryBuilder,
                                 String sortField, SortOrder sortOrder,
                                 int curPage, int pageSize, String... indexNames) {
        if (!this.esEnable) {
            LOGGER.error(" The esEnable is false ... ");
            return null;
        }
        SearchRequestBuilder searchRequestBuilder = transportClient.prepareSearch(indexNames).setTypes(typeName);
        //排序
        if (StringUtils.isNotEmpty(sortField)) {
            sortOrder = sortOrder == null ? SortOrder.ASC : sortOrder;
            searchRequestBuilder.addSort(SortBuilders.fieldSort(sortField).order(sortOrder).unmappedType("long"));
        }
        if (queryBuilder != null) {
            searchRequestBuilder.setQuery(queryBuilder);
        }
        if (curPage > 0 && pageSize > 0) {
            searchRequestBuilder.setFrom((curPage - 1) * pageSize);
            searchRequestBuilder.setSize(pageSize);
        }
        SearchResponse searchResponse = searchRequestBuilder.setSearchType(SearchType.DFS_QUERY_AND_FETCH).execute().actionGet();
        return searchResponse;
    }

    /**
     * 简单的搜索
     */
    public SearchResponse search(String indexName, String typeName, QueryBuilder queryBuilder) {
        if (!this.esEnable) {
            LOGGER.error(" The esEnable is false ... ");
            return null;
        }
        SearchRequestBuilder searchRequestBuilder = transportClient.prepareSearch(indexName).setTypes(typeName);
        if (queryBuilder != null) {
            searchRequestBuilder.setQuery(queryBuilder);
        }
        SearchResponse searchResponse = searchRequestBuilder.execute().actionGet();
        return searchResponse;
    }

    /**
     * 根据ESID进行搜索
     */
    public GetResponse search(String indexName, String typeName, String esId) {
        if (!this.esEnable) {
            LOGGER.error(" The esEnable is false ... ");
            return null;
        }
        GetResponse getResponse = null;
        if (StringUtils.isBlank(indexName)
                || StringUtils.isBlank(typeName)
                || StringUtils.isBlank(esId)) {
            LOGGER.info("The indexName | typeName | esId is emtpy ...");
            return getResponse;
        }
        getResponse = transportClient.prepareGet(indexName, typeName, esId).get();
        return getResponse;
    }

    /**
     * 删除文档
     */
    public DeleteResponse delete(String indexName, String typeName, String id) {
        DeleteResponse deleteResponse = null;
        if (!this.esEnable) {
            LOGGER.error(" The esEnable is false ... ");
            return deleteResponse;
        }
        if (StringUtils.isBlank(indexName)
                || StringUtils.isBlank(typeName)
                || StringUtils.isBlank(id)) {
            LOGGER.info("The indexName | typeName | id is emtpy ...");
            return deleteResponse;
        }
        DeleteRequest deleteRequest = new DeleteRequest(indexName);
        deleteRequest.type(typeName);
        deleteRequest.id(id);
        deleteResponse = transportClient.delete(deleteRequest).actionGet();
        return deleteResponse;
    }

}
