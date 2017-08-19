package vn.bananavietnam.ict.banana.component;

import vn.bananavietnam.ict.common.db.model.IvbMManager;

public class Bnn0023SearchUserPopupDataObject extends IvbMManager {
	// User Id
	private String userId;
	// User name
	private String userName;

	// From parameter
	private int fromRow;

	// Number of items in a page
	private int itemCount;
	// Added 1 more attribute to hold the total number of items searched in DB
    private String searchDataTotalCounts;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getFromRow() {
		return fromRow;
	}
	public void setFromRow(int fromRow) {
		this.fromRow = fromRow;
	}
	public int getItemCount() {
		return itemCount;
	}
	public void setItemCount(int itemCount) {
		this.itemCount = itemCount;
	}
	public String getSearchDataTotalCounts() {
		return searchDataTotalCounts;
	}
	public void setSearchDataTotalCounts(String searchDataTotalCounts) {
		this.searchDataTotalCounts = searchDataTotalCounts;
	}
}