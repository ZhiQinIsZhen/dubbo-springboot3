package com.liyz.boot3.generation.mybatis.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.liyz.boot3.generation.mybatis.dataobject.RiskControllerRecordDO;
import java.util.List;
import com.liyz.boot3.generation.mybatis.mapper.RiskControllerRecordDOMapper;

/**
* The Table risk_controller_record.
* 风控指标表
*/
@Repository
public class RiskControllerRecordDAO{

    @Autowired
    private RiskControllerRecordDOMapper riskControllerRecordDOMapper;

    /**
     * desc:插入表:risk_controller_record.<br/>
     * @param entity entity
     * @return int
     */
    public int insert(RiskControllerRecordDO entity){
        return riskControllerRecordDOMapper.insert(entity);
    }
    /**
     * desc:批量插入表:risk_controller_record.<br/>
     * @param list list
     * @return int
     */
    public int insertBatch(List<RiskControllerRecordDO> list){
        return riskControllerRecordDOMapper.insertBatch(list);
    }
    /**
     * desc:根据主键删除数据:risk_controller_record.<br/>
     * @param id id
     * @return int
     */
    public int deleteById(Long id){
        return riskControllerRecordDOMapper.deleteById(id);
    }
    /**
     * desc:根据主键获取数据:risk_controller_record.<br/>
     * @param id id
     * @return RiskControllerRecordDO
     */
    public RiskControllerRecordDO getById(Long id){
        return riskControllerRecordDOMapper.getById(id);
    }
    /**
     * desc:根据普通索引IdxShcoolBizCode获取数据:risk_controller_record.<br/>
     * @param bizCode bizCode
     * @param schoolCode schoolCode
     * @return List<RiskControllerRecordDO>
     */
    public List<RiskControllerRecordDO> queryByIdxShcoolBizCode(Long bizCode,Long schoolCode){
        return riskControllerRecordDOMapper.queryByIdxShcoolBizCode(bizCode, schoolCode);
    }
    /**
     * desc:根据普通索引IdxShcoolSemesterStud获取数据:risk_controller_record.<br/>
     * @param schoolCode schoolCode
     * @param studentCode studentCode
     * @param semesterCode semesterCode
     * @return List<RiskControllerRecordDO>
     */
    public List<RiskControllerRecordDO> queryByIdxShcoolSemesterStud(Long schoolCode,Long studentCode,Long semesterCode){
        return riskControllerRecordDOMapper.queryByIdxShcoolSemesterStud(schoolCode, studentCode, semesterCode);
    }
}
