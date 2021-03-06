package vn.bananavietnam.ict.common.db.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import vn.bananavietnam.ict.common.db.model.IvbMFarm;
import vn.bananavietnam.ict.common.db.model.IvbMFarmExample;

public interface IvbMFarmMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table IVB_M_FARM
     *
     * @mbggenerated Tue May 16 09:30:26 ICT 2017
     */
    int countByExample(IvbMFarmExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table IVB_M_FARM
     *
     * @mbggenerated Tue May 16 09:30:26 ICT 2017
     */
    int deleteByExample(IvbMFarmExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table IVB_M_FARM
     *
     * @mbggenerated Tue May 16 09:30:26 ICT 2017
     */
    int deleteByPrimaryKey(String farmId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table IVB_M_FARM
     *
     * @mbggenerated Tue May 16 09:30:26 ICT 2017
     */
    int insert(IvbMFarm record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table IVB_M_FARM
     *
     * @mbggenerated Tue May 16 09:30:26 ICT 2017
     */
    int insertSelective(IvbMFarm record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table IVB_M_FARM
     *
     * @mbggenerated Tue May 16 09:30:26 ICT 2017
     */
    List<IvbMFarm> selectByExample(IvbMFarmExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table IVB_M_FARM
     *
     * @mbggenerated Tue May 16 09:30:26 ICT 2017
     */
    IvbMFarm selectByPrimaryKey(String farmId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table IVB_M_FARM
     *
     * @mbggenerated Tue May 16 09:30:26 ICT 2017
     */
    int updateByExampleSelective(@Param("record") IvbMFarm record, @Param("example") IvbMFarmExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table IVB_M_FARM
     *
     * @mbggenerated Tue May 16 09:30:26 ICT 2017
     */
    int updateByExample(@Param("record") IvbMFarm record, @Param("example") IvbMFarmExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table IVB_M_FARM
     *
     * @mbggenerated Tue May 16 09:30:26 ICT 2017
     */
    int updateByPrimaryKeySelective(IvbMFarm record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table IVB_M_FARM
     *
     * @mbggenerated Tue May 16 09:30:26 ICT 2017
     */
    int updateByPrimaryKey(IvbMFarm record);
}