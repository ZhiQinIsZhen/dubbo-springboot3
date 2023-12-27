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
 * @date 2023/12/26 15:37
 */
@Getter
@Setter
@Document(indexName = "partner-online", createIndex = false)
public class CompanyHolderDO implements Serializable {
    @Serial
    private static final long serialVersionUID = -1671558668081800712L;

    @Id
    private String id;

    @Field(name = "company_id", type = FieldType.Keyword)
    private String companyId;

    @Field(name = "company_name", type = FieldType.Text)
    private String companyName;

    @Field(name = "stock_name", type = FieldType.Keyword)
    private String stockName;

    @Field(name = "stock_name_id", type = FieldType.Keyword)
    private String stockNameId;
}
