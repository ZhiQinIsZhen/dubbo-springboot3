package com.liyz.boot3.service.search.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2024/1/9 14:05
 */
@Getter
@Setter
@Document(indexName = "company-financing-20240109", createIndex = false)
public class CompanyFinancingDO implements Serializable {
    @Serial
    private static final long serialVersionUID = 7747494754239884918L;

    @Id
    private String id;

    @Field(name = "company_id", type = FieldType.Keyword)
    private String companyId;

    @Field(name = "company_name", type = FieldType.Keyword)
    private String companyName;

    @Field(name = "financing_date", type = FieldType.Date)
    private Date financingDate;

    @Field(name = "financing_rounds", type = FieldType.Keyword)
    private String financingRounds;

    @Field(name = "valuation", type = FieldType.Keyword)
    private String valuation;

    @Field(name = "use_flag", type = FieldType.Integer)
    private Integer useFlag;
}
