package vn.bananavietnam.ict.banana.db.mapper;

import java.util.List;
import java.util.Map;

import vn.bananavietnam.ict.banana.component.Bnn0045SearchCultivationInFormCbbResult;
import vn.bananavietnam.ict.banana.component.Bnn0045SearchCultivationResult;

public interface Bnn0045SearchCultivationMapper {	
	List<Bnn0045SearchCultivationInFormCbbResult> getDataKindCbb (Map<String, Object> params);
	
	List<Bnn0045SearchCultivationInFormCbbResult> getDataProcessCbb (Map<String, Object> params);
	
	List<Bnn0045SearchCultivationInFormCbbResult> getDataTaskCbb (Map<String, Object> params);
	
	List<Bnn0045SearchCultivationResult> searchData(Map<String, Object> params);
	
	String getSearchDataTotalCounts(Map<String, Object> params);
}