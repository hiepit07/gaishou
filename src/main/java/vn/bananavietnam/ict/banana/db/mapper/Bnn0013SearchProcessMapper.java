package vn.bananavietnam.ict.banana.db.mapper;

import java.util.List;
import java.util.Map;

import vn.bananavietnam.ict.banana.component.Bnn0013SearchProcessResult;
import vn.bananavietnam.ict.banana.component.Bnn0017SearchBananaKindResult;

/**
 * @author HiepTL
 */
public interface Bnn0013SearchProcessMapper {
	List<Bnn0017SearchBananaKindResult> searchKind();
	List<Bnn0013SearchProcessResult> searchData(Map<String, Object> params);

	String getSearchDataTotalCounts(Map<String, Object> params);
	String getLastProcessId();
	int updateData(Map<String, Object> params);
}
