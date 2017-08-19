package vn.bananavietnam.ict.banana.db.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import vn.bananavietnam.ict.banana.component.Bnn0087SearchAffiliationResult;
import vn.bananavietnam.ict.banana.component.Bnn0088SearchAreaResult;
import vn.bananavietnam.ict.banana.component.Bnn0088SearchFarmResult;

public interface Bnn0087SearchAffiliationMapper {
    List<Bnn0087SearchAffiliationResult> searchData(Map<String, Object> params);

    String getSearchDataTotalCounts(Map<String, Object> params);

    Integer updateDeleteFlag(Map<String, Object> params);

    List<Bnn0088SearchFarmResult> selectDataFarm();

    List<Bnn0088SearchAreaResult> selectDataArea(Map<String, Object> params);
    
    Integer deleteData(Map<String, Object> params);
    
    List<Bnn0088SearchAreaResult> checkAreaManager(Map<String, Object> params);

    Date getLastUpdateDate(Map<String, Object> params);
}
