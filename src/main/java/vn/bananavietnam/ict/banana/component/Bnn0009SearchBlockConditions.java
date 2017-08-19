package vn.bananavietnam.ict.banana.component;

import vn.bananavietnam.ict.common.db.model.IvbMBlock;

public class Bnn0009SearchBlockConditions extends IvbMBlock {

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
}