package com.lmm.test.esClusterDemo;

import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * 产品页面
 */
public class ProdPageEsWapper {

    private final static Logger LOGGER = LoggerFactory.getLogger(ProdPageEsWapper.class);
    private String indexName;

    private static ProdPageEsWapper singleton = null;

    public static ProdPageEsWapper get(){
        if(singleton == null){
            synchronized (ProdPageEsWapper.class){
                if(singleton == null){
                    singleton = new ProdPageEsWapper();
                }
            }
        }
        return singleton;
    }

    /** 私有构造器 */
    private ProdPageEsWapper(){
       this.init();
    }

    /** 初始化 */
    private void init(){
        this.indexName = EsClusterClient.get().getStrProp("prod.page.es.index.name");
        Integer shardNum = EsClusterClient.get().getIntegerProp("prod.page.es.shard.num");
        Integer replicaNum = EsClusterClient.get().getIntegerProp("prod.page.es.replica.num");
        try {
            //创建索引
            EsClusterClient.get().createIndex(this.indexName,shardNum,replicaNum);
            //创建产品类型
            ProdProductEs.get().createType(indexName);
            //创建商品类型
            SuppGoodsEs.get().createType(indexName);
            //创建队列表
            EsClusterClient.get().createType(this.indexName,ProdEsQueue.ES_TYPE_NAME,ProdEsQueue.getEsMappingJson());
        } catch (Exception e) {
            LOGGER.error(e.toString());
        }
    }

    /**
     * 产品对象ES
     */
    public static class ProdProductEs{

        public final static String TYPE_NAME = "prodProduct";
        private static class Builder {
            public static ProdProductEs singleton = new ProdProductEs();
        }
        private ProdProductEs(){}
        public static ProdProductEs get(){
            return ProdProductEs.Builder.singleton;
        }

        /**
         * 创建产品Type
         */
        private void createType(String indexName){
            EsClusterClient.get().createType(indexName,TYPE_NAME,getMapping());
        }

        /**
         * 产品在ES中的Json数据结构和映射
         * */
        private String getMapping(){
            StringBuffer mapping = new StringBuffer();
            mapping.append("{");
            mapping.append("    \"prodProduct\": ");
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

        /**
         * 生产产品在ES中的ID
         */
        public String getEsId(long productId, long categoryId){
            return productId+"_"+categoryId;
        }

        /**
         * 保存产品到ES
         */
        public IndexResponse index(String indexName, long productId, long categoryId, String document){
            return EsClusterClient.get()
                    .index(indexName,TYPE_NAME,getEsId(productId,categoryId),document);
        }

        /**
         * 搜索产品
         */
        public SearchResponse search(QueryBuilder queryBuilder,
                                     String sortField, SortOrder sortOrder,
                                     int curPage, int pageSize, String... indexNames){
            return EsClusterClient.get().search(TYPE_NAME,queryBuilder,sortField,sortOrder,curPage,pageSize,indexNames);
        }

    }

    /**
     * 商品ES
     */
    public static class SuppGoodsEs {

        public final static String TYPE_NAME = "suppGoods";
        private static class Builder {
            public static SuppGoodsEs singleton = new SuppGoodsEs();
        }
        private SuppGoodsEs() {}
        public static SuppGoodsEs get() {
            return SuppGoodsEs.Builder.singleton;
        }

        /**
         * 创建商品type
         */
        private void createType(String indexName) {
            EsClusterClient.get().createType(indexName, TYPE_NAME, getMapping());
        }

        /**
         * 商品在ES中的Json数据结构和映射
         * */
        private String getMapping(){
            StringBuffer mapping = new StringBuffer();
            mapping.append("{");
            mapping.append("    \"suppGoods\": ");//商品
            mapping.append("    {");
            mapping.append("        \"properties\": ");
            mapping.append("        {");
            mapping.append("            \"productId\": { \"type\": \"long\" },");
            mapping.append("            \"categoryId\": { \"type\": \"long\" },");
            mapping.append("            \"suppGoodsId\": { \"type\": \"long\" },");
            mapping.append("            \"managerId\": { \"type\": \"long\" },");
            mapping.append("            \"filiale\": { \"type\": \"string\", \"index\": \"not_analyzed\" },");
            mapping.append("            \"supplierId\": { \"type\": \"long\" },");
            //冗余字段,查询方便
            mapping.append("            \"prod_managerId\": { \"type\": \"long\" },");
            mapping.append("            \"pord_filiale\": { \"type\": \"string\", \"index\": \"not_analyzed\" }");
            mapping.append("        }");
            mapping.append("    }");
            mapping.append("}");

            return mapping.toString();
        }

        /**
         * 生成商品保存在ES中的ID
         */
        public String getEsId(long productId, long categoryId, long suppgoodsId){
            return productId+"_"+categoryId+"_"+suppgoodsId;
        }

        /**
         * 保存商品对象到ES中
         */
        public IndexResponse index(String indexName, long productId, long categoryId, long suppgoodsId, String document){
            return EsClusterClient.get()
                    .index(indexName, SuppGoodsEs.TYPE_NAME,getEsId(productId,categoryId,suppgoodsId),document);
        }

        /**
         * 搜索商品
         */
        public SearchResponse search(String indexName, QueryBuilder queryBuilder){
            return EsClusterClient.get().search(indexName,TYPE_NAME,queryBuilder);
        }

    }

    /**
     * 保存产品信息
     */
    public IndexResponse saveProdJson(long productId, long categoryId, String prodJson){
        IndexResponse indexResponse = null;
        if(StringUtils.isBlank(prodJson)){
            LOGGER.info("prodJson is null ... ");
            return  indexResponse;
        }
        indexResponse = ProdProductEs.get().index(indexName,productId,categoryId,prodJson);
        return indexResponse;
    }

    /**
     * 保存商品信息
     */
    public IndexResponse saveSuppGoodsJson(long productId,long categoryId, long suppGoodsId, String suppGoodsJson){
        IndexResponse indexResponse = null;
        if(StringUtils.isBlank(suppGoodsJson)){
            LOGGER.info("prodJson is null ... ");
            return  indexResponse;
        }
        indexResponse = SuppGoodsEs.get().index(indexName,productId,categoryId,suppGoodsId,suppGoodsJson);
        return indexResponse;
    }

/*    *//**
     * 查询产品信息
     *//*
    public ProdQueryVo selectProdProduct(ProdQueryPams prodQueryPams, Long curPage, Long pageSize) {
        ProdQueryVo prodQueryVo = new ProdQueryVo();

        //查询条件拼接
        BoolQueryBuilder prodProductQueryBuilder = QueryBuilders.boolQuery();

        if(prodQueryPams != null){

            //查询商品查询条件拼接
            BoolQueryBuilder suppGoodsQueryBuilder = QueryBuilders.boolQuery();
            JSONArray productIdList = new JSONArray();
            //商品子公司
            if(StringUtil.isNotEmptyString(prodQueryPams.getSubCompany())){
                suppGoodsQueryBuilder.should(QueryBuilders.termQuery("filiale", prodQueryPams.getSubCompany()));
                suppGoodsQueryBuilder.should(QueryBuilders.termQuery("prod_filiale", prodQueryPams.getSubCompany()));
            }
            //商品ID
            if(prodQueryPams.getGoodsId() != null){
                suppGoodsQueryBuilder.must(QueryBuilders.termQuery("suppGoodsId", prodQueryPams.getGoodsId()));
            }
            //商品产品经理
            if(prodQueryPams.getProductManagerId() != null){
                suppGoodsQueryBuilder.should(QueryBuilders.termQuery("managerId", prodQueryPams.getProductManagerId()));
                suppGoodsQueryBuilder.should(QueryBuilders.termQuery("prod_managerId", prodQueryPams.getProductManagerId()));
            }
            //商品供应商Id
            if(prodQueryPams.getSupplierId() != null){
                suppGoodsQueryBuilder.must(QueryBuilders.termQuery("supplierId", prodQueryPams.getSupplierId()));
            }
            if(suppGoodsQueryBuilder.hasClauses()){
                SearchResponse searchResponse = SuppGoodsEs.get().search(this.indexName,suppGoodsQueryBuilder);
                Long totalRows = searchResponse.getHits().getTotalHits();
                if(totalRows != null && totalRows > 0) {
                    for(SearchHit searchHit : searchResponse.getHits().getHits()) {
                        Long productId = Constants.obj2Long(searchHit.getSource().get("productId"));
                        if(productId == null)continue;
                        productIdList.add(productId);
                    }
                }
            }
            if(CollectionUtils.isNotEmpty(productIdList)){
                prodProductQueryBuilder.must(QueryBuilders.termsQuery("productId",productIdList));
            }


            //查询产品查询条件拼接
            //产品ID
            if(prodQueryPams.getProductId() != null){
                prodProductQueryBuilder.must(QueryBuilders.termQuery("productId", prodQueryPams.getProductId()));
            }
            //产品名称
            if(StringUtil.isNotEmptyString(prodQueryPams.getProductName())){
                prodProductQueryBuilder.must(QueryBuilders.wildcardQuery("productName", "*"+prodQueryPams.getProductName()+"*"));
            }
            //是否有效
            if(StringUtil.isNotEmptyString(prodQueryPams.getCancelFlag())){
                prodProductQueryBuilder.must(QueryBuilders.termQuery("cancelFlag", prodQueryPams.getCancelFlag()));
            }
            //是否可售
            if(StringUtil.isNotEmptyString(prodQueryPams.getSaleFlag())){
                prodProductQueryBuilder.must(QueryBuilders.termQuery("saleFlag", prodQueryPams.getSaleFlag()));
            }
            //目的地
            if(prodQueryPams.getDestId() != null){
                prodProductQueryBuilder.must(QueryBuilders.termQuery("prodDest.destId", prodQueryPams.getDestId()));
            }
            //是否废弃
            if(StringUtil.isNotEmptyString(prodQueryPams.getAbandonFlag())){
                prodProductQueryBuilder.must(QueryBuilders.termQuery("abandonFlag", prodQueryPams.getAbandonFlag()));
            }
            //子品类
            if(prodQueryPams.getSubCategoryId() != null){
                prodProductQueryBuilder.must(QueryBuilders.termQuery("subCategoryId", prodQueryPams.getSubCategoryId()));
            }
            //审核状态
            if(StringUtil.isNotEmptyString(prodQueryPams.getAuditStatus())
                    && !"all".equalsIgnoreCase(prodQueryPams.getAuditStatus())){
                prodProductQueryBuilder.must(QueryBuilders.termQuery("auditStatus", prodQueryPams.getAuditStatus()));
            }
            //行政区划ID
            if(prodQueryPams.getBizDistrictId() != null){
                prodProductQueryBuilder.must(QueryBuilders.termQuery("districtId", prodQueryPams.getBizDistrictId()));
            }
            //行政区划
            if(StringUtil.isNotEmptyString(prodQueryPams.getDistrictName())){
                prodProductQueryBuilder.must(QueryBuilders.wildcardQuery("prodDistrict.districtName", "*"+prodQueryPams.getDistrictName()+"*"));
            }
            //供应商产品名称
            if(StringUtil.isNotEmptyString(prodQueryPams.getSuppProductName())){
                prodProductQueryBuilder.must(QueryBuilders
                        .wildcardQuery("suppProductName", "*"+prodQueryPams.getSuppProductName()+"*"));
            }
            //品类ID
            if(prodQueryPams.getBizCategoryId() != null){
                prodProductQueryBuilder.must(QueryBuilders.termQuery("categoryId", prodQueryPams.getBizCategoryId()));
            }
            //酒店品牌
            if(prodQueryPams.getBrandId() != null){
                prodProductQueryBuilder.must(QueryBuilders.termQuery("prodBrand.prodBrandId", prodQueryPams.getBrandId()));
            }
            //品类为99的排除
            prodProductQueryBuilder.mustNot(QueryBuilders.termQuery("categoryId", 99));
        }
        SearchResponse searchResponse = EsClusterClient.get().search(ProdProductEs.TYPE_NAME,
                prodProductQueryBuilder,"productId", SortOrder.DESC,curPage.intValue(),pageSize.intValue(),this.indexName);
        if(searchResponse == null){
            LOGGER.info(" the searchResponse is null ");
            prodQueryVo.setTotal(0l);
            return prodQueryVo;
        }
        //设置总的数据条数
        Long totalRows = searchResponse.getHits().getTotalHits();
        //设置分页的数据
        if(totalRows != null && totalRows > 0) {
            prodQueryVo.setTotal(totalRows);
            List<ProdProduct> prodProductList = prodQueryVo.getProdProductList();
            for(SearchHit searchHit : searchResponse.getHits().getHits()) {
                ProdProduct prodProduct = new ProdProduct();
                prodProductList.add(prodProduct);
                Map<String, Object> prodQueryMap = searchHit.getSource();
                Long productId = Constants.obj2Long(prodQueryMap.get("productId"));
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
                prodProduct.setEbkSupplierGroupId(ebkSupplierGroupId);
            }
        }
        return prodQueryVo;
    }*/

/*    *//**
     * 查询行政区
     *//*
    public BizDistrict getBizDistrict(Long productId, Long categoryId, Long bizDistrictId){
        BizDistrict bizDistrict = null;
        if(productId != null && categoryId != null && bizDistrictId != null){
            //根据fu查询zi
            BoolQueryBuilder boolQueryBuilderParams = QueryBuilders.boolQuery();
            BoolQueryBuilder boolQueryBuilderParent = QueryBuilders.boolQuery();
            boolQueryBuilderParent.must(QueryBuilders.termQuery("productId",productId));
            boolQueryBuilderParent.must(QueryBuilders.termQuery("categoryId",categoryId));
            boolQueryBuilderParams.must(QueryBuilders.hasParentQuery(ProdProductEs.TYPE_NAME,boolQueryBuilderParent));
            boolQueryBuilderParams.must(QueryBuilders.termQuery("districtId",bizDistrictId));
            SearchResponse searchResponse = null;//EsClusterClient.get().search(this.indexName,ProdDistrictEs.TYPE_NAME,boolQueryBuilderParams);

            if(searchResponse == null){
                LOGGER.info(" the searchResponse is null ");
                return bizDistrict;
            }
            //设置总的数据条数
            Long totalRows = searchResponse.getHits().getTotalHits();
            //设置分页的数据
            if(totalRows != null && totalRows > 0) {
                SearchHit searchHit = searchResponse.getHits().getHits()[0];
                Integer districtId = (Integer) searchHit.getSource().get("districtId");
                String districtName = (String) searchHit.getSource().get("districtName");
                bizDistrict = new BizDistrict();
                bizDistrict.setDistrictId(categoryId == null ? null : categoryId.longValue());
                bizDistrict.setDistrictName(districtName);
            }
        }
        return bizDistrict;
    }*/

    /**
     * 将字符串转换成日期
     */
    private Date parse(String dateStr, String formate){
        Date date = null;
        if(StringUtils.isBlank(dateStr)){
            return date;
        }
        if(StringUtils.isBlank(formate)){
            formate = "yyyy-MM-dd HH:mm:ss SSS";
        }
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formate);
            date = simpleDateFormat.parse(dateStr);
        }catch (Exception e){
            LOGGER.error(e.toString());
        }
        return date;
    }

    /**
     * 记录消息日志
     */
    public IndexResponse saveProdEsQueueJson(String prodEsQueueJson){
        IndexResponse indexResponse = EsClusterClient.get().index(this.indexName, ProdEsQueue.ES_TYPE_NAME, prodEsQueueJson);
        return indexResponse;
    }

    /**
     * 记录消息日志
     */
    public IndexResponse saveProdEsQueueJson(String esId, String prodEsQueueJson){
        IndexResponse indexResponse = EsClusterClient.get().index(this.indexName, ProdEsQueue.ES_TYPE_NAME,esId,prodEsQueueJson);
        return indexResponse;
    }

    /**
     * 分页查询队列表
     */
    /*public ProdEsQueueVo searchProdEsQueue(ProdEsQueuePams prodEsQueuePams){
        ProdEsQueueVo prodEsQueueVo = new ProdEsQueueVo();
        BoolQueryBuilder boolQueryBuilderParams = QueryBuilders.boolQuery();
        //使用过滤器，过滤器非常快，非常快，不会打分，不会计算相关性，只判断是或者否
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        if(prodEsQueuePams != null){
            if(StringUtil.isNotEmptyString(prodEsQueuePams.getStartReceTime())
                    && StringUtil.isNotEmptyString(prodEsQueuePams.getEndReceTime())){
                boolQueryBuilder.filter(QueryBuilders.rangeQuery("receiveTime")
                        .from(prodEsQueuePams.getStartReceTime()).to(prodEsQueuePams.getEndReceTime()));
            }
            if(prodEsQueuePams.getProductId() != null){
                boolQueryBuilder.must(QueryBuilders.termQuery("productId",prodEsQueuePams.getProductId()));
            }
            if(prodEsQueuePams.getCategoryId() != null){
                boolQueryBuilder.must(QueryBuilders.termQuery("categoryId",prodEsQueuePams.getCategoryId()));
            }
            if(StringUtil.isNotEmptyString(prodEsQueuePams.getStatus())){
                boolQueryBuilder.must(QueryBuilders.termQuery("status",prodEsQueuePams.getOriginType()));
            }
            if(StringUtil.isNotEmptyString(prodEsQueuePams.getOriginType())){
                boolQueryBuilder.must(QueryBuilders.termQuery("originType",prodEsQueuePams.getStatus()));
            }
            if(boolQueryBuilder.hasClauses()){
                boolQueryBuilderParams.filter(boolQueryBuilder);
            }
        }
        SearchResponse searchResponse = EsClusterClient.get().search(ProdEsQueue.ES_TYPE_NAME,
                boolQueryBuilderParams,"receiveTime",prodEsQueuePams.getSortOrder(),prodEsQueuePams.getFrom(),prodEsQueuePams.getSize(),this.indexName);
        Integer total = searchResponse == null ? null : searchResponse.getHits().getHits().length;
        if(total != null && total > 0){
            prodEsQueueVo.setTotal(total.longValue());
            for(SearchHit searchHit : searchResponse.getHits().getHits()){
                ProdEsQueue prodEsQueue = new ProdEsQueue();
                String esId = searchHit.getId();
                prodEsQueue.setEsId(esId);
                Map<String,Object> searchHitMap = searchHit.getSource();
                prodEsQueue.fill(searchHitMap);
                prodEsQueueVo.getProdEsQueueList().add(prodEsQueue);
            }
        }
        return prodEsQueueVo;
    }*/

    /**
     * 删除队列表老的数据
     */
    public void deleteSubSysQueue() {

        //开始时间
        Calendar fromTime = Calendar.getInstance();
        fromTime.set(Calendar.YEAR, 2015);
        //结束时间
        Calendar toTime = Calendar.getInstance();
        toTime.set(Calendar.DAY_OF_MONTH, -3);
        toTime.set(Calendar.HOUR_OF_DAY,0);
        toTime.set(Calendar.MINUTE,0);
        toTime.set(Calendar.SECOND,0);
        toTime.set(Calendar.MILLISECOND,0);

        //使用过滤器，过滤器非常快，非常快，不会打分，不会计算相关性，只判断是或者否
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.filter(QueryBuilders.rangeQuery("receiveTime")
                .from(DateUtil.formatDate(fromTime.getTime(), "yyyy-MM-dd HH:mm:ss SSS"))
                .to(DateUtil.formatDate(toTime.getTime(), "yyyy-MM-dd HH:mm:ss SSS")));
        int curPage = 1;
        int pageSize = 1000;
        while (true){
            SearchResponse searchResponse = EsClusterClient.get().search(ProdEsQueue.ES_TYPE_NAME,
                    boolQueryBuilder,"receiveTime", SortOrder.ASC,curPage,pageSize,this.indexName);
            if(searchResponse == null)break;
            long total = searchResponse.getHits().getTotalHits();
            if(total <= 0)break;
            for (SearchHit searchHit : searchResponse.getHits().getHits()){
                String searchId = searchHit.getId();
                EsClusterClient.get().delete(this.indexName,ProdEsQueue.ES_TYPE_NAME,searchId);
            }
            curPage++;
        }
    }

    /**
     * 根据esId查询
     * @param esId
     * @return
     */
    public ProdEsQueue searchProdEsQueue(String esId){
        ProdEsQueue prodEsQueue = null;
        GetResponse getResponse = EsClusterClient.get().search(this.indexName,ProdEsQueue.ES_TYPE_NAME,esId);
        if(getResponse == null || StringUtils.isBlank(getResponse.getId())){
            return prodEsQueue;
        }
        prodEsQueue = new ProdEsQueue();
        prodEsQueue.setEsId(getResponse.getId());
        Map<String,Object> searchHitMap = getResponse.getSource();
        prodEsQueue.fill(searchHitMap);
        return prodEsQueue;
    }

}
