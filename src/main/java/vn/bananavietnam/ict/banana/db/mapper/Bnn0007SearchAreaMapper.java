package vn.bananavietnam.ict.banana.db.mapper;

import java.util.List;
import java.util.Map;

import vn.bananavietnam.ict.banana.component.Bnn0007SearchAreaFormResult;
import vn.bananavietnam.ict.banana.component.Bnn0007SearchAreaResult;

public interface Bnn0007SearchAreaMapper {

	List<Bnn0007SearchAreaResult> searchData(Map<String, Object> params);

	List<Bnn0007SearchAreaFormResult> searchSingleData(Map<String, Object> params);

	String getSearchDataTotalCounts(Map<String, Object> params);

	Map<String, Object> insertData(Map<String, Object> params);

	Map<String, Object> deleteData(Map<String, Object> params);

	List<Bnn0007SearchAreaResult> searchAreaNameByFarmId(Map<String, Object> params);

	String getLastAreaId(Map<String, Object> params);

	int updateData(Map<String, Object> params);
}
