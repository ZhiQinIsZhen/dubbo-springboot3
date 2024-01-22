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
@Document(indexName = "company_basic", createIndex = false)
public class CompanyBasicDO implements Serializable {
    @Serial
    private static final long serialVersionUID = 170535601509514115L;

    @Id
    private String id;

    @Field(name = "name", type = FieldType.Text)
    private String name;

    @Field(name = "regNumber", type = FieldType.Keyword)
    private String regNumber;

    @Field(name = "creditCode", type = FieldType.Keyword)
    private String creditCode;

    @Field(name = "legalPersonName", type = FieldType.Keyword)
    private String legalPersonName;

    @Field(name = "type", type = FieldType.Long)
    private Long type;

    @Field(name = "companyType", type = FieldType.Long)
    private Long companyType;

    @Field(name = "regCapital", type = FieldType.Keyword)
    private String regCapital;

    @Field(name = "estiblishTime", type = FieldType.Keyword)
    private String estiblishTime;
}
