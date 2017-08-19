package vn.bananavietnam.ict.common.db.mapper;

import java.util.List;
import java.util.Map;

import vn.bananavietnam.ict.common.component.UtilComponent;

public interface UtilMapper {
	List<UtilComponent> selectFarmDataMaster();
	
	List<UtilComponent> selectFarmData(Map<String, Object> params);
	
	List<UtilComponent> selectAreaDataByFarmIdMaster(Map<String, Object> params);
	
	List<UtilComponent> selectAreaDataByFarmId(Map<String, Object> params);
}