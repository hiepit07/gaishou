package vn.bananavietnam.ict.banana.component;

import java.util.Date;

import vn.bananavietnam.ict.common.db.model.IvbMCultivation;

/**
 * @author Hieu Dao
 */

public class Bnn0091CultivationMasterDataObject extends IvbMCultivation {

	// Farm id
	private String farmId;
	// Farm name
	private String farmName;
	// Kind id
	private String kindId;
	// Kind name
	private String kindName;
	// Process id
	private String processId;
	// Task id
	private String taskId;
	// Task name
	private String taskName;
	// Block flag
	private String blockFlag;
	// Last update date
	private Date lastUpdateDate;

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
	public String getKindId() {
		return kindId;
	}
	public void setKindId(String kindId) {
		this.kindId = kindId;
	}
	public String getKindName() {
		return kindName;
	}
	public void setKindName(String kindName) {
		this.kindName = kindName;
	}
	public String getProcessId() {
		return processId;
	}
	public void setProcessId(String processId) {
		this.processId = processId;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getBlockFlag() {
		return blockFlag;
	}
	public void setBlockFlag(String blockFlag) {
		this.blockFlag = blockFlag;
	}
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}
	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
}