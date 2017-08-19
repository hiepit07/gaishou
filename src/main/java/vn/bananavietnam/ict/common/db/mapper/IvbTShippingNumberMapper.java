package vn.bananavietnam.ict.common.db.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import vn.bananavietnam.ict.common.db.model.IvbTShippingNumber;
import vn.bananavietnam.ict.common.db.model.IvbTShippingNumberExample;

public interface IvbTShippingNumberMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table IVB_T_SHIPPING_NUMBER
     *
     * @mbggenerated Fri Apr 07 14:55:57 ICT 2017
     */
    int countByExample(IvbTShippingNumberExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table IVB_T_SHIPPING_NUMBER
     *
     * @mbggenerated Fri Apr 07 14:55:57 ICT 2017
     */
    int deleteByExample(IvbTShippingNumberExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table IVB_T_SHIPPING_NUMBER
     *
     * @mbggenerated Fri Apr 07 14:55:57 ICT 2017
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table IVB_T_SHIPPING_NUMBER
     *
     * @mbggenerated Fri Apr 07 14:55:57 ICT 2017
     */
    int insert(IvbTShippingNumber record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table IVB_T_SHIPPING_NUMBER
     *
     * @mbggenerated Fri Apr 07 14:55:57 ICT 2017
     */
    int insertSelective(IvbTShippingNumber record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table IVB_T_SHIPPING_NUMBER
     *
     * @mbggenerated Fri Apr 07 14:55:57 ICT 2017
     */
    List<IvbTShippingNumber> selectByExample(IvbTShippingNumberExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table IVB_T_SHIPPING_NUMBER
     *
     * @mbggenerated Fri Apr 07 14:55:57 ICT 2017
     */
    int updateByExampleSelective(@Param("record") IvbTShippingNumber record, @Param("example") IvbTShippingNumberExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table IVB_T_SHIPPING_NUMBER
     *
     * @mbggenerated Fri Apr 07 14:55:57 ICT 2017
     */
    int updateByExample(@Param("record") IvbTShippingNumber record, @Param("example") IvbTShippingNumberExample example);
}