package vn.bananavietnam.ict.banana.component;

import vn.bananavietnam.ict.common.db.model.IvbMUsers;

public class Bnn0003SearchUserResult extends IvbMUsers {

    // Added 1 more attribute to hold the total number of items searched in DB
    private String searchDataTotalCounts;

	public String getSearchDataTotalCounts() {
		return searchDataTotalCounts;
	}
	public void setSearchDataTotalCounts(String searchDataTotalCounts) {
		this.searchDataTotalCounts = searchDataTotalCounts;
	}
}