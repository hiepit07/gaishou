package vn.bananavietnam.ict.common.db.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class IvbTBlockHarvestExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table IVB_T_BLOCK_HARVEST
     *
     * @mbggenerated Fri Apr 07 14:55:57 ICT 2017
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table IVB_T_BLOCK_HARVEST
     *
     * @mbggenerated Fri Apr 07 14:55:57 ICT 2017
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table IVB_T_BLOCK_HARVEST
     *
     * @mbggenerated Fri Apr 07 14:55:57 ICT 2017
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table IVB_T_BLOCK_HARVEST
     *
     * @mbggenerated Fri Apr 07 14:55:57 ICT 2017
     */
    public IvbTBlockHarvestExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table IVB_T_BLOCK_HARVEST
     *
     * @mbggenerated Fri Apr 07 14:55:57 ICT 2017
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table IVB_T_BLOCK_HARVEST
     *
     * @mbggenerated Fri Apr 07 14:55:57 ICT 2017
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table IVB_T_BLOCK_HARVEST
     *
     * @mbggenerated Fri Apr 07 14:55:57 ICT 2017
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table IVB_T_BLOCK_HARVEST
     *
     * @mbggenerated Fri Apr 07 14:55:57 ICT 2017
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table IVB_T_BLOCK_HARVEST
     *
     * @mbggenerated Fri Apr 07 14:55:57 ICT 2017
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table IVB_T_BLOCK_HARVEST
     *
     * @mbggenerated Fri Apr 07 14:55:57 ICT 2017
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table IVB_T_BLOCK_HARVEST
     *
     * @mbggenerated Fri Apr 07 14:55:57 ICT 2017
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table IVB_T_BLOCK_HARVEST
     *
     * @mbggenerated Fri Apr 07 14:55:57 ICT 2017
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table IVB_T_BLOCK_HARVEST
     *
     * @mbggenerated Fri Apr 07 14:55:57 ICT 2017
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table IVB_T_BLOCK_HARVEST
     *
     * @mbggenerated Fri Apr 07 14:55:57 ICT 2017
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table IVB_T_BLOCK_HARVEST
     *
     * @mbggenerated Fri Apr 07 14:55:57 ICT 2017
     */
    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        protected void addCriterionForJDBCDate(String condition, Date value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value.getTime()), property);
        }

        protected void addCriterionForJDBCDate(String condition, List<Date> values, String property) {
            if (values == null || values.size() == 0) {
                throw new RuntimeException("Value list for " + property + " cannot be null or empty");
            }
            List<java.sql.Date> dateList = new ArrayList<java.sql.Date>();
            Iterator<Date> iter = values.iterator();
            while (iter.hasNext()) {
                dateList.add(new java.sql.Date(iter.next().getTime()));
            }
            addCriterion(condition, dateList, property);
        }

        protected void addCriterionForJDBCDate(String condition, Date value1, Date value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value1.getTime()), new java.sql.Date(value2.getTime()), property);
        }

        public Criteria andFarmIdIsNull() {
            addCriterion("FARM_ID is null");
            return (Criteria) this;
        }

        public Criteria andFarmIdIsNotNull() {
            addCriterion("FARM_ID is not null");
            return (Criteria) this;
        }

        public Criteria andFarmIdEqualTo(String value) {
            addCriterion("FARM_ID =", value, "farmId");
            return (Criteria) this;
        }

        public Criteria andFarmIdNotEqualTo(String value) {
            addCriterion("FARM_ID <>", value, "farmId");
            return (Criteria) this;
        }

        public Criteria andFarmIdGreaterThan(String value) {
            addCriterion("FARM_ID >", value, "farmId");
            return (Criteria) this;
        }

        public Criteria andFarmIdGreaterThanOrEqualTo(String value) {
            addCriterion("FARM_ID >=", value, "farmId");
            return (Criteria) this;
        }

        public Criteria andFarmIdLessThan(String value) {
            addCriterion("FARM_ID <", value, "farmId");
            return (Criteria) this;
        }

        public Criteria andFarmIdLessThanOrEqualTo(String value) {
            addCriterion("FARM_ID <=", value, "farmId");
            return (Criteria) this;
        }

        public Criteria andFarmIdLike(String value) {
            addCriterion("FARM_ID like", value, "farmId");
            return (Criteria) this;
        }

        public Criteria andFarmIdNotLike(String value) {
            addCriterion("FARM_ID not like", value, "farmId");
            return (Criteria) this;
        }

        public Criteria andFarmIdIn(List<String> values) {
            addCriterion("FARM_ID in", values, "farmId");
            return (Criteria) this;
        }

        public Criteria andFarmIdNotIn(List<String> values) {
            addCriterion("FARM_ID not in", values, "farmId");
            return (Criteria) this;
        }

        public Criteria andFarmIdBetween(String value1, String value2) {
            addCriterion("FARM_ID between", value1, value2, "farmId");
            return (Criteria) this;
        }

        public Criteria andFarmIdNotBetween(String value1, String value2) {
            addCriterion("FARM_ID not between", value1, value2, "farmId");
            return (Criteria) this;
        }

        public Criteria andAreaIdIsNull() {
            addCriterion("AREA_ID is null");
            return (Criteria) this;
        }

        public Criteria andAreaIdIsNotNull() {
            addCriterion("AREA_ID is not null");
            return (Criteria) this;
        }

        public Criteria andAreaIdEqualTo(String value) {
            addCriterion("AREA_ID =", value, "areaId");
            return (Criteria) this;
        }

        public Criteria andAreaIdNotEqualTo(String value) {
            addCriterion("AREA_ID <>", value, "areaId");
            return (Criteria) this;
        }

        public Criteria andAreaIdGreaterThan(String value) {
            addCriterion("AREA_ID >", value, "areaId");
            return (Criteria) this;
        }

        public Criteria andAreaIdGreaterThanOrEqualTo(String value) {
            addCriterion("AREA_ID >=", value, "areaId");
            return (Criteria) this;
        }

        public Criteria andAreaIdLessThan(String value) {
            addCriterion("AREA_ID <", value, "areaId");
            return (Criteria) this;
        }

        public Criteria andAreaIdLessThanOrEqualTo(String value) {
            addCriterion("AREA_ID <=", value, "areaId");
            return (Criteria) this;
        }

        public Criteria andAreaIdLike(String value) {
            addCriterion("AREA_ID like", value, "areaId");
            return (Criteria) this;
        }

        public Criteria andAreaIdNotLike(String value) {
            addCriterion("AREA_ID not like", value, "areaId");
            return (Criteria) this;
        }

        public Criteria andAreaIdIn(List<String> values) {
            addCriterion("AREA_ID in", values, "areaId");
            return (Criteria) this;
        }

        public Criteria andAreaIdNotIn(List<String> values) {
            addCriterion("AREA_ID not in", values, "areaId");
            return (Criteria) this;
        }

        public Criteria andAreaIdBetween(String value1, String value2) {
            addCriterion("AREA_ID between", value1, value2, "areaId");
            return (Criteria) this;
        }

        public Criteria andAreaIdNotBetween(String value1, String value2) {
            addCriterion("AREA_ID not between", value1, value2, "areaId");
            return (Criteria) this;
        }

        public Criteria andBlockIdIsNull() {
            addCriterion("BLOCK_ID is null");
            return (Criteria) this;
        }

        public Criteria andBlockIdIsNotNull() {
            addCriterion("BLOCK_ID is not null");
            return (Criteria) this;
        }

        public Criteria andBlockIdEqualTo(String value) {
            addCriterion("BLOCK_ID =", value, "blockId");
            return (Criteria) this;
        }

        public Criteria andBlockIdNotEqualTo(String value) {
            addCriterion("BLOCK_ID <>", value, "blockId");
            return (Criteria) this;
        }

        public Criteria andBlockIdGreaterThan(String value) {
            addCriterion("BLOCK_ID >", value, "blockId");
            return (Criteria) this;
        }

        public Criteria andBlockIdGreaterThanOrEqualTo(String value) {
            addCriterion("BLOCK_ID >=", value, "blockId");
            return (Criteria) this;
        }

        public Criteria andBlockIdLessThan(String value) {
            addCriterion("BLOCK_ID <", value, "blockId");
            return (Criteria) this;
        }

        public Criteria andBlockIdLessThanOrEqualTo(String value) {
            addCriterion("BLOCK_ID <=", value, "blockId");
            return (Criteria) this;
        }

        public Criteria andBlockIdLike(String value) {
            addCriterion("BLOCK_ID like", value, "blockId");
            return (Criteria) this;
        }

        public Criteria andBlockIdNotLike(String value) {
            addCriterion("BLOCK_ID not like", value, "blockId");
            return (Criteria) this;
        }

        public Criteria andBlockIdIn(List<String> values) {
            addCriterion("BLOCK_ID in", values, "blockId");
            return (Criteria) this;
        }

        public Criteria andBlockIdNotIn(List<String> values) {
            addCriterion("BLOCK_ID not in", values, "blockId");
            return (Criteria) this;
        }

        public Criteria andBlockIdBetween(String value1, String value2) {
            addCriterion("BLOCK_ID between", value1, value2, "blockId");
            return (Criteria) this;
        }

        public Criteria andBlockIdNotBetween(String value1, String value2) {
            addCriterion("BLOCK_ID not between", value1, value2, "blockId");
            return (Criteria) this;
        }

        public Criteria andPreviousRoundIsNull() {
            addCriterion("PREVIOUS_ROUND is null");
            return (Criteria) this;
        }

        public Criteria andPreviousRoundIsNotNull() {
            addCriterion("PREVIOUS_ROUND is not null");
            return (Criteria) this;
        }

        public Criteria andPreviousRoundEqualTo(Boolean value) {
            addCriterion("PREVIOUS_ROUND =", value, "previousRound");
            return (Criteria) this;
        }

        public Criteria andPreviousRoundNotEqualTo(Boolean value) {
            addCriterion("PREVIOUS_ROUND <>", value, "previousRound");
            return (Criteria) this;
        }

        public Criteria andPreviousRoundGreaterThan(Boolean value) {
            addCriterion("PREVIOUS_ROUND >", value, "previousRound");
            return (Criteria) this;
        }

        public Criteria andPreviousRoundGreaterThanOrEqualTo(Boolean value) {
            addCriterion("PREVIOUS_ROUND >=", value, "previousRound");
            return (Criteria) this;
        }

        public Criteria andPreviousRoundLessThan(Boolean value) {
            addCriterion("PREVIOUS_ROUND <", value, "previousRound");
            return (Criteria) this;
        }

        public Criteria andPreviousRoundLessThanOrEqualTo(Boolean value) {
            addCriterion("PREVIOUS_ROUND <=", value, "previousRound");
            return (Criteria) this;
        }

        public Criteria andPreviousRoundIn(List<Boolean> values) {
            addCriterion("PREVIOUS_ROUND in", values, "previousRound");
            return (Criteria) this;
        }

        public Criteria andPreviousRoundNotIn(List<Boolean> values) {
            addCriterion("PREVIOUS_ROUND not in", values, "previousRound");
            return (Criteria) this;
        }

        public Criteria andPreviousRoundBetween(Boolean value1, Boolean value2) {
            addCriterion("PREVIOUS_ROUND between", value1, value2, "previousRound");
            return (Criteria) this;
        }

        public Criteria andPreviousRoundNotBetween(Boolean value1, Boolean value2) {
            addCriterion("PREVIOUS_ROUND not between", value1, value2, "previousRound");
            return (Criteria) this;
        }

        public Criteria andBlockHarvestDateIsNull() {
            addCriterion("BLOCK_HARVEST_DATE is null");
            return (Criteria) this;
        }

        public Criteria andBlockHarvestDateIsNotNull() {
            addCriterion("BLOCK_HARVEST_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andBlockHarvestDateEqualTo(Date value) {
            addCriterionForJDBCDate("BLOCK_HARVEST_DATE =", value, "blockHarvestDate");
            return (Criteria) this;
        }

        public Criteria andBlockHarvestDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("BLOCK_HARVEST_DATE <>", value, "blockHarvestDate");
            return (Criteria) this;
        }

        public Criteria andBlockHarvestDateGreaterThan(Date value) {
            addCriterionForJDBCDate("BLOCK_HARVEST_DATE >", value, "blockHarvestDate");
            return (Criteria) this;
        }

        public Criteria andBlockHarvestDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("BLOCK_HARVEST_DATE >=", value, "blockHarvestDate");
            return (Criteria) this;
        }

        public Criteria andBlockHarvestDateLessThan(Date value) {
            addCriterionForJDBCDate("BLOCK_HARVEST_DATE <", value, "blockHarvestDate");
            return (Criteria) this;
        }

        public Criteria andBlockHarvestDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("BLOCK_HARVEST_DATE <=", value, "blockHarvestDate");
            return (Criteria) this;
        }

        public Criteria andBlockHarvestDateIn(List<Date> values) {
            addCriterionForJDBCDate("BLOCK_HARVEST_DATE in", values, "blockHarvestDate");
            return (Criteria) this;
        }

        public Criteria andBlockHarvestDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("BLOCK_HARVEST_DATE not in", values, "blockHarvestDate");
            return (Criteria) this;
        }

        public Criteria andBlockHarvestDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("BLOCK_HARVEST_DATE between", value1, value2, "blockHarvestDate");
            return (Criteria) this;
        }

        public Criteria andBlockHarvestDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("BLOCK_HARVEST_DATE not between", value1, value2, "blockHarvestDate");
            return (Criteria) this;
        }

        public Criteria andBlockYieldIsNull() {
            addCriterion("BLOCK_YIELD is null");
            return (Criteria) this;
        }

        public Criteria andBlockYieldIsNotNull() {
            addCriterion("BLOCK_YIELD is not null");
            return (Criteria) this;
        }

        public Criteria andBlockYieldEqualTo(Double value) {
            addCriterion("BLOCK_YIELD =", value, "blockYield");
            return (Criteria) this;
        }

        public Criteria andBlockYieldNotEqualTo(Double value) {
            addCriterion("BLOCK_YIELD <>", value, "blockYield");
            return (Criteria) this;
        }

        public Criteria andBlockYieldGreaterThan(Double value) {
            addCriterion("BLOCK_YIELD >", value, "blockYield");
            return (Criteria) this;
        }

        public Criteria andBlockYieldGreaterThanOrEqualTo(Double value) {
            addCriterion("BLOCK_YIELD >=", value, "blockYield");
            return (Criteria) this;
        }

        public Criteria andBlockYieldLessThan(Double value) {
            addCriterion("BLOCK_YIELD <", value, "blockYield");
            return (Criteria) this;
        }

        public Criteria andBlockYieldLessThanOrEqualTo(Double value) {
            addCriterion("BLOCK_YIELD <=", value, "blockYield");
            return (Criteria) this;
        }

        public Criteria andBlockYieldIn(List<Double> values) {
            addCriterion("BLOCK_YIELD in", values, "blockYield");
            return (Criteria) this;
        }

        public Criteria andBlockYieldNotIn(List<Double> values) {
            addCriterion("BLOCK_YIELD not in", values, "blockYield");
            return (Criteria) this;
        }

        public Criteria andBlockYieldBetween(Double value1, Double value2) {
            addCriterion("BLOCK_YIELD between", value1, value2, "blockYield");
            return (Criteria) this;
        }

        public Criteria andBlockYieldNotBetween(Double value1, Double value2) {
            addCriterion("BLOCK_YIELD not between", value1, value2, "blockYield");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdIsNull() {
            addCriterion("CREATE_USER_ID is null");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdIsNotNull() {
            addCriterion("CREATE_USER_ID is not null");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdEqualTo(String value) {
            addCriterion("CREATE_USER_ID =", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdNotEqualTo(String value) {
            addCriterion("CREATE_USER_ID <>", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdGreaterThan(String value) {
            addCriterion("CREATE_USER_ID >", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdGreaterThanOrEqualTo(String value) {
            addCriterion("CREATE_USER_ID >=", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdLessThan(String value) {
            addCriterion("CREATE_USER_ID <", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdLessThanOrEqualTo(String value) {
            addCriterion("CREATE_USER_ID <=", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdLike(String value) {
            addCriterion("CREATE_USER_ID like", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdNotLike(String value) {
            addCriterion("CREATE_USER_ID not like", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdIn(List<String> values) {
            addCriterion("CREATE_USER_ID in", values, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdNotIn(List<String> values) {
            addCriterion("CREATE_USER_ID not in", values, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdBetween(String value1, String value2) {
            addCriterion("CREATE_USER_ID between", value1, value2, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdNotBetween(String value1, String value2) {
            addCriterion("CREATE_USER_ID not between", value1, value2, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateDateIsNull() {
            addCriterion("CREATE_DATE is null");
            return (Criteria) this;
        }

        public Criteria andCreateDateIsNotNull() {
            addCriterion("CREATE_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andCreateDateEqualTo(Date value) {
            addCriterion("CREATE_DATE =", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotEqualTo(Date value) {
            addCriterion("CREATE_DATE <>", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateGreaterThan(Date value) {
            addCriterion("CREATE_DATE >", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateGreaterThanOrEqualTo(Date value) {
            addCriterion("CREATE_DATE >=", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateLessThan(Date value) {
            addCriterion("CREATE_DATE <", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateLessThanOrEqualTo(Date value) {
            addCriterion("CREATE_DATE <=", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateIn(List<Date> values) {
            addCriterion("CREATE_DATE in", values, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotIn(List<Date> values) {
            addCriterion("CREATE_DATE not in", values, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateBetween(Date value1, Date value2) {
            addCriterion("CREATE_DATE between", value1, value2, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotBetween(Date value1, Date value2) {
            addCriterion("CREATE_DATE not between", value1, value2, "createDate");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIdIsNull() {
            addCriterion("UPDATE_USER_ID is null");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIdIsNotNull() {
            addCriterion("UPDATE_USER_ID is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIdEqualTo(String value) {
            addCriterion("UPDATE_USER_ID =", value, "updateUserId");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIdNotEqualTo(String value) {
            addCriterion("UPDATE_USER_ID <>", value, "updateUserId");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIdGreaterThan(String value) {
            addCriterion("UPDATE_USER_ID >", value, "updateUserId");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIdGreaterThanOrEqualTo(String value) {
            addCriterion("UPDATE_USER_ID >=", value, "updateUserId");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIdLessThan(String value) {
            addCriterion("UPDATE_USER_ID <", value, "updateUserId");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIdLessThanOrEqualTo(String value) {
            addCriterion("UPDATE_USER_ID <=", value, "updateUserId");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIdLike(String value) {
            addCriterion("UPDATE_USER_ID like", value, "updateUserId");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIdNotLike(String value) {
            addCriterion("UPDATE_USER_ID not like", value, "updateUserId");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIdIn(List<String> values) {
            addCriterion("UPDATE_USER_ID in", values, "updateUserId");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIdNotIn(List<String> values) {
            addCriterion("UPDATE_USER_ID not in", values, "updateUserId");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIdBetween(String value1, String value2) {
            addCriterion("UPDATE_USER_ID between", value1, value2, "updateUserId");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIdNotBetween(String value1, String value2) {
            addCriterion("UPDATE_USER_ID not between", value1, value2, "updateUserId");
            return (Criteria) this;
        }

        public Criteria andLastUpdateDateIsNull() {
            addCriterion("LAST_UPDATE_DATE is null");
            return (Criteria) this;
        }

        public Criteria andLastUpdateDateIsNotNull() {
            addCriterion("LAST_UPDATE_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andLastUpdateDateEqualTo(Date value) {
            addCriterion("LAST_UPDATE_DATE =", value, "lastUpdateDate");
            return (Criteria) this;
        }

        public Criteria andLastUpdateDateNotEqualTo(Date value) {
            addCriterion("LAST_UPDATE_DATE <>", value, "lastUpdateDate");
            return (Criteria) this;
        }

        public Criteria andLastUpdateDateGreaterThan(Date value) {
            addCriterion("LAST_UPDATE_DATE >", value, "lastUpdateDate");
            return (Criteria) this;
        }

        public Criteria andLastUpdateDateGreaterThanOrEqualTo(Date value) {
            addCriterion("LAST_UPDATE_DATE >=", value, "lastUpdateDate");
            return (Criteria) this;
        }

        public Criteria andLastUpdateDateLessThan(Date value) {
            addCriterion("LAST_UPDATE_DATE <", value, "lastUpdateDate");
            return (Criteria) this;
        }

        public Criteria andLastUpdateDateLessThanOrEqualTo(Date value) {
            addCriterion("LAST_UPDATE_DATE <=", value, "lastUpdateDate");
            return (Criteria) this;
        }

        public Criteria andLastUpdateDateIn(List<Date> values) {
            addCriterion("LAST_UPDATE_DATE in", values, "lastUpdateDate");
            return (Criteria) this;
        }

        public Criteria andLastUpdateDateNotIn(List<Date> values) {
            addCriterion("LAST_UPDATE_DATE not in", values, "lastUpdateDate");
            return (Criteria) this;
        }

        public Criteria andLastUpdateDateBetween(Date value1, Date value2) {
            addCriterion("LAST_UPDATE_DATE between", value1, value2, "lastUpdateDate");
            return (Criteria) this;
        }

        public Criteria andLastUpdateDateNotBetween(Date value1, Date value2) {
            addCriterion("LAST_UPDATE_DATE not between", value1, value2, "lastUpdateDate");
            return (Criteria) this;
        }

        public Criteria andDeleteFlagIsNull() {
            addCriterion("DELETE_FLAG is null");
            return (Criteria) this;
        }

        public Criteria andDeleteFlagIsNotNull() {
            addCriterion("DELETE_FLAG is not null");
            return (Criteria) this;
        }

        public Criteria andDeleteFlagEqualTo(Boolean value) {
            addCriterion("DELETE_FLAG =", value, "deleteFlag");
            return (Criteria) this;
        }

        public Criteria andDeleteFlagNotEqualTo(Boolean value) {
            addCriterion("DELETE_FLAG <>", value, "deleteFlag");
            return (Criteria) this;
        }

        public Criteria andDeleteFlagGreaterThan(Boolean value) {
            addCriterion("DELETE_FLAG >", value, "deleteFlag");
            return (Criteria) this;
        }

        public Criteria andDeleteFlagGreaterThanOrEqualTo(Boolean value) {
            addCriterion("DELETE_FLAG >=", value, "deleteFlag");
            return (Criteria) this;
        }

        public Criteria andDeleteFlagLessThan(Boolean value) {
            addCriterion("DELETE_FLAG <", value, "deleteFlag");
            return (Criteria) this;
        }

        public Criteria andDeleteFlagLessThanOrEqualTo(Boolean value) {
            addCriterion("DELETE_FLAG <=", value, "deleteFlag");
            return (Criteria) this;
        }

        public Criteria andDeleteFlagIn(List<Boolean> values) {
            addCriterion("DELETE_FLAG in", values, "deleteFlag");
            return (Criteria) this;
        }

        public Criteria andDeleteFlagNotIn(List<Boolean> values) {
            addCriterion("DELETE_FLAG not in", values, "deleteFlag");
            return (Criteria) this;
        }

        public Criteria andDeleteFlagBetween(Boolean value1, Boolean value2) {
            addCriterion("DELETE_FLAG between", value1, value2, "deleteFlag");
            return (Criteria) this;
        }

        public Criteria andDeleteFlagNotBetween(Boolean value1, Boolean value2) {
            addCriterion("DELETE_FLAG not between", value1, value2, "deleteFlag");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table IVB_T_BLOCK_HARVEST
     *
     * @mbggenerated do_not_delete_during_merge Fri Apr 07 14:55:57 ICT 2017
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table IVB_T_BLOCK_HARVEST
     *
     * @mbggenerated Fri Apr 07 14:55:57 ICT 2017
     */
    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}