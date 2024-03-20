package com.liyz.boot3.api.user.dto.search.bid;

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
public class BidTenderCountDTO implements Serializable {
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
     * 参与方名称
     */
    private String partyName;

    /**
     * 参与方数量
     */
    private String partyCount;

    private Integer type;
}
