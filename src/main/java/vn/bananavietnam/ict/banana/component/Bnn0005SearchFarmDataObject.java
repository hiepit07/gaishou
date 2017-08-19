package vn.bananavietnam.ict.banana.component;

import java.sql.Date;

import vn.bananavietnam.ict.common.db.model.IvbMFarm;

/**
 * @author Hieu Dao
 */

public class Bnn0005SearchFarmDataObject extends IvbMFarm {

	private String usersId;
	private String usersName;
	private String usersIdNew;
	private Date dateOpen;
	private Date fromTime;
	private Date toTime;

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
	public String getUsersIdNew() {
		return usersIdNew;
	}
	public void setUsersIdNew(String usersIdNew) {
		this.usersIdNew = usersIdNew;
	}
	public Date getDateOpen() {
		return dateOpen;
	}
	public void setDateOpen(Date dateOpen) {
		this.dateOpen = dateOpen;
	}
	public Date getFromTime() {
		return fromTime;
	}
	public void setFromTime(Date fromTime) {
		this.fromTime = fromTime;
	}
	public Date getToTime() {
		return toTime;
	}
	public void setToTime(Date toTime) {
		this.toTime = toTime;
	}
}