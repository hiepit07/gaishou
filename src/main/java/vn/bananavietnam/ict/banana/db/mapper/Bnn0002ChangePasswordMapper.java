package vn.bananavietnam.ict.banana.db.mapper;

import java.util.Map;

public interface Bnn0002ChangePasswordMapper {

	String comparePassword(Map<String, Object> params);
	
	int updateData(Map<String, Object> params);
}
