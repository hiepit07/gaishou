package vn.bananavietnam.ict.banana.db.mapper;

import java.util.List;
import java.util.Map;

import vn.bananavietnam.ict.banana.component.Bnn0037ProductExtendObject;
import vn.bananavietnam.ict.banana.component.BnnBlockCultivationResult;
import vn.bananavietnam.ict.common.db.model.IvbMProcess;
import vn.bananavietnam.ict.common.db.model.IvbMTask;
import vn.bananavietnam.ict.common.db.model.IvbTCultivationResult;
import vn.bananavietnam.ict.common.db.model.IvbTProduct;

public interface Bnn0037BlockDetailUpdateMapper {

	List<BnnBlockCultivationResult> getCultivationResultData(Map<String, Object> params);

	List<IvbMProcess> getProcessData(Map<String, Object> params);

	List<Bnn0037ProductExtendObject> getProductExtendData(Map<String, Object> params);

	List<IvbMTask> getTaskData(Map<String, Object> params);

	int updateCultivationStartDate(Map<String, Object> params);

	int updateProductPlantingDate(Map<String, Object> params);

	int updateProductFloweringDate(Map<String, Object> params);

	int updateProductBaggedDate(Map<String, Object> params);

	int updateProductHarvestedDate(Map<String, Object> params);
	
	IvbTProduct getLastUpdateDateProduct(Map<String, Object> params);
	
	IvbTCultivationResult getLastUpdateDateCultivation(Map<String, Object> params);
	
	int updateData(Map<String, Object> params);
}
