package vn.bananavietnam.ict.banana.component;

import vn.bananavietnam.ict.common.db.model.IvbMUsers;

public class Bnn0003SearchUserDataObject extends IvbMUsers {

	// Password changed indicator
	private boolean passwordChanged;

	public boolean isPasswordChanged() {
		return passwordChanged;
	}

	public void setPasswordChanged(boolean passwordChanged) {
		this.passwordChanged = passwordChanged;
	}
}