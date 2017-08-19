package vn.bananavietnam.ict.banana.component;

import java.util.Date;

public class Bnn0005SearchFarmResult {

	// All the fields below are taken from the generated model IvbMFarm and IvbMUsers
	private String farmId;
	private String farmName;
	private String usersId;
	private String usersName;
	private String lineOfPlan;
	private String columnOfPlan;
	private String openDate;
	private String timeFrom;
	private String timeTo;
	private String address;
	private String climate;
	private String soil;
	private Double sizeOfPlan;
	private String email;
	private String phone;
	private String fax;
	private String note;
	private String deleteFlag;
	private Date lastUpdateDate;

	// Added 1 more attribute to hold the total number of items searched in DB
	private String searchDataTotalCounts;

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

	public String getLineOfPlan() {
		return lineOfPlan;
	}

	public void setLineOfPlan(String lineOfPlan) {
		this.lineOfPlan = lineOfPlan;
	}

	public String getColumnOfPlan() {
		return columnOfPlan;
	}

	public void setColumnOfPlan(String columnOfPlan) {
		this.columnOfPlan = columnOfPlan;
	}

	public String getOpenDate() {
		return openDate;
	}

	public void setOpenDate(String openDate) {
		this.openDate = openDate;
	}

	public String getTimeFrom() {
		return timeFrom;
	}

	public void setTimeFrom(String timeFrom) {
		this.timeFrom = timeFrom;
	}

	public String getTimeTo() {
		return timeTo;
	}

	public void setTimeTo(String timeTo) {
		this.timeTo = timeTo;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getClimate() {
		return climate;
	}

	public void setClimate(String climate) {
		this.climate = climate;
	}

	public String getSoil() {
		return soil;
	}

	public void setSoil(String soil) {
		this.soil = soil;
	}

	public Double getSizeOfPlan() {
		return sizeOfPlan;
	}

	public void setSizeOfPlan(Double sizeOfPlan) {
		this.sizeOfPlan = sizeOfPlan;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public String getSearchDataTotalCounts() {
		return searchDataTotalCounts;
	}

	public void setSearchDataTotalCounts(String searchDataTotalCounts) {
		this.searchDataTotalCounts = searchDataTotalCounts;
	}

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
}