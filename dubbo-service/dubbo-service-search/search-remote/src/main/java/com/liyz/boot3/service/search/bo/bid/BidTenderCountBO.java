package com.liyz.boot3.service.search.bo.bid;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2024/3/18 11:27
 */
@Data
public class BidTenderCountBO implements Serializable {
    @Serial
    private static final long serialVersionUID = 5113896035737947927L;

    /**
     * 唯一主键
     */
    private String _id;

    /**
     * 招标方ID
     */
    private String tenderId;

    /**
     * 招标方名称
     */
    private String tenderName;

    /**
     * 招标方logo地址
     */
    private String tenderLogoUrl;

    /**
     * 企业ID
     */
    private String companyId;

    /**
     * 参与方名称
     */
    private String partyName;

    /**
     * 参与方logo地址
     */
    private String partyLogoUrl;

    /**
     * 参与方类型:2中标方，3代理机构，4其他
     */
    private Integer partyType;


    /**
     * 参与方数量
     */
    private String partyCount;

    /**
     * 生成的唯一键
     */
    private String primaryKey;

    private Integer useFlag;

    private Integer type;
}
