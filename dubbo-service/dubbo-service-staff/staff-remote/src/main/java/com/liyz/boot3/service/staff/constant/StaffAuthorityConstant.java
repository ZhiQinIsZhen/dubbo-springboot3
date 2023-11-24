package com.liyz.boot3.service.staff.constant;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/11/24 15:21
 */
public interface StaffAuthorityConstant {

    String STAFF_INFO = "DUBBO-API-ADMIN:STAFFINFO";

    default String getStaffInfo() {
        return STAFF_INFO;
    }
}
