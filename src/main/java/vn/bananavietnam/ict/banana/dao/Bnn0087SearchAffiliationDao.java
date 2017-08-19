package vn.bananavietnam.ict.banana.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import vn.bananavietnam.ict.banana.db.mapper.Bnn0087SearchAffiliationMapper;
import vn.bananavietnam.ict.common.db.mapper.IvbMAuthorizationMapper;
import vn.bananavietnam.ict.common.db.mapper.IvbMManagerMapper;

@Component
public class Bnn0087SearchAffiliationDao {
    @Autowired
    private Bnn0087SearchAffiliationMapper bnn0087SearchAffiliationMapper;
    @Autowired
    private IvbMAuthorizationMapper ivbMAuthorizationMapper;
    @Autowired
    private IvbMManagerMapper ivbMManagerMapper;
    public Bnn0087SearchAffiliationMapper getBnn0087SearchAffiliationMapper() {
        return bnn0087SearchAffiliationMapper;
    }

    public IvbMAuthorizationMapper getIvbMAuthorizationMapper() {
        return ivbMAuthorizationMapper;
    }

    public IvbMManagerMapper getIvbMManagerMapper() {
        return ivbMManagerMapper;
    }

}
