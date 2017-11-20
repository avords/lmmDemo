package com.lmm.test.Demo;

import com.alibaba.fastjson.JSON;
import com.lmm.test.esClusterDemo.EsClusterClient;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by Administrator on 2017/2/27.
 */
public class ESCRUD {
    private String indexName = "query_index";
    private String typeName="prodProduct";
    private final static Logger LOGGER = LoggerFactory.getLogger(ESCRUD.class);

    /**
     * 创建索引，相当于创建数据库
     */
    @Test
    public void createIndex() {
        this.indexName = EsClusterClient.get().getStrProp("prod.page.es.index.name");
        Integer shardNum = EsClusterClient.get().getIntegerProp("prod.page.es.shard.num");
        Integer replicaNum = EsClusterClient.get().getIntegerProp("prod.page.es.replica.num");
        try {
            //创建索引
            EsClusterClient.get().createIndex(this.indexName,shardNum,replicaNum);
        } catch (Exception e) {
            LOGGER.error(e.toString());
        }
    }

    /**
     * 创建类型，相当于创建数据库的表
     */
    @Test
    public void createType(){
        EsClusterClient.get().createType(indexName,typeName,getMapping());
    }
    
    /**
     * 创建文档，相当于插入记录,更新索引也可以用这个方法（指定id）
     */
    @Test
    public void createDocument(){
        Random random = new Random();
        int productId = random.nextInt(10000);
        int categoryId = random.nextInt(10000);
        String document = "{\n" +
                "  \"productId\": "+productId+",\n" +
                "  \"categoryId\": "+categoryId+",\n" +
                "  \"productName\": \"我是一个中国人，我爱中国，上帝创造了我们\",\n" +
                "  \"cancelFlag\": \"Y\",\n" +
                "  \"recommendLevel\": 123,\n" +
                "  \"saleFlag\": \"Y\",\n" +
                "  \"districtId\": 123,\n" +
                "  \"managerId\": 123,\n" +
                "  \"filiale\": \"子公司\",\n" +
                "  \"urlId\": \"123\",\n" +
                "  \"createUser\": \"123\",\n" +
                "  \"createTime\": \"2017-02-27 15:27:15 003\",\n" +
                "  \"updateUser\": \"123\",\n" +
                "  \"updateTime\": \"2017-02-27 15:27:15 003\",\n" +
                "  \"productType\": \"123\",\n" +
                "  \"packageType\": \"123\",\n" +
                "  \"auditStatus\": \"123\",\n" +
                "  \"sensitiveFlag\": \"123\",\n" +
                "  \"suppProductName\": \"123\",\n" +
                "  \"source\": \"123\",\n" +
                "  \"bu\": \"123\",\n" +
                "  \"attributionId\": 123,\n" +
                "  \"companyType\": \"123\",\n" +
                "  \"muiltDepatrueFlag\": \"123\",\n" +
                "  \"abandonFlag\": \"Y\",\n" +
                "  \"managerIdPerm\": \"123\",\n" +
                "  \"modelVersion\": 123,\n" +
                "  \"subCategoryId\": 123,\n" +
                "  \"travellerDelayFlag\": \"Y\",\n" +
                "  \"syncDetailFlag\": \"123\",\n" +
                "  \"ebkSupplierGroupId\": 123\n" +
                "}";
        EsClusterClient.get()
                .index(indexName,typeName,getEsId(productId,categoryId),document);
    }
    @Test
    public void batchInsertDocument(){
        for(int i=0;i<50;i++){
            createDocument();
        }
    }

    /**
     * 根据id删除一个文档
     */
    @Test
    public void deleteDocument(){
        EsClusterClient.get().delete(indexName,typeName,"123_123");
    }

    /**
     * 条件查询文档
     */
    @Test
    public void searchDocument(){
        BoolQueryBuilder prodProductQueryBuilder = QueryBuilders.boolQuery();
        prodProductQueryBuilder.must(QueryBuilders.wildcardQuery("productName", "*邮轮*"));
        //prodProductQueryBuilder.must(QueryBuilders.termQuery("cancelFlag", prodQueryPams.getCancelFlag()));

        SearchResponse searchResponse = EsClusterClient.get().search(typeName,
                prodProductQueryBuilder,"productId", SortOrder.DESC,1,15,this.indexName);
        if(searchResponse == null){
            LOGGER.info(" the searchResponse is null ");
        }
        //设置总的数据条数
        Long totalRows = searchResponse.getHits().getTotalHits();
        //设置分页的数据
        List<Map<String,Object>> list = new ArrayList<>();
        if(totalRows != null && totalRows > 0) {
            for(SearchHit searchHit : searchResponse.getHits().getHits()) {
                Map<String, Object> prodQueryMap = searchHit.getSource();
                list.add(prodQueryMap);
                /*Long productId = Constants.obj2Long(prodQueryMap.get("productId"));
                prodProduct.setProductId(productId);
                Long categoryId = Constants.obj2Long(prodQueryMap.get("categoryId"));
                prodProduct.setCategoryId(categoryId);
                prodProduct.setBizCategoryId(categoryId);
                String productName = (String) prodQueryMap.get("productName");
                prodProduct.setProductName(productName);
                String cancelFlag = (String) prodQueryMap.get("cancelFlag");
                prodProduct.setCancelFlag(cancelFlag);
                Long recommendLevel = Constants.obj2Long(prodQueryMap.get("recommendLevel"));
                prodProduct.setRecommendLevel(recommendLevel);
                String saleFlag = (String) prodQueryMap.get("saleFlag");
                prodProduct.setSaleFlag(saleFlag);
                Long districtId = Constants.obj2Long(prodQueryMap.get("districtId"));
                prodProduct.setBizDistrictId(districtId);
                Long managerId = Constants.obj2Long(prodQueryMap.get("managerId"));
                prodProduct.setManagerId(managerId);
                String filiale = (String) prodQueryMap.get("filiale");
                prodProduct.setFiliale(filiale);
                String urlId = (String) prodQueryMap.get("urlId");
                prodProduct.setUrlId(urlId);
                String createUser = (String) prodQueryMap.get("createUser");
                prodProduct.setCreateUser(createUser);
                String createTime = (String) prodQueryMap.get("createTime");
                prodProduct.setCreateTime(this.parse(createTime,null));
                String updateUser = (String) prodQueryMap.get("updateUser");
                prodProduct.setUpdateUser(updateUser);
                String updateTime = (String) prodQueryMap.get("updateTime");
                prodProduct.setUpdateTime(this.parse(updateTime,null));
                String productType = (String) prodQueryMap.get("productType");
                prodProduct.setProductType(productType);
                String packageType = (String) prodQueryMap.get("packageType");
                prodProduct.setPackageType(packageType);
                String auditStatus = (String) prodQueryMap.get("auditStatus");
                prodProduct.setAuditStatus(auditStatus);
                String sensitiveFlag = (String) prodQueryMap.get("sensitiveFlag");
                prodProduct.setSenisitiveFlag(sensitiveFlag);
                String suppProductName = (String) prodQueryMap.get("suppProductName");
                prodProduct.setSuppProductName(suppProductName);
                String source = (String) prodQueryMap.get("source");
                prodProduct.setSource(source);
                String bu = (String) prodQueryMap.get("bu");
                prodProduct.setBu(bu);
                Long attributionId = Constants.obj2Long(prodQueryMap.get("attributionId"));
                prodProduct.setAttributionId(attributionId);
                String companyType = (String) prodQueryMap.get("companyType");
                prodProduct.setCompanyType(companyType);
                String muiltDepatrueFlag = (String) prodQueryMap.get("muiltDepatrueFlag");
                prodProduct.setMuiltDpartureFlag(muiltDepatrueFlag);
                String abandonFlag = (String) prodQueryMap.get("abandonFlag");
                prodProduct.setAbandonFlag(abandonFlag);
                String managerIdPerm = (String) prodQueryMap.get("managerIdPerm");
                prodProduct.setManagerIdPerm(managerIdPerm);
                Long modelVersion = Constants.obj2Long(prodQueryMap.get("modelVersion"));
                prodProduct.setModelVersion(modelVersion == null ? null : modelVersion.doubleValue());
                Long subCategoryId = Constants.obj2Long(prodQueryMap.get("subCategoryId"));
                prodProduct.setSubCategoryId(subCategoryId);
                String travellerDelayFlag = (String) prodQueryMap.get("travellerDelayFlag");
                prodProduct.setTravellerDelayFlag(travellerDelayFlag);
                String syncDetailFlag = (String) prodQueryMap.get("syncDetailFlag");
                prodProduct.setSyncDetailFlag(syncDetailFlag);
                Long ebkSupplierGroupId = Constants.obj2Long(prodQueryMap.get("ebkSupplierGroupId"));
                prodProduct.setEbkSupplierGroupId(ebkSupplierGroupId);*/
            }
        }
        LOGGER.info(JSON.toJSONString(list));
    }
    /**
     * 生产产品在ES中的ID
     */
    public String getEsId(long productId,long categoryId){
        return productId+"_"+categoryId;
    }
    /**
     * 产品在ES中的Json数据结构和映射
     * */
    private String getMapping(){
        StringBuffer mapping = new StringBuffer();
        mapping.append("{");
        mapping.append("    \""+typeName+"\": ");
        mapping.append("    {");
        mapping.append("        \"properties\": ");
        mapping.append("        {");
        mapping.append("            \"productId\": { \"type\": \"long\" },"); //1
        mapping.append("            \"categoryId\": { \"type\": \"long\" },");//2
        mapping.append("            \"productName\": { \"type\": \"string\", \"index\": \"not_analyzed\" },");//3
        mapping.append("            \"cancelFlag\": { \"type\": \"string\", \"index\": \"not_analyzed\" },");//4
        mapping.append("            \"recommendLevel\": { \"type\": \"long\" },");//5
        mapping.append("            \"saleFlag\": { \"type\": \"string\", \"index\": \"not_analyzed\" },");//6
        mapping.append("            \"districtId\": { \"type\": \"long\" },");//7
        mapping.append("            \"managerId\": { \"type\": \"long\" },");//8
        mapping.append("            \"filiale\": { \"type\": \"string\", \"index\": \"not_analyzed\" },");//9
        mapping.append("            \"urlId\": { \"type\": \"string\", \"index\": \"not_analyzed\" },");//10
        mapping.append("            \"createUser\": { \"type\": \"string\", \"index\": \"not_analyzed\" },");//11
        mapping.append("            \"createTime\": { \"type\": \"date\", \"format\": \"yyyy-MM-dd HH:mm:ss SSS\"},");//13
        mapping.append("            \"updateUser\": { \"type\": \"string\", \"index\": \"not_analyzed\" },");//14
        mapping.append("            \"updateTime\": { \"type\": \"date\", \"format\": \"yyyy-MM-dd HH:mm:ss SSS\"},");//14
        mapping.append("            \"productType\": { \"type\": \"string\", \"index\": \"not_analyzed\" },");//15
        mapping.append("            \"packageType\": { \"type\": \"string\", \"index\": \"not_analyzed\" },");//16
        mapping.append("            \"auditStatus\": { \"type\": \"string\", \"index\": \"not_analyzed\" },");//17
        mapping.append("            \"sensitiveFlag\": { \"type\": \"string\", \"index\": \"not_analyzed\" },");//18
        mapping.append("            \"suppProductName\": { \"type\": \"string\", \"index\": \"not_analyzed\" },");//19
        mapping.append("            \"source\": { \"type\": \"string\", \"index\": \"not_analyzed\" },");//20
        mapping.append("            \"bu\": { \"type\": \"string\", \"index\": \"not_analyzed\" },");//21
        mapping.append("            \"attributionId\": { \"type\": \"long\" },");//22
        mapping.append("            \"companyType\": { \"type\": \"string\", \"index\": \"not_analyzed\" },");//23
        mapping.append("            \"muiltDepatrueFlag\": { \"type\": \"string\", \"index\": \"not_analyzed\" },");//24
        mapping.append("            \"abandonFlag\": { \"type\": \"string\", \"index\": \"not_analyzed\" },");//25
        mapping.append("            \"managerIdPerm\": { \"type\": \"string\", \"index\": \"not_analyzed\" },");//26
        mapping.append("            \"modelVersion\": { \"type\": \"long\" },");//27
        mapping.append("            \"subCategoryId\": { \"type\": \"long\" },");//28
        mapping.append("            \"travellerDelayFlag\": { \"type\": \"string\", \"index\": \"not_analyzed\"},");//29
        mapping.append("            \"syncDetailFlag\": { \"type\": \"string\", \"index\": \"not_analyzed\" },");//30
        mapping.append("            \"ebkSupplierGroupId\": { \"type\": \"long\" },");//31
        //目的地
        mapping.append("            \"prodDest\": ");
        mapping.append("            {");
        mapping.append("                \"type\": \"nested\",");
        mapping.append("                \"properties\": ");
        mapping.append("                {");
        mapping.append("                    \"destId\": { \"type\": \"long\" }");
        mapping.append("                }");
        mapping.append("            },");
        //酒店品牌
        mapping.append("            \"prodBrand\": ");
        mapping.append("            {");
        mapping.append("                \"type\": \"object\",");
        mapping.append("                \"properties\": ");
        mapping.append("                {");
        mapping.append("                    \"prodBrandId\": { \"type\": \"long\" }");
        mapping.append("                }");
        mapping.append("            },");
        //行政区
        mapping.append("            \"prodDistrict\": ");//行政区
        mapping.append("            {");
        mapping.append("                \"type\": \"object\",");
        mapping.append("                \"properties\": ");
        mapping.append("                {");
        mapping.append("                    \"districtId\": { \"type\": \"long\" },");
        mapping.append("                    \"districtName\": { \"type\": \"string\", \"index\": \"not_analyzed\" }");
        mapping.append("                }");
        mapping.append("            }");

        mapping.append("        }");
        mapping.append("    }");
        mapping.append("}");
        return mapping.toString();
    }
}
