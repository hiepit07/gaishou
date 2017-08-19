package vn.bananavietnam.ict.banana.component;

import java.util.Date;

public class Bnn0087SearchAffiliationResult {

    private String userName;
    private String typeName;
    private String typeId;
    private String userId;
    private String farmIdList;
    private String areaIdList;
    private Date lastUpdateDate;
    // Added 1 more attribute to hold the total number of items searched in DB
    private String searchDataTotalCounts;

    public String getSearchDataTotalCounts() {
        return searchDataTotalCounts;
    }

    public void setSearchDataTotalCounts(String searchDataTotalCounts) {
        this.searchDataTotalCounts = searchDataTotalCounts;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFarmIdList() {
		return farmIdList;
	}

	public void setFarmIdList(String farmIdList) {
		this.farmIdList = farmIdList;
	}

	public String getAreaIdList() {
		return areaIdList;
	}

	public void setAreaIdList(String areaIdList) {
		this.areaIdList = areaIdList;
	}

	public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
}