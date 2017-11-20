package com.lmm.test.esClusterDemo;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Map;

/**
 * 产品ES队列对象
 */
public class ProdEsQueue {

    private final Logger logger = LoggerFactory.getLogger(ProdEsQueue.class);

    final public static String ES_TYPE_NAME = "prodEsQueue";
    final private String PROD_DOC_POSTFIX = "_Gal";

    private String esId;

    private Long qId;
    private Long productId;
    private Long categoryId;
    private String prodDocu;
    private String originType;
    private Date createTime;
    private Date receiveTime;
    private String status;
    private String execuDesc;

    private String caseType;
    private Long caseId;

    /**
     * 消息来源
     */
    public enum ORIGIN_TYPE {
        VST("vst系统"),VISA("签证子系统"),SHIP("邮轮子系统"),LINE("线路子系统"),LOCAL_PALY("当地玩乐");
        private String cName;

        ORIGIN_TYPE(String cName) {
            this.cName = cName;
        }

    }

    /**
     * 消息状态
     */
    public enum STATUS {
        WAITING("待处理","0"),SUCESS("处理成功","1"),FAILURE("处理失败","2"),EXCEPTION("异常错误","3");
        private String cName;
        private String code;

        STATUS(String cName, String code) {
            this.cName = cName;
            this.code = code;
        }

        public String getcName() {
            return cName;
        }

        public String getCode() {
            return code;
        }
    }

    /**
     * 业务场景
     */
    public enum CASE_TYPE {
        ALL("所有场景"), PROD_PRODUCT("产品修改"), SUPP_GOODS("商品修改");
        private String cName;
        CASE_TYPE(String cName) {
            this.cName = cName;
        }
        public String getcName() {
            return cName;
        }
    }

    /**
     * 是否有错误
     */
    public boolean hasError(){
        if(productId != null && categoryId != null){
            return false;
        }
        logger.error("The productId | categoryId is empty ... ");
        return true;
    }

    /**
     * 该记录是否处理成功
     * @return
     */
    public boolean isSuccess(){
        return STATUS.SUCESS.getCode().equals(this.status);
    }

    /**
     * ES中Type的Mapping
     */
    public static String getEsMappingJson(){
        StringBuilder esMappingJson = new StringBuilder();
        esMappingJson.append("{");
        esMappingJson.append("    \""+ES_TYPE_NAME+"\": ");//行政区
        esMappingJson.append("    {");
        esMappingJson.append("        \"properties\": ");
        esMappingJson.append("        {");
        esMappingJson.append("            \"productId\": { \"type\": \"long\" },");
        esMappingJson.append("            \"categoryId\": { \"type\": \"long\" },");
        esMappingJson.append("            \"prodDocu\": { \"type\": \"string\", \"index\": \"no\" },");
        esMappingJson.append("            \"originType\": { \"type\": \"string\", \"index\": \"not_analyzed\" },");
        esMappingJson.append("            \"createTime\": { \"type\": \"date\", \"format\": \"yyyy-MM-dd HH:mm:ss SSS\"},");//13
        esMappingJson.append("            \"receiveTime\": { \"type\": \"date\", \"format\": \"yyyy-MM-dd HH:mm:ss SSS\"},");//13
        esMappingJson.append("            \"status\": { \"type\": \"string\", \"index\": \"not_analyzed\" },");
        esMappingJson.append("            \"execuDesc\": { \"type\": \"string\", \"index\": \"no\" },");
        esMappingJson.append("            \"caseType\": { \"type\": \"string\", \"index\": \"not_analyzed\" },");
        esMappingJson.append("            \"caseId\": { \"type\": \"long\" }");
        esMappingJson.append("        }");
        esMappingJson.append("    }");
        esMappingJson.append("}");
        return esMappingJson.toString();
    }

    /**
     * 填充字段
     */
    public void fill(Map<String,Object> prodEsQueueMap){
        this.setProductId(Constants.obj2Long(prodEsQueueMap.get("productId")));
        this.setCategoryId(Constants.obj2Long(prodEsQueueMap.get("categoryId")));
        this.setProdDocu((String) prodEsQueueMap.get("prodDocu"));
        this.setOriginType((String) prodEsQueueMap.get("originType"));
        String createTime = (String) prodEsQueueMap.get("createTime");
        this.setCreateTime(DateUtil.parse(createTime,null));
        String receiveTime = (String) prodEsQueueMap.get("receiveTime");
        this.setReceiveTime(DateUtil.parse(receiveTime,null));
        this.setStatus((String) prodEsQueueMap.get("status"));
        this.setExecuDesc((String) prodEsQueueMap.get("execuDesc"));
        this.setCaseType((String) prodEsQueueMap.get("caseType"));
        this.setCaseId(Constants.obj2Long(prodEsQueueMap.get("caseId")));
    }

    /**
     * 获取能Json，然后保存到ES
     * @return
     */
    public String getJson(){
        JSONObject prodEsQueueJson = new JSONObject();
        prodEsQueueJson.put("productId", this.productId);
        prodEsQueueJson.put("categoryId", this.categoryId);
        prodEsQueueJson.put("prodDocu", this.prodDocu+PROD_DOC_POSTFIX);
        prodEsQueueJson.put("originType", this.originType);
        prodEsQueueJson.put("createTime", this.createTime == null ? null : DateUtil.formatDate(this.createTime, "yyyy-MM-dd HH:mm:ss SSS"));
        prodEsQueueJson.put("receiveTime", this.receiveTime == null ? null : DateUtil.formatDate(this.receiveTime, "yyyy-MM-dd HH:mm:ss SSS"));
        prodEsQueueJson.put("status", this.status);
        prodEsQueueJson.put("execuDesc", this.execuDesc);
        prodEsQueueJson.put("caseType", this.caseType);
        prodEsQueueJson.put("caseId", this.caseId);
        return prodEsQueueJson.toString();
    }


    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("esId: " + esId);
        stringBuffer.append(", qId: " + qId);
        stringBuffer.append(", productId: " + productId);
        stringBuffer.append(", categoryId: " + categoryId);
        stringBuffer.append(", originType: " + originType);
        stringBuffer.append(", createTime: " + (createTime == null ? null : DateUtil.formatDate(createTime,"yyyy-MM-dd HH:mm:ss SSS")));
        stringBuffer.append(", receiveTime: " + (receiveTime == null ? null : DateUtil.formatDate(receiveTime,"yyyy-MM-dd HH:mm:ss SSS")));
        stringBuffer.append(", status: " + status);
        stringBuffer.append(", execuDesc: " + execuDesc);
        stringBuffer.append(", caseType: " + caseType);
        stringBuffer.append(", caseId: " + caseId);
        return stringBuffer.toString();
    }

    public String getProdDocuJson() {
        String tempProdDocu = prodDocu;
        if(StringUtils.isNotBlank(this.prodDocu)
                && prodDocu.lastIndexOf(PROD_DOC_POSTFIX) == prodDocu.length()-PROD_DOC_POSTFIX.length()){
            tempProdDocu = prodDocu.substring(0,prodDocu.lastIndexOf(PROD_DOC_POSTFIX));
        }
        return tempProdDocu;
    }

    public Logger getLogger() {
        return logger;
    }

    public String getCaseType() {
        return caseType;
    }

    public void setCaseType(String caseType) {
        this.caseType = caseType;
    }

    public Long getCaseId() {
        return caseId;
    }

    public void setCaseId(Long caseId) {
        this.caseId = caseId;
    }

    public Long getqId() {
        return qId;
    }

    public void setqId(Long qId) {
        this.qId = qId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getProdDocu() {
        return prodDocu;
    }

    public void setProdDocu(String prodDocu) {
        this.prodDocu = prodDocu;
    }

    public String getOriginType() {
        return originType;
    }

    public void setOriginType(String originType) {
        this.originType = originType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(Date receiveTime) {
        this.receiveTime = receiveTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getExecuDesc() {
        return execuDesc;
    }

    public void setExecuDesc(String execuDesc) {
        this.execuDesc = execuDesc;
    }

    public String getEsId() {
        return esId;
    }

    public void setEsId(String esId) {
        this.esId = esId;
    }

    /**
     * append执行记录
     * @param execuDesc
     */
    public void appendExecuDsc(String execuDesc){
        if(StringUtils.isNotBlank(execuDesc)) return;
        this.execuDesc = StringUtils.isNotBlank(this.execuDesc) ? execuDesc : this.execuDesc+" |<<----- "+execuDesc;
    }

}
