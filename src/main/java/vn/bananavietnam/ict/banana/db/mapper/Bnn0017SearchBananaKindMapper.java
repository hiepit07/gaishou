package vn.bananavietnam.ict.banana.db.mapper;

import java.util.List;
import java.util.Map;

import vn.bananavietnam.ict.banana.component.Bnn0017SearchBananaKindResult;

public interface Bnn0017SearchBananaKindMapper {
	
	List<Bnn0017SearchBananaKindResult> searchData(Map<String, Object> params);
	
	String getSearchDataTotalCounts(Map<String, Object> params);
	
	String getLastKindId(Map<String, Object> params);
	int updateData(Map<String, Object> params);
}
