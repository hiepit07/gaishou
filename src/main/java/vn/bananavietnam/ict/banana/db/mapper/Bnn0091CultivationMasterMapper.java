package vn.bananavietnam.ict.banana.db.mapper;

import java.util.Date;
import java.util.List;

import vn.bananavietnam.ict.banana.component.Bnn0091CultivationMasterDataObject;
import vn.bananavietnam.ict.common.db.model.IvbMCultivation;
import vn.bananavietnam.ict.common.db.model.IvbMTask;

/**
 * @author Hieu Dao
 */

public interface Bnn0091CultivationMasterMapper {

	List<IvbMTask> getUnregisteredTask(IvbMCultivation cultivationData);
	List<Bnn0091CultivationMasterDataObject> getRegisteredTask(IvbMCultivation cultivationData);

	Date getLastUpdateDate(Bnn0091CultivationMasterDataObject cultivationData);
	String getUnregisteredProcessTotal(IvbMCultivation cultivationData);
}