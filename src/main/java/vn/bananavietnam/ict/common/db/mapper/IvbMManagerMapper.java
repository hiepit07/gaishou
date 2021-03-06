package vn.bananavietnam.ict.common.db.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import vn.bananavietnam.ict.common.db.model.IvbMManager;
import vn.bananavietnam.ict.common.db.model.IvbMManagerExample;
import vn.bananavietnam.ict.common.db.model.IvbMManagerKey;

public interface IvbMManagerMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table IVB_M_MANAGER
     *
     * @mbggenerated Fri Apr 07 14:55:57 ICT 2017
     */
    int countByExample(IvbMManagerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table IVB_M_MANAGER
     *
     * @mbggenerated Fri Apr 07 14:55:57 ICT 2017
     */
    int deleteByExample(IvbMManagerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table IVB_M_MANAGER
     *
     * @mbggenerated Fri Apr 07 14:55:57 ICT 2017
     */
    int deleteByPrimaryKey(IvbMManagerKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table IVB_M_MANAGER
     *
     * @mbggenerated Fri Apr 07 14:55:57 ICT 2017
     */
    int insert(IvbMManager record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table IVB_M_MANAGER
     *
     * @mbggenerated Fri Apr 07 14:55:57 ICT 2017
     */
    int insertSelective(IvbMManager record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table IVB_M_MANAGER
     *
     * @mbggenerated Fri Apr 07 14:55:57 ICT 2017
     */
    List<IvbMManager> selectByExample(IvbMManagerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table IVB_M_MANAGER
     *
     * @mbggenerated Fri Apr 07 14:55:57 ICT 2017
     */
    IvbMManager selectByPrimaryKey(IvbMManagerKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table IVB_M_MANAGER
     *
     * @mbggenerated Fri Apr 07 14:55:57 ICT 2017
     */
    int updateByExampleSelective(@Param("record") IvbMManager record, @Param("example") IvbMManagerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table IVB_M_MANAGER
     *
     * @mbggenerated Fri Apr 07 14:55:57 ICT 2017
     */
    int updateByExample(@Param("record") IvbMManager record, @Param("example") IvbMManagerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table IVB_M_MANAGER
     *
     * @mbggenerated Fri Apr 07 14:55:57 ICT 2017
     */
    int updateByPrimaryKeySelective(IvbMManager record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table IVB_M_MANAGER
     *
     * @mbggenerated Fri Apr 07 14:55:57 ICT 2017
     */
    int updateByPrimaryKey(IvbMManager record);
}