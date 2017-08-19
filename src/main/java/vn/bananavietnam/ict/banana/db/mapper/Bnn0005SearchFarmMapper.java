package vn.bananavietnam.ict.banana.db.mapper;

import java.util.List;
import java.util.Map;

import vn.bananavietnam.ict.banana.component.Bnn0005SearchFarmDataObject;
import vn.bananavietnam.ict.banana.component.Bnn0005SearchFarmResult;
import vn.bananavietnam.ict.common.db.model.IvbMFarm;
import vn.bananavietnam.ict.common.db.model.IvbMManager;

/**
 * @author Hieu Dao
 */

public interface Bnn0005SearchFarmMapper {

	List<Bnn0005SearchFarmResult> searchData(Map<String, Object> params);

	Bnn0005SearchFarmDataObject getSingleData(String farmId);
	
	List<IvbMManager> selectDataMananger(Map<String, Object> params);
	
	String getLastFarmId(Map<String, Object> params);

	String getSearchDataTotalCounts(Map<String, Object> params);
	
	Map<String, Object> deleteData(Map<String, Object> params);
	
	int updateData(Map<String, Object> params);
	
	List<IvbMFarm> getAllFarmName(Map<String, Object> params);
}
