package com.lmm.mvc.demo.model;

import java.io.Serializable;
import java.util.Date;

public class ProdProduct implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final double MODEL_VERSION_1D0 = 1.0;

	private Long categoryId;
	private Long productId;
	private Long attributionId;
	private String attributionName;

	private String productName;
	
	private String cancelFlag;

	private String saleFlag;

	private Long recommendLevel;

	private Long bizCategoryId;
	
	private String bizCategoryName;
	
    private Long subCategoryId;// 子品类ID

	private Long bizDistrictId;

	private String imageUrl;

	private String senisitiveFlag;

	// 产品经理ID
	private Long managerId;

	private String urlId;

	private String filiale;

	private String bu;
	// 数据源ebk或者vst
	private String source;

	// vo 使用
	private String managerName;

	// 出发地(行政区域)
	private String districtName;

	// 打包类型
	private String packageType;

	// 是否仅组合销售
	private String packageFlag;

	// 线路类别
	private String productType;
	// 线路类别(vo使用)
	private String productTypeName;

	private String toTraffic;
	
	private String backTraffic;

	// 销售渠道
	private String distributorIds;

	// 销售渠道集合
	private String[] disIds;
	
	private String backToChange;

	private Date createTime;

	private String createUser;

	private Date updateTime;

	private String updateUser;

	private String auditStatus;

	private String currentAuditStatus;

	private String abandonFlag;// 废弃标识

	private String categoryCombTicket;
	private String url;
	
	private String muiltDpartureFlag;

	private Double modelVersion;
	private String managerIdPerm;
    private String syncDetailFlag; 
	

	// 供应商产品名称
	private String suppProductName;

	private String hotelCombFlag = "Y";

	private Long dailyLowestPrice;
	
	private Long baseAdultQuantity = 0L;
	// 商品上儿童数
	private Long baseChildQuantity = 0L;
	private String district;
	private String urlQR;
	private String isKaixinlvxing;
	private String companyType;


	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Long getAttributionId() {
		return attributionId;
	}

	public void setAttributionId(Long attributionId) {
		this.attributionId = attributionId;
	}

	public String getAttributionName() {
		return attributionName;
	}

	public void setAttributionName(String attributionName) {
		this.attributionName = attributionName;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getCancelFlag() {
		return cancelFlag;
	}

	public void setCancelFlag(String cancelFlag) {
		this.cancelFlag = cancelFlag;
	}

	public String getSaleFlag() {
		return saleFlag;
	}

	public void setSaleFlag(String saleFlag) {
		this.saleFlag = saleFlag;
	}

	public Long getRecommendLevel() {
		return recommendLevel;
	}

	public void setRecommendLevel(Long recommendLevel) {
		this.recommendLevel = recommendLevel;
	}

	public Long getBizCategoryId() {
		return bizCategoryId;
	}

	public void setBizCategoryId(Long bizCategoryId) {
		this.bizCategoryId = bizCategoryId;
	}

	public String getBizCategoryName() {
		return bizCategoryName;
	}

	public void setBizCategoryName(String bizCategoryName) {
		this.bizCategoryName = bizCategoryName;
	}

	public Long getSubCategoryId() {
		return subCategoryId;
	}

	public void setSubCategoryId(Long subCategoryId) {
		this.subCategoryId = subCategoryId;
	}

	public Long getBizDistrictId() {
		return bizDistrictId;
	}

	public void setBizDistrictId(Long bizDistrictId) {
		this.bizDistrictId = bizDistrictId;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getSenisitiveFlag() {
		return senisitiveFlag;
	}

	public void setSenisitiveFlag(String senisitiveFlag) {
		this.senisitiveFlag = senisitiveFlag;
	}

	public Long getManagerId() {
		return managerId;
	}

	public void setManagerId(Long managerId) {
		this.managerId = managerId;
	}

	public String getUrlId() {
		return urlId;
	}

	public void setUrlId(String urlId) {
		this.urlId = urlId;
	}

	public String getFiliale() {
		return filiale;
	}

	public void setFiliale(String filiale) {
		this.filiale = filiale;
	}

	public String getBu() {
		return bu;
	}

	public void setBu(String bu) {
		this.bu = bu;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public String getPackageType() {
		return packageType;
	}

	public void setPackageType(String packageType) {
		this.packageType = packageType;
	}

	public String getPackageFlag() {
		return packageFlag;
	}

	public void setPackageFlag(String packageFlag) {
		this.packageFlag = packageFlag;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getProductTypeName() {
		return productTypeName;
	}

	public void setProductTypeName(String productTypeName) {
		this.productTypeName = productTypeName;
	}

	public String getToTraffic() {
		return toTraffic;
	}

	public void setToTraffic(String toTraffic) {
		this.toTraffic = toTraffic;
	}

	public String getBackTraffic() {
		return backTraffic;
	}

	public void setBackTraffic(String backTraffic) {
		this.backTraffic = backTraffic;
	}

	public String getDistributorIds() {
		return distributorIds;
	}

	public void setDistributorIds(String distributorIds) {
		this.distributorIds = distributorIds;
	}

	public String[] getDisIds() {
		return disIds;
	}

	public void setDisIds(String[] disIds) {
		this.disIds = disIds;
	}

	public String getBackToChange() {
		return backToChange;
	}

	public void setBackToChange(String backToChange) {
		this.backToChange = backToChange;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public String getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}

	public String getCurrentAuditStatus() {
		return currentAuditStatus;
	}

	public void setCurrentAuditStatus(String currentAuditStatus) {
		this.currentAuditStatus = currentAuditStatus;
	}

	public String getAbandonFlag() {
		return abandonFlag;
	}

	public void setAbandonFlag(String abandonFlag) {
		this.abandonFlag = abandonFlag;
	}

	public String getCategoryCombTicket() {
		return categoryCombTicket;
	}

	public void setCategoryCombTicket(String categoryCombTicket) {
		this.categoryCombTicket = categoryCombTicket;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMuiltDpartureFlag() {
		return muiltDpartureFlag;
	}

	public void setMuiltDpartureFlag(String muiltDpartureFlag) {
		this.muiltDpartureFlag = muiltDpartureFlag;
	}

	public Double getModelVersion() {
		return modelVersion;
	}

	public void setModelVersion(Double modelVersion) {
		this.modelVersion = modelVersion;
	}

	public String getManagerIdPerm() {
		return managerIdPerm;
	}

	public void setManagerIdPerm(String managerIdPerm) {
		this.managerIdPerm = managerIdPerm;
	}

	public String getSyncDetailFlag() {
		return syncDetailFlag;
	}

	public void setSyncDetailFlag(String syncDetailFlag) {
		this.syncDetailFlag = syncDetailFlag;
	}

	public String getSuppProductName() {
		return suppProductName;
	}

	public void setSuppProductName(String suppProductName) {
		this.suppProductName = suppProductName;
	}

	public String getHotelCombFlag() {
		return hotelCombFlag;
	}

	public void setHotelCombFlag(String hotelCombFlag) {
		this.hotelCombFlag = hotelCombFlag;
	}

	public Long getDailyLowestPrice() {
		return dailyLowestPrice;
	}

	public void setDailyLowestPrice(Long dailyLowestPrice) {
		this.dailyLowestPrice = dailyLowestPrice;
	}

	public Long getBaseAdultQuantity() {
		return baseAdultQuantity;
	}

	public void setBaseAdultQuantity(Long baseAdultQuantity) {
		this.baseAdultQuantity = baseAdultQuantity;
	}

	public Long getBaseChildQuantity() {
		return baseChildQuantity;
	}

	public void setBaseChildQuantity(Long baseChildQuantity) {
		this.baseChildQuantity = baseChildQuantity;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getUrlQR() {
		return urlQR;
	}

	public void setUrlQR(String urlQR) {
		this.urlQR = urlQR;
	}

	public String getIsKaixinlvxing() {
		return isKaixinlvxing;
	}

	public void setIsKaixinlvxing(String isKaixinlvxing) {
		this.isKaixinlvxing = isKaixinlvxing;
	}

	public String getCompanyType() {
		return companyType;
	}

	public void setCompanyType(String companyType) {
		this.companyType = companyType;
	}
}