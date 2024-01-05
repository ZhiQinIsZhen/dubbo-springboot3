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
 * @date 2023/11/14 9:34
 */
@Getter
@Setter
@Document(indexName = "search-company-auto-20231230", createIndex = false)
public class CompanyDO implements Serializable {
    @Serial
    private static final long serialVersionUID = 170535601509514115L;

    @Id
    private String id;

    @Field(name = "company_id", type = FieldType.Keyword)
    private String companyId;

    @Field(name = "company_name_tag", type = FieldType.Text)
    private String companyNameTag;

    @Field(name = "company_code", type = FieldType.Keyword)
    private String companyCode;

    @Field(name = "credit_no", type = FieldType.Keyword)
    private String creditNo;

    @Field(name = "legal_person_flag", type = FieldType.Keyword)
    private String legalPersonFlag;

    @Field(name = "address_tag", type = FieldType.Keyword)
    private String addressTag;

    @Field(name = "establishment_time", type = FieldType.Long)
    private Long establishmentTime;
}
