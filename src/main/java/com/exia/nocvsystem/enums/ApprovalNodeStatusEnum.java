package com.exia.nocvsystem.enums;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * 请假审批节点的枚举类型
 */
@AllArgsConstructor
@NoArgsConstructor
public enum ApprovalNodeStatusEnum {
    NO_SUBMIT("0","未提交"),
    APPROVAL_TEACHER_ING("1","正在审核中"),
    TEACHER_REJECTED("2","老师驳回"),
    APPROVAL_TEACHER_PASSED("3","老师审核通过"),
    APPROVAL_COLLEGE_ING("4","正在审核中"),
    COLLEGE_REJECTED("5","院系驳回"),
    APPROVAL_COLLEGE_PASSED("6","院系审核通过")



    ;

    private String code;
    private String desc;

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
