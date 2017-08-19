package vn.bananavietnam.ict.banana.db.mapper;

import java.util.List;
import java.util.Map;

import vn.bananavietnam.ict.banana.component.Bnn0009SearchBlockResult;

public interface Bnn0009SearchBlockMapper {

	List<Bnn0009SearchBlockResult> searchData(Map<String, Object> params);

	String getSearchDataTotalCounts(Map<String, Object> params);

	Map<String, Object> insertData(Map<String, Object> params);

	Map<String, Object> deleteData(Map<String, Object> params);
	
	List<Bnn0009SearchBlockResult> searchBlockNameByFarmIdAndAreaId(Map<String, Object> params);
	
	String getLastBlockId(Map<String, Object> params);

	int updateData(Map<String, Object> params);
}
