package vn.bananavietnam.ict.banana.db.mapper;

import java.util.List;

import vn.bananavietnam.ict.banana.component.Bnn0023SearchUserPopupDataObject;

public interface Bnn0023SearchUserPopupMapper {
	List<Bnn0023SearchUserPopupDataObject> searchData(Bnn0023SearchUserPopupDataObject userData);
	String getSearchDataTotalCounts(Bnn0023SearchUserPopupDataObject userData);
}
