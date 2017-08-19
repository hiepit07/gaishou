package vn.bananavietnam.ict.banana.db.mapper;

import java.util.List;
import java.util.Map;

import vn.bananavietnam.ict.banana.component.Bnn0061TraceabilityMasterDataConditions;
import vn.bananavietnam.ict.banana.component.Bnn0061TraceabilityMasterDataObject;
import vn.bananavietnam.ict.common.db.model.IvbMProcess;
import vn.bananavietnam.ict.common.db.model.IvbMStatus;
import vn.bananavietnam.ict.common.db.model.IvbMTask;
import vn.bananavietnam.ict.common.db.model.IvbTProduct;

/**
 * @author Hieu Dao
 */

public interface Bnn0061TraceabilityMasterMapper {

	List<String> getLineIdList(IvbTProduct productObject);

	List<String> getColumnIdList(IvbTProduct productObject);

	List<Bnn0061TraceabilityMasterDataObject> searchData(Bnn0061TraceabilityMasterDataConditions bnn0061Conditions);

	Bnn0061TraceabilityMasterDataObject getSingleData(Bnn0061TraceabilityMasterDataConditions bnn0061Conditions);

	String getSearchDataTotalCounts(Bnn0061TraceabilityMasterDataConditions bnn0061Conditions);
	
	List<IvbMTask> getTaskList(Map<String, Object> params);
	
	List<IvbMStatus> getStatusList(Map<String, Object> params);
	
	List<IvbMProcess> getProcessList(Map<String, Object> params);
}