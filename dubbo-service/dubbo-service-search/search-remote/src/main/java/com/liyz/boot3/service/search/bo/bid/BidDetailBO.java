package com.liyz.boot3.service.search.bo.bid;

import com.liyz.boot3.service.search.bo.BaseBO;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/11/14 14:51
 */
@Getter
@Setter
public class BidDetailBO extends BaseBO {
    @Serial
    private static final long serialVersionUID = 5710585117072286084L;

    private String uniqueId;

    private String link;

    private String title;

    private Date publishTime;

    private String province;

    private String city;

    private String noticeType;

    private String noticeTypeSub;

    private String businessType;

    private String publishTimeYear;

    private List<PartyBO> party;

    @Getter
    @Setter
    public static class PartyBO implements Serializable {
        @Serial
        private static final long serialVersionUID = -4756023745826708888L;

        /**
         * 公司ID
         */
        private String companyId;

        /**
         * 参与方名称
         */
        private String partyName;

        /**
         * 参与方类型
         */
        private String partyType;

        /**
         * 省份
         */
        private String province;

        /**
         * 最高预算价
         */
        private String maxBudget;
    }
}
