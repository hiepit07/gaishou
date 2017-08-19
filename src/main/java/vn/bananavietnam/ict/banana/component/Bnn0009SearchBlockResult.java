package vn.bananavietnam.ict.banana.component;

import vn.bananavietnam.ict.common.db.model.IvbMBlock;

public class Bnn0009SearchBlockResult extends IvbMBlock {

	// Farm name
	private String farmName;

	// Area name
	private String areaName;
	
	// Last Update Date
	private String lastUpdateDateStr;

	// Real search result total data count
	private String searchDataTotalCounts;

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

	public String getSearchDataTotalCounts() {
		return searchDataTotalCounts;
	}

	public void setSearchDataTotalCounts(String searchDataTotalCounts) {
		this.searchDataTotalCounts = searchDataTotalCounts;
	}

	public String getLastUpdateDateStr() {
		return lastUpdateDateStr;
	}

	public void setLastUpdateDateStr(String lastUpdateDateStr) {
		this.lastUpdateDateStr = lastUpdateDateStr;
	}
}