package com.lmm.mvc.demo.data;

import com.ximalaya.configcenter.client.bean.XmlyconfFile;
import com.ximalaya.configcenter.client.bean.XmlyconfFileItem;
import org.springframework.stereotype.Component;


@Component
@XmlyconfFile(partion = "redis_databridge_config")
public class DataBridgeConfig {

    private static final String SHOULD_HANDLE = "1";

    @XmlyconfFileItem(name = "data.shouldprocess")
    private String process = "0";

    private static final String SHOULD_RESET_PROCESS = "1";
    @XmlyconfFileItem(name = "data.resetprocess")
    private String resetProcess;

    @XmlyconfFileItem(name = "data.process.db.querypagesize")
    private String queryPageSize = "1000";

    // page for redis
    @XmlyconfFileItem(name = "data.process.perpage.day")
    private String perPageDay = "30";

    @XmlyconfFileItem(name = "data.process.perpage.night")
    private String perPageNight = "200";
    
    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    public boolean isShouldProcess() {
        return SHOULD_HANDLE.equals(process);
    }

    public String getQueryPageSize() {
        return queryPageSize;
    }

    public void setQueryPageSize(String queryPageSize) {
        this.queryPageSize = queryPageSize;
    }


    public String getResetProcess() {
        return resetProcess;
    }

    public void setResetProcess(String resetProcess) {
        this.resetProcess = resetProcess;
    }

    public boolean shouldResetProcess() {
        return SHOULD_RESET_PROCESS.equals(resetProcess);
    }

    public boolean isReady() {
        return resetProcess != null;
    }

    public String getPerPageDay() {
        return perPageDay;
    }

    public void setPerPageDay(String perPageDay) {
        this.perPageDay = perPageDay;
    }

    public String getPerPageNight() {
        return perPageNight;
    }

    public void setPerPageNight(String perPageNight) {
        this.perPageNight = perPageNight;
    }
}
