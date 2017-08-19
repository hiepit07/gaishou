package vn.bananavietnam.ict.banana.component;

import vn.bananavietnam.ict.common.db.model.IvbMUsers;

public class Bnn0003SearchUserConditions extends IvbMUsers {

	// Role type string
	private String roleIdString;

	// Farm
	private String farmId;
	
	// From parameter
	private String fromRow;

	// Number of items in a page
	private String itemCount;

	public String getFromRow() {
		return fromRow;
	}

	public void setFromRow(String fromRow) {
		this.fromRow = fromRow;
	}

	public String getItemCount() {
		return itemCount;
	}

	public void setItemCount(String itemCount) {
		this.itemCount = itemCount;
	}

	public String getRoleIdString() {
		return roleIdString;
	}

	public void setRoleIdString(String roleIdString) {
		this.roleIdString = roleIdString;
	}

	public String getFarmId() {
		return farmId;
	}

	public void setFarmId(String farmId) {
		this.farmId = farmId;
	}
}