package vn.bananavietnam.ict.banana.db.mapper;

import java.util.Date;
import java.util.List;

import vn.bananavietnam.ict.banana.component.Bnn0092ProcessMasterResult;
import vn.bananavietnam.ict.common.db.model.IvbMCultivation;
import vn.bananavietnam.ict.common.db.model.IvbMProcess;

/**
 * @author Hieu Dao
 */

public interface Bnn0092ProcessMasterMapper {

	List<IvbMProcess> getUnregisteredProcess(IvbMCultivation cultivationData);
	List<IvbMProcess> getRegisteredProcess(IvbMCultivation cultivationData);
	List<Bnn0092ProcessMasterResult> getProcessDetailData(IvbMCultivation cultivationData);

	Date getLastUpdateDate(IvbMCultivation cultivationData);
	String getSearchDataTotalCounts(IvbMCultivation cultivationData);
}