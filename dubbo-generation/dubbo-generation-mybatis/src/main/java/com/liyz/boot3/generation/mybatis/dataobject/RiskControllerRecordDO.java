package com.liyz.boot3.generation.mybatis.dataobject;

import java.util.Date;

/**
 * The table 风控指标表
 */
public class RiskControllerRecordDO{

    /**
     * id 主键.
     */
    private Long id;
    /**
     * bizCode 业务code.
     */
    private Long bizCode;
    /**
     * schoolCode 关联学校主键code.
     */
    private Long schoolCode;
    /**
     * runPlanCode 跑步计划code.
     */
    private Long runPlanCode;
    /**
     * studentCode 关联学生主键Code.
     */
    private Long studentCode;
    /**
     * semesterCode 关联学期主键Code.
     */
    private Long semesterCode;
    /**
     * reason 记录原因.
     */
    private String reason;
    /**
     * indexName 风控指标名称.
     */
    private String indexName;
    /**
     * indexType 风控指标类型.
     */
    private String indexType;
    /**
     * rcType 风控类型 1-跑步记录.
     */
    private Integer rcType;
    /**
     * delFlag 是否删除 0-未删除，1-已删除.
     */
    private Integer delFlag;
    /**
     * createTime 创建时间.
     */
    private Date createTime;
    /**
     * updateTime 更新时间.
     */
    private Date updateTime;

    /**
     * Set id 主键.
     */
    public void setId(Long id){
        this.id = id;
    }

    /**
     * Get id 主键.
     *
     * @return the string
     */
    public Long getId(){
        return id;
    }

    /**
     * Set bizCode 业务code.
     */
    public void setBizCode(Long bizCode){
        this.bizCode = bizCode;
    }

    /**
     * Get bizCode 业务code.
     *
     * @return the string
     */
    public Long getBizCode(){
        return bizCode;
    }

    /**
     * Set schoolCode 关联学校主键code.
     */
    public void setSchoolCode(Long schoolCode){
        this.schoolCode = schoolCode;
    }

    /**
     * Get schoolCode 关联学校主键code.
     *
     * @return the string
     */
    public Long getSchoolCode(){
        return schoolCode;
    }

    /**
     * Set runPlanCode 跑步计划code.
     */
    public void setRunPlanCode(Long runPlanCode){
        this.runPlanCode = runPlanCode;
    }

    /**
     * Get runPlanCode 跑步计划code.
     *
     * @return the string
     */
    public Long getRunPlanCode(){
        return runPlanCode;
    }

    /**
     * Set studentCode 关联学生主键Code.
     */
    public void setStudentCode(Long studentCode){
        this.studentCode = studentCode;
    }

    /**
     * Get studentCode 关联学生主键Code.
     *
     * @return the string
     */
    public Long getStudentCode(){
        return studentCode;
    }

    /**
     * Set semesterCode 关联学期主键Code.
     */
    public void setSemesterCode(Long semesterCode){
        this.semesterCode = semesterCode;
    }

    /**
     * Get semesterCode 关联学期主键Code.
     *
     * @return the string
     */
    public Long getSemesterCode(){
        return semesterCode;
    }

    /**
     * Set reason 记录原因.
     */
    public void setReason(String reason){
        this.reason = reason;
    }

    /**
     * Get reason 记录原因.
     *
     * @return the string
     */
    public String getReason(){
        return reason;
    }

    /**
     * Set indexName 风控指标名称.
     */
    public void setIndexName(String indexName){
        this.indexName = indexName;
    }

    /**
     * Get indexName 风控指标名称.
     *
     * @return the string
     */
    public String getIndexName(){
        return indexName;
    }

    /**
     * Set indexType 风控指标类型.
     */
    public void setIndexType(String indexType){
        this.indexType = indexType;
    }

    /**
     * Get indexType 风控指标类型.
     *
     * @return the string
     */
    public String getIndexType(){
        return indexType;
    }

    /**
     * Set rcType 风控类型 1-跑步记录.
     */
    public void setRcType(Integer rcType){
        this.rcType = rcType;
    }

    /**
     * Get rcType 风控类型 1-跑步记录.
     *
     * @return the string
     */
    public Integer getRcType(){
        return rcType;
    }

    /**
     * Set delFlag 是否删除 0-未删除，1-已删除.
     */
    public void setDelFlag(Integer delFlag){
        this.delFlag = delFlag;
    }

    /**
     * Get delFlag 是否删除 0-未删除，1-已删除.
     *
     * @return the string
     */
    public Integer getDelFlag(){
        return delFlag;
    }

    /**
     * Set createTime 创建时间.
     */
    public void setCreateTime(Date createTime){
        this.createTime = createTime;
    }

    /**
     * Get createTime 创建时间.
     *
     * @return the string
     */
    public Date getCreateTime(){
        return createTime;
    }

    /**
     * Set updateTime 更新时间.
     */
    public void setUpdateTime(Date updateTime){
        this.updateTime = updateTime;
    }

    /**
     * Get updateTime 更新时间.
     *
     * @return the string
     */
    public Date getUpdateTime(){
        return updateTime;
    }
}
