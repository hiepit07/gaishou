package vn.bananavietnam.ict.common.db.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import vn.bananavietnam.ict.common.db.model.IvbMAccessAuthorization;
import vn.bananavietnam.ict.common.db.model.IvbMAccessAuthorizationExample;
import vn.bananavietnam.ict.common.db.model.IvbMAccessAuthorizationKey;

public interface IvbMAccessAuthorizationMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table IVB_M_ACCESS_AUTHORIZATION
     *
     * @mbggenerated Fri Apr 07 14:55:57 ICT 2017
     */
    int countByExample(IvbMAccessAuthorizationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table IVB_M_ACCESS_AUTHORIZATION
     *
     * @mbggenerated Fri Apr 07 14:55:57 ICT 2017
     */
    int deleteByExample(IvbMAccessAuthorizationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table IVB_M_ACCESS_AUTHORIZATION
     *
     * @mbggenerated Fri Apr 07 14:55:57 ICT 2017
     */
    int deleteByPrimaryKey(IvbMAccessAuthorizationKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table IVB_M_ACCESS_AUTHORIZATION
     *
     * @mbggenerated Fri Apr 07 14:55:57 ICT 2017
     */
    int insert(IvbMAccessAuthorization record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table IVB_M_ACCESS_AUTHORIZATION
     *
     * @mbggenerated Fri Apr 07 14:55:57 ICT 2017
     */
    int insertSelective(IvbMAccessAuthorization record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table IVB_M_ACCESS_AUTHORIZATION
     *
     * @mbggenerated Fri Apr 07 14:55:57 ICT 2017
     */
    List<IvbMAccessAuthorization> selectByExample(IvbMAccessAuthorizationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table IVB_M_ACCESS_AUTHORIZATION
     *
     * @mbggenerated Fri Apr 07 14:55:57 ICT 2017
     */
    IvbMAccessAuthorization selectByPrimaryKey(IvbMAccessAuthorizationKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table IVB_M_ACCESS_AUTHORIZATION
     *
     * @mbggenerated Fri Apr 07 14:55:57 ICT 2017
     */
    int updateByExampleSelective(@Param("record") IvbMAccessAuthorization record, @Param("example") IvbMAccessAuthorizationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table IVB_M_ACCESS_AUTHORIZATION
     *
     * @mbggenerated Fri Apr 07 14:55:57 ICT 2017
     */
    int updateByExample(@Param("record") IvbMAccessAuthorization record, @Param("example") IvbMAccessAuthorizationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table IVB_M_ACCESS_AUTHORIZATION
     *
     * @mbggenerated Fri Apr 07 14:55:57 ICT 2017
     */
    int updateByPrimaryKeySelective(IvbMAccessAuthorization record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table IVB_M_ACCESS_AUTHORIZATION
     *
     * @mbggenerated Fri Apr 07 14:55:57 ICT 2017
     */
    int updateByPrimaryKey(IvbMAccessAuthorization record);
}