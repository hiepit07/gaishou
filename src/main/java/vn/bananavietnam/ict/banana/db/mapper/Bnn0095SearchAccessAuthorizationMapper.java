package vn.bananavietnam.ict.banana.db.mapper;

import java.util.List;
import java.util.Map;

import vn.bananavietnam.ict.banana.component.Bnn0095SearchAccessAuthorizationResult;
/**
 * 	Bnn0095SearchAccessAuthorizationMapper
 * 
 *	@author NghiaNguyen
 *	@version 1.0.0
 * 	@since 1.0.0
 */
public interface Bnn0095SearchAccessAuthorizationMapper {

	List<Bnn0095SearchAccessAuthorizationResult> searchData(Map<String, Object> params);

	List<Bnn0095SearchAccessAuthorizationResult> searchSingleData(Map<String, Object> params);
	
	String getSearchDataTotalCounts(Map<String, Object> params);

	int updateData(Map<String, Object> params);

	int deleteData(Map<String, Object> params);
}
