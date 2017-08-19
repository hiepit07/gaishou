package vn.bananavietnam.ict.common.db.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import vn.bananavietnam.ict.common.db.model.IvbMKind;
import vn.bananavietnam.ict.common.db.model.IvbMKindExample;

public interface IvbMKindMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table IVB_M_KIND
     *
     * @mbggenerated Fri Apr 07 14:55:57 ICT 2017
     */
    int countByExample(IvbMKindExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table IVB_M_KIND
     *
     * @mbggenerated Fri Apr 07 14:55:57 ICT 2017
     */
    int deleteByExample(IvbMKindExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table IVB_M_KIND
     *
     * @mbggenerated Fri Apr 07 14:55:57 ICT 2017
     */
    int deleteByPrimaryKey(String kindId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table IVB_M_KIND
     *
     * @mbggenerated Fri Apr 07 14:55:57 ICT 2017
     */
    int insert(IvbMKind record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table IVB_M_KIND
     *
     * @mbggenerated Fri Apr 07 14:55:57 ICT 2017
     */
    int insertSelective(IvbMKind record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table IVB_M_KIND
     *
     * @mbggenerated Fri Apr 07 14:55:57 ICT 2017
     */
    List<IvbMKind> selectByExample(IvbMKindExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table IVB_M_KIND
     *
     * @mbggenerated Fri Apr 07 14:55:57 ICT 2017
     */
    IvbMKind selectByPrimaryKey(String kindId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table IVB_M_KIND
     *
     * @mbggenerated Fri Apr 07 14:55:57 ICT 2017
     */
    int updateByExampleSelective(@Param("record") IvbMKind record, @Param("example") IvbMKindExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table IVB_M_KIND
     *
     * @mbggenerated Fri Apr 07 14:55:57 ICT 2017
     */
    int updateByExample(@Param("record") IvbMKind record, @Param("example") IvbMKindExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table IVB_M_KIND
     *
     * @mbggenerated Fri Apr 07 14:55:57 ICT 2017
     */
    int updateByPrimaryKeySelective(IvbMKind record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table IVB_M_KIND
     *
     * @mbggenerated Fri Apr 07 14:55:57 ICT 2017
     */
    int updateByPrimaryKey(IvbMKind record);
}