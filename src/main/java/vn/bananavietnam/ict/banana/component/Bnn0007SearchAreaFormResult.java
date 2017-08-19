package vn.bananavietnam.ict.banana.component;

import vn.bananavietnam.ict.common.db.model.IvbMArea;

public class Bnn0007SearchAreaFormResult extends  IvbMArea {
	// Farm ID
	private String farmId;
	// Farm Name
	private String farmName;
	// Area ID
	private String areaId;
	// Area Name
	private String areaName;
	// area Manager
	private String areaManager;
	// Number Of Block;
	private Integer numberOfBlock;
	// kind Id
	private String kindId;
	// Kind Name
	private String kindName;
	// sugar Content
	private String sugarContent;
	// texture
	private String texture;
	// prospective Harvest Amount
	private Double prospectiveHarvestAmount;
	
	private String lastUpdateDateStr;
	// note
	private String note;
	// Delete Flag
	private Boolean deleteFlag;
	private String usersId;
	private String usersName;

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

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
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

	public Integer getNumberOfBlock() {
		return numberOfBlock;
	}

	public void setNumberOfBlock(Integer numberOfBlock) {
		this.numberOfBlock = numberOfBlock;
	}

	public String getKindName() {
		return kindName;
	}

	public void setKindName(String kindName) {
		this.kindName = kindName;
	}

	public String getLastUpdateDateStr() {
		return lastUpdateDateStr;
	}

	public void setLastUpdateDateStr(String lastUpdateDateStr) {
		this.lastUpdateDateStr = lastUpdateDateStr;
	}
}
