package vn.bananavietnam.ict.banana.db.mapper;

import java.util.List;
import java.util.Map;

import vn.bananavietnam.ict.banana.component.Bnn0075SearchShippingScreenResult;

public interface Bnn0075SearchShippSreenMapper {

	List<Bnn0075SearchShippingScreenResult> searchData(Map<String, Object> params);
	
	String getSearchDataTotalCounts(Map<String, Object> params);
	
	String getLastShippingNumber();
	
	int updateData(Map<String, Object> params);
	int updateProductData(Map<String, Object> params);
	/******** NGHIA STRAT 2017/05/18 ********/
	List<Bnn0075SearchShippingScreenResult> getLastUpdateDate(Map<String, Object> params);
	Bnn0075SearchShippingScreenResult getLastUpdateDateProduct(Map<String, Object> params);
	/******** NGHIA END 2017/05/18 ********/
}
