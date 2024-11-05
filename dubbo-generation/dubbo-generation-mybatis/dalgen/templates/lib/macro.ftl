<#macro SqlBaseColumnList table id prex>
    <sql id="${id}">
        <#list table.columnList as column><#if column_index gt 0><#if column_index%5==0>${" \n"}        </#if>,</#if>${prex?default("")}${column.sqlName}</#list>
    </sql>
</#macro>

<#macro BaseColumnList table>
    <@SqlBaseColumnList table,"Base_Column_List","" />
</#macro>

<#macro Base_SF_Column_List table>
    <@SqlBaseColumnList table,"Base_SF_Column_List","sf." />
</#macro>

<#macro insertMethod table>
            <#if dalgen.dbType=="MySQL">
            <selectKey resultType="java.lang.Long" keyProperty="id" order="AFTER">
                SELECT
                LAST_INSERT_ID()
            </selectKey>
            <#elseif dalgen.dbType=="Oracle">
            <selectKey keyProperty="id" resultType="DECIMAL" order="BEFORE">
                select seq_${table.sqlName?lower_case}.nextval from dual
            </selectKey>
            </#if>
            <![CDATA[
            INSERT INTO ${table.sqlName?lower_case}(
                <#list table.columnList as column>
                    <#if column_index gt 0>,</#if>${column.sqlName}
                </#list>
            )VALUES(
                <#list table.columnList as column>
                    <#if column_index gt 0>,</#if> ${lib.insertVal(column,dalgen)}
                </#list>
            )
            ]]>
</#macro>


<#macro insertBatchMethod table>
            INSERT INTO ${table.sqlName?lower_case}(
                <#list table.columnList as column>
                    <#if column_index gt 0>,</#if>${column.sqlName}
                </#list>
            )VALUES
            <foreach collection="list"  item="item" separator=",">
                (
                <#list table.columnList as column>
                    <#if column_index gt 0>,</#if> ${lib.insertBatchVal(column,dalgen)}
                </#list>
                )
            </foreach>
</#macro>

<#macro updateMethod table>
        <![CDATA[
        UPDATE ${table.sqlName?lower_case}
        SET
            <#assign c_idx = 0>
            <#list table.columnList as column>
                <#if lib.updateIncludeColumn(dalgen,column,table.primaryKeys.columnList)><#assign c_idx = c_idx+1>
                    <#if c_idx gt 1>,</#if>${column.sqlName}${lib.space(column.sqlName)} = ${lib.updateVal(column)}
                </#if>
            </#list>
        WHERE
            <#list table.primaryKeys.columnList as column>
                <#if column_index gt 0>AND </#if>${column.sqlName}${lib.space(column.sqlName)} = ${"#"}{${column.javaName},jdbcType=${column.sqlType}}
            </#list>
        ]]>
</#macro>


<#macro autoGen method table>
    <#if method=="insert">
        <@insertMethod table/>
    <#elseif method=="insertBatch">
        <@insertBatchMethod table/>
    <#elseif method=="update">
        <@updateMethod table/>
    </#if>
</#macro>

