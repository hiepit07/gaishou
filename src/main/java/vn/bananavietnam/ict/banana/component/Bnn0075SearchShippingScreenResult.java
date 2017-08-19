package vn.bananavietnam.ict.banana.component;
import java.sql.Date;

public class Bnn0075SearchShippingScreenResult {

	// Farm Id
	private String farmId;
	// Area Id
	private String areaId;
	// Farm Name
	private String farmName;
	// Area Name
	private String areaName;
	//Shipping Number
	private String shippingNumber;
	// area Manager
	private String areaManager;
	//harvest date
	private Date harvestDate;
	//ship date
	private Date shipDate;
	// Delete flag
	private Boolean deleteFlag;
	// last Update Date
	private java.util.Date lastUpdateDate;
	private java.util.Date lastUpdateDateProduct;
	private java.util.Date shippingDate;
	// Added 1 more attribute to hold the total number of items searched in DB
	private String searchDataTotalCounts;
	
	public String getShippingNumber() {
		return shippingNumber;
	}
	
	public void setShippingNumber(String shippingNumber) {
		this.shippingNumber = shippingNumber;
	}
	
	public String getAreaId() {
		return areaId;
	}
	
	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}
	
	public String getFarmId() {
		return farmId;
	}
	
	public void setFarmId(String farmId) {
		this.farmId = farmId;
	}
	
	public String getFarmName() {
		return farmName;
	}
	
	public void setFarmName(String farmName) {
		this.farmName = farmName;
	}
	
	public String getAreaName() {
		return areaName;
	}
	
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	
	public String getAreaManager() {
		return areaManager;
	}
	
	public void setAreaManager(String areaManager) {
		this.areaManager = areaManager;
	}
	
	public Date getHarvestDate() {
		return harvestDate;
	}
	
	public void setHarvestDate(Date harvestDate) {
		this.harvestDate = harvestDate;
	}
	
	public Date getShipDate() {
		return shipDate;
	}
	
	public void setShipDate(Date shipDate) {
		this.shipDate = shipDate;
	}
	
	public Boolean getDeleteFlag() {
		return deleteFlag;
	}
	
	public void setDeleteFlag(Boolean deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	
	public java.util.Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(java.util.Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public String getSearchDataTotalCounts() {
		return searchDataTotalCounts;
	}
	
	public void setSearchDataTotalCounts(String searchDataTotalCounts) {
		this.searchDataTotalCounts = searchDataTotalCounts;
	}

	public java.util.Date getLastUpdateDateProduct() {
		return lastUpdateDateProduct;
	}

	public void setLastUpdateDateProduct(java.util.Date lastUpdateDateProduct) {
		this.lastUpdateDateProduct = lastUpdateDateProduct;
	}

	public java.util.Date getShippingDate() {
		return shippingDate;
	}

	public void setShippingDate(java.util.Date shippingDate) {
		this.shippingDate = shippingDate;
	}

}
