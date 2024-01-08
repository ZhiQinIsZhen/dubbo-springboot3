package com.liyz.boot3.service.search.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serial;
import java.io.Serializable;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2024/1/8 13:36
 */
@Getter
@Setter
@Document(indexName = "company-investor", createIndex = false)
public class CompanyInvestorDO implements Serializable {
    @Serial
    private static final long serialVersionUID = 2524475764297876678L;

    @Id
    private String id;

    @Field(name = "company_id", type = FieldType.Keyword)
    private String companyId;

    @Field(name = "company_name", type = FieldType.Keyword)
    private String companyName;

    @Field(name = "investor_name", type = FieldType.Keyword)
    private String investorName;

    @Field(name = "establish_time", type = FieldType.Keyword)
    private String establishTime;

    @Field(name = "introduct", type = FieldType.Keyword)
    private String mark;

    @Field(name = "logo", type = FieldType.Keyword)
    private String logo;
}
