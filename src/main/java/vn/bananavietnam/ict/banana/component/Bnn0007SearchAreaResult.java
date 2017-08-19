package vn.bananavietnam.ict.banana.component;

import vn.bananavietnam.ict.common.db.model.IvbMArea;

public class Bnn0007SearchAreaResult extends IvbMArea{

	// areaId
	private String areaId;
	// farmId
	private String farmId;
	// farmName
	private String farmName;
	// areaName
	private String areaName;
	// area Manager
	private String areaManager;
	// users Id
	private String usersId;
	// users Name 
	private String usersName;
	// kind Id
	private String kindId;
	// kind Name
	private String kindName;
	// sugarContent
	private String sugarContent;
	//texture
	private String texture;
	// prospectiveHarvestAmount
	private Double prospectiveHarvestAmount;
	//note
	private String note;
	// deleteFlag
	private Boolean deleteFlag;
	private String lastUpdateDateStr;
	// Added 1 more attribute to hold the total number of items searched in DB
	private String searchDataTotalCounts;

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
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

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Boolean getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Boolean deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public String getSearchDataTotalCounts() {
		return searchDataTotalCounts;
	}

	public void setSearchDataTotalCounts(String searchDataTotalCounts) {
		this.searchDataTotalCounts = searchDataTotalCounts;
	}

	public String getFarmId() {
		return farmId;
	}

	public void setFarmId(String farmId) {
		this.farmId = farmId;
	}

	public String getAreaManager() {
		return areaManager;
	}

	public void setAreaManager(String areaManager) {
		this.areaManager = areaManager;
	}

	public String getKindId() {
		return kindId;
	}

	public void setKindId(String kindId) {
		this.kindId = kindId;
	}

	public String getSugarContent() {
		return sugarContent;
	}

	public void setSugarContent(String sugarContent) {
		this.sugarContent = sugarContent;
	}

	public String getTexture() {
		return texture;
	}

	public void setTexture(String texture) {
		this.texture = texture;
	}

	public Double getProspectiveHarvestAmount() {
		return prospectiveHarvestAmount;
	}

	public void setProspectiveHarvestAmount(Double prospectiveHarvestAmount) {
		this.prospectiveHarvestAmount = prospectiveHarvestAmount;
	}

	public String getKindName() {
		return kindName;
	}

	public void setKindName(String kindName) {
		this.kindName = kindName;
	}

	public String getUsersId() {
		return usersId;
	}

	public void setUsersId(String usersId) {
		this.usersId = usersId;
	}

	public String getUsersName() {
		return usersName;
	}

	public void setUsersName(String usersName) {
		this.usersName = usersName;
	}

	public String getLastUpdateDateStr() {
		return lastUpdateDateStr;
	}

	public void setLastUpdateDateStr(String lastUpdateDateStr) {
		this.lastUpdateDateStr = lastUpdateDateStr;
	}
}
