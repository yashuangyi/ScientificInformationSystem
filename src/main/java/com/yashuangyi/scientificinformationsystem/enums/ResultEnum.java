package com.yashuangyi.scientificinformationsystem.enums;

import lombok.Getter;

/**
 * @author yashuangyi
 * @version 1.0
 * @content
 * @date 2020-04-21 13:11
 */
@Getter
public enum ResultEnum {
    /**
     * 测试用
     */

    NO_CONTENT(201,"无内容"),
    TEST(404, "测试"),
    FAIL(506, "失败"),
    USER_ACCOUNT_ALREADY_EXIST(507, "账户已经存在"),
    USER_PASSWORD_ERROR(511,"密码错误"),
    USER_NOT_EXISTED(511,"用户不存在"),
    DATA_ERROR(512,"空指针异常"),
    USER_PERMISSION_DENIED(403,"用户权限不足"),
    OLD_PASSWORD_ERROR(514,"原密码错误"),
    FILE_NOT_FOUND(515,"文件找不到！");

    private Integer code;
    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
