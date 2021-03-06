package vn.bananavietnam.ict.common.db.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import vn.bananavietnam.ict.common.db.model.IvbTCultivationResult;
import vn.bananavietnam.ict.common.db.model.IvbTCultivationResultExample;
import vn.bananavietnam.ict.common.db.model.IvbTCultivationResultKey;

public interface IvbTCultivationResultMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table IVB_T_CULTIVATION_RESULT
     *
     * @mbggenerated Fri Apr 07 14:55:57 ICT 2017
     */
    int countByExample(IvbTCultivationResultExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table IVB_T_CULTIVATION_RESULT
     *
     * @mbggenerated Fri Apr 07 14:55:57 ICT 2017
     */
    int deleteByExample(IvbTCultivationResultExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table IVB_T_CULTIVATION_RESULT
     *
     * @mbggenerated Fri Apr 07 14:55:57 ICT 2017
     */
    int deleteByPrimaryKey(IvbTCultivationResultKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table IVB_T_CULTIVATION_RESULT
     *
     * @mbggenerated Fri Apr 07 14:55:57 ICT 2017
     */
    int insert(IvbTCultivationResult record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table IVB_T_CULTIVATION_RESULT
     *
     * @mbggenerated Fri Apr 07 14:55:57 ICT 2017
     */
    int insertSelective(IvbTCultivationResult record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table IVB_T_CULTIVATION_RESULT
     *
     * @mbggenerated Fri Apr 07 14:55:57 ICT 2017
     */
    List<IvbTCultivationResult> selectByExample(IvbTCultivationResultExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table IVB_T_CULTIVATION_RESULT
     *
     * @mbggenerated Fri Apr 07 14:55:57 ICT 2017
     */
    IvbTCultivationResult selectByPrimaryKey(IvbTCultivationResultKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table IVB_T_CULTIVATION_RESULT
     *
     * @mbggenerated Fri Apr 07 14:55:57 ICT 2017
     */
    int updateByExampleSelective(@Param("record") IvbTCultivationResult record, @Param("example") IvbTCultivationResultExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table IVB_T_CULTIVATION_RESULT
     *
     * @mbggenerated Fri Apr 07 14:55:57 ICT 2017
     */
    int updateByExample(@Param("record") IvbTCultivationResult record, @Param("example") IvbTCultivationResultExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table IVB_T_CULTIVATION_RESULT
     *
     * @mbggenerated Fri Apr 07 14:55:57 ICT 2017
     */
    int updateByPrimaryKeySelective(IvbTCultivationResult record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table IVB_T_CULTIVATION_RESULT
     *
     * @mbggenerated Fri Apr 07 14:55:57 ICT 2017
     */
    int updateByPrimaryKey(IvbTCultivationResult record);
}