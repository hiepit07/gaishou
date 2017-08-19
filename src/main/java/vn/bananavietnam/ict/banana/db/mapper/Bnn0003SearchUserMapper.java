package vn.bananavietnam.ict.banana.db.mapper;

import java.util.List;
import java.util.Map;

import vn.bananavietnam.ict.banana.component.Bnn0003SearchUserResult;

public interface Bnn0003SearchUserMapper {

	List<Bnn0003SearchUserResult> searchData(Map<String, Object> params);

	String getSearchDataTotalCounts(Map<String, Object> params);
    
    int updateData(Map<String, Object> params);
}
