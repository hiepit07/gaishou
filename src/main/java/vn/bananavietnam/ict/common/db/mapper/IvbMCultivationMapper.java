package vn.bananavietnam.ict.common.db.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import vn.bananavietnam.ict.common.db.model.IvbMCultivation;
import vn.bananavietnam.ict.common.db.model.IvbMCultivationExample;
import vn.bananavietnam.ict.common.db.model.IvbMCultivationKey;

public interface IvbMCultivationMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table IVB_M_CULTIVATION
     *
     * @mbggenerated Fri Apr 07 14:55:57 ICT 2017
     */
    int countByExample(IvbMCultivationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table IVB_M_CULTIVATION
     *
     * @mbggenerated Fri Apr 07 14:55:57 ICT 2017
     */
    int deleteByExample(IvbMCultivationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table IVB_M_CULTIVATION
     *
     * @mbggenerated Fri Apr 07 14:55:57 ICT 2017
     */
    int deleteByPrimaryKey(IvbMCultivationKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table IVB_M_CULTIVATION
     *
     * @mbggenerated Fri Apr 07 14:55:57 ICT 2017
     */
    int insert(IvbMCultivation record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table IVB_M_CULTIVATION
     *
     * @mbggenerated Fri Apr 07 14:55:57 ICT 2017
     */
    int insertSelective(IvbMCultivation record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table IVB_M_CULTIVATION
     *
     * @mbggenerated Fri Apr 07 14:55:57 ICT 2017
     */
    List<IvbMCultivation> selectByExample(IvbMCultivationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table IVB_M_CULTIVATION
     *
     * @mbggenerated Fri Apr 07 14:55:57 ICT 2017
     */
    IvbMCultivation selectByPrimaryKey(IvbMCultivationKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table IVB_M_CULTIVATION
     *
     * @mbggenerated Fri Apr 07 14:55:57 ICT 2017
     */
    int updateByExampleSelective(@Param("record") IvbMCultivation record, @Param("example") IvbMCultivationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table IVB_M_CULTIVATION
     *
     * @mbggenerated Fri Apr 07 14:55:57 ICT 2017
     */
    int updateByExample(@Param("record") IvbMCultivation record, @Param("example") IvbMCultivationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table IVB_M_CULTIVATION
     *
     * @mbggenerated Fri Apr 07 14:55:57 ICT 2017
     */
    int updateByPrimaryKeySelective(IvbMCultivation record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table IVB_M_CULTIVATION
     *
     * @mbggenerated Fri Apr 07 14:55:57 ICT 2017
     */
    int updateByPrimaryKey(IvbMCultivation record);
}