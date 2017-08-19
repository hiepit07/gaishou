package vn.bananavietnam.ict.common.db.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import vn.bananavietnam.ict.common.db.model.IvbMStatus;
import vn.bananavietnam.ict.common.db.model.IvbMStatusExample;
import vn.bananavietnam.ict.common.db.model.IvbMStatusKey;

public interface IvbMStatusMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table IVB_M_STATUS
     *
     * @mbggenerated Fri Apr 07 14:55:57 ICT 2017
     */
    int countByExample(IvbMStatusExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table IVB_M_STATUS
     *
     * @mbggenerated Fri Apr 07 14:55:57 ICT 2017
     */
    int deleteByExample(IvbMStatusExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table IVB_M_STATUS
     *
     * @mbggenerated Fri Apr 07 14:55:57 ICT 2017
     */
    int deleteByPrimaryKey(IvbMStatusKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table IVB_M_STATUS
     *
     * @mbggenerated Fri Apr 07 14:55:57 ICT 2017
     */
    int insert(IvbMStatus record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table IVB_M_STATUS
     *
     * @mbggenerated Fri Apr 07 14:55:57 ICT 2017
     */
    int insertSelective(IvbMStatus record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table IVB_M_STATUS
     *
     * @mbggenerated Fri Apr 07 14:55:57 ICT 2017
     */
    List<IvbMStatus> selectByExample(IvbMStatusExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table IVB_M_STATUS
     *
     * @mbggenerated Fri Apr 07 14:55:57 ICT 2017
     */
    IvbMStatus selectByPrimaryKey(IvbMStatusKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table IVB_M_STATUS
     *
     * @mbggenerated Fri Apr 07 14:55:57 ICT 2017
     */
    int updateByExampleSelective(@Param("record") IvbMStatus record, @Param("example") IvbMStatusExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table IVB_M_STATUS
     *
     * @mbggenerated Fri Apr 07 14:55:57 ICT 2017
     */
    int updateByExample(@Param("record") IvbMStatus record, @Param("example") IvbMStatusExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table IVB_M_STATUS
     *
     * @mbggenerated Fri Apr 07 14:55:57 ICT 2017
     */
    int updateByPrimaryKeySelective(IvbMStatus record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table IVB_M_STATUS
     *
     * @mbggenerated Fri Apr 07 14:55:57 ICT 2017
     */
    int updateByPrimaryKey(IvbMStatus record);
}