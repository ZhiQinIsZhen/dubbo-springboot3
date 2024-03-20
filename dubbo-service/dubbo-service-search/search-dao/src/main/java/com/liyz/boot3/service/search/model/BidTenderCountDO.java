package com.liyz.boot3.service.search.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

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
@Document(collection = "ads_company_bid_tender_count")
public class BidTenderCountDO implements Serializable {
    @Serial
    private static final long serialVersionUID = -8832039523316972378L;

    /**
     * 唯一主键
     */
    @Id
    private String _id;

    /**
     * 招标方ID
     */
    @Field("tender_id")
    private String tenderId;

    /**
     * 招标方名称
     */
    @Field("tender_name")
    private String tenderName;

    /**
     * 招标方logo地址
     */
    @Field("tender_logo_url")
    private String tenderLogoUrl;

    /**
     * 企业ID
     */
    @Field("company_id")
    private String companyId;

    /**
     * 参与方名称
     */
    @Field("party_name")
    private String partyName;

    /**
     * 参与方logo地址
     */
    @Field("party_logo_url")
    private String partyLogoUrl;

    /**
     * 参与方类型:2中标方，3代理机构，4其他
     */
    @Field("party_type")
    private Integer partyType;


    /**
     * 参与方数量
     */
    @Field("party_count")
    private String partyCount;

    /**
     * 生成的唯一键
     */
    @Field("primary_key")
    private String primaryKey;

    @Field("use_flag")
    private Integer useFlag;
}
