package com.liyz.boot3.common.search.method;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2024/1/15 19:47
 */
public class SelectPage extends AbstractEsMethod {

    public SelectPage() {
        this(EsMethod.SELECT_PAGE.getMethod());
    }

    public SelectPage(String methodName) {
        super(methodName);
    }
}
