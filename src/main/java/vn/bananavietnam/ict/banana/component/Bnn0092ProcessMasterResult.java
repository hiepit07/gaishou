package vn.bananavietnam.ict.banana.component;

import java.util.Date;

public class Bnn0092ProcessMasterResult {

	// All the fields below are taken from the generated model IvbMPesticides
	private String processName;
	private String taskOrder;
	private String taskName;
	private String workingDetails;
	private String note;
	private Boolean quarantineHandlingFlag;
	private int changePointCode;
	private Date lastUpdateDate;

	// Added 1 more attribute to hold the total number of items searched in DB
	private String searchDataTotalCounts;

	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public String getTaskOrder() {
		return taskOrder;
	}

	public void setTaskOrder(String taskOrder) {
		this.taskOrder = taskOrder;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getWorkingDetails() {
		return workingDetails;
	}

	public void setWorkingDetails(String workingDetails) {
		this.workingDetails = workingDetails;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Boolean getQuarantineHandlingFlag() {
		return quarantineHandlingFlag;
	}

	public void setQuarantineHandlingFlag(Boolean quarantineHandlingFlag) {
		this.quarantineHandlingFlag = quarantineHandlingFlag;
	}

	public String getSearchDataTotalCounts() {
		return searchDataTotalCounts;
	}

	public void setSearchDataTotalCounts(String searchDataTotalCounts) {
		this.searchDataTotalCounts = searchDataTotalCounts;
	}

	public int getChangePointCode() {
		return changePointCode;
	}

	public void setChangePointCode(int changePointCode) {
		this.changePointCode = changePointCode;
	}

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
}