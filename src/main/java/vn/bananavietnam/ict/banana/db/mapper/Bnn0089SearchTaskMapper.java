package vn.bananavietnam.ict.banana.db.mapper;

import java.util.List;
import java.util.Map;

import vn.bananavietnam.ict.banana.component.Bnn0089SearchTaskResult;

public interface Bnn0089SearchTaskMapper {

	List<Bnn0089SearchTaskResult> searchData(Map<String, Object> params);
	
	String getSearchDataTotalCounts(Map<String, Object> params);
	
	String getLastTaskId();
	
	int updateData(Map<String, Object> params);
}
