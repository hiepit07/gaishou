package vn.bananavietnam.ict.banana.component;

import java.util.ArrayList;

public class Bnn0088SearchAreaResult {
    private String farmName;
    private String areaName;
    private String farmId;
    private String areaId;
    private String areaManager;
    private String haveData;
    private String usersName;

    public String getHaveData() {
        return haveData;
    }

    public void setHaveData(String haveData) {
        this.haveData = haveData;
    }

    ArrayList<Bnn0088SearchFarmResult> arrDataFarm = new ArrayList<Bnn0088SearchFarmResult>();

    public String getUsersName() {
		return usersName;
	}

	public void setUsersName(String usersName) {
		this.usersName = usersName;
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

    public ArrayList<Bnn0088SearchFarmResult> getArrDataFarm() {
        return arrDataFarm;
    }

    public void setArrDataFarm(ArrayList<Bnn0088SearchFarmResult> arrDataFarm) {
        this.arrDataFarm = arrDataFarm;
    }

    public String getFarmId() {
        return farmId;
    }

    public void setFarmId(String farmId) {
        this.farmId = farmId;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }
}