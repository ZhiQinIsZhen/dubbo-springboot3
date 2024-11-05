package com.liyz.boot3.generation.mybatis.mapper;

import com.liyz.boot3.generation.mybatis.dataobject.RiskControllerRecordDO;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * 由于需要对分页支持,请直接使用对应的DAO类
 * The Table risk_controller_record.
 * 风控指标表
 */
public interface RiskControllerRecordDOMapper{

    /**
     * desc:插入表:risk_controller_record.<br/>
     * @param entity entity
     * @return int
     */
    int insert(RiskControllerRecordDO entity);
    /**
     * desc:批量插入表:risk_controller_record.<br/>
     * @param list list
     * @return int
     */
    int insertBatch(List<RiskControllerRecordDO> list);
    /**
     * desc:根据主键删除数据:risk_controller_record.<br/>
     * @param id id
     * @return int
     */
    int deleteById(Long id);
    /**
     * desc:根据主键获取数据:risk_controller_record.<br/>
     * @param id id
     * @return RiskControllerRecordDO
     */
    RiskControllerRecordDO getById(Long id);
    /**
     * desc:根据普通索引IdxShcoolBizCode获取数据:risk_controller_record.<br/>
     * @param bizCode bizCode
     * @param schoolCode schoolCode
     * @return List<RiskControllerRecordDO>
     */
    List<RiskControllerRecordDO> queryByIdxShcoolBizCode(@Param("bizCode")Long bizCode,@Param("schoolCode")Long schoolCode);
    /**
     * desc:根据普通索引IdxShcoolSemesterStud获取数据:risk_controller_record.<br/>
     * @param schoolCode schoolCode
     * @param studentCode studentCode
     * @param semesterCode semesterCode
     * @return List<RiskControllerRecordDO>
     */
    List<RiskControllerRecordDO> queryByIdxShcoolSemesterStud(@Param("schoolCode")Long schoolCode,@Param("studentCode")Long studentCode,@Param("semesterCode")Long semesterCode);
}
