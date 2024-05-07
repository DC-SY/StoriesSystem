package cn.dcsy.stsy.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 错误码
 * <hr/>
 *
 * @author DC_DC
 * Date: 2024/4/22/14:47
 */
@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    REQUEST_BODY_ERROR("RequestBodyError", 40001, "请求体错误"),
    REQUEST_PARAM_ERROR("RequestParamError", 40002, "请求参数错误"),
    REQUEST_METHOD_ERROR("RequestMethodError", 40003, "请求方法错误"),
    REQUEST_HEADER_ERROR("RequestHeaderError", 40004, "请求头错误"),
    REQUEST_URL_ERROR("RequestUrlError", 40005, "请求地址错误"),

    INSERT_DATA_ERROR("InsertDataError", 40010, "插入数据失败"),
    UPDATE_DATA_ERROR("UpdateDataError", 40011, "更新数据失败"),
    DELETE_DATA_ERROR("DeleteDataError", 40012, "删除数据失败"),
    SELECT_DATA_ERROR("SelectDataError", 40013, "查询数据失败"),

    USER_IS_EXIST("UserIsExist", 40020, "用户已存在"),
    USER_IS_NOT_EXIST("UserIsNotExist", 40021, "用户不存在"),
    USER_PASSWORD_ERROR("UserPasswordError", 40022, "用户密码错误"),
    USER_NOT_LOGIN("UserNotLogin", 40023, "用户未登录"),
    USER_NOT_PERMISSION("UserNotPermission", 40024, "用户没有权限"),
    USER_NOT_AUTHENTICATION("UserNotAuthentication", 40025, "用户未认证"),

    EMAIL_EXIST("EmailExist", 40030, "邮箱已存在"),
    EMAIL_CODE_ERROR("EmailCodeError", 40031, "邮箱验证码错误"),
    SEND_MAIL_ERROR("SendMailError", 40090, "发送邮件失败"),

    SERVER_INTERNAL_ERROR("ServerInternalError", 50000, "服务器内部错误");
    private final String output;
    private final Integer code;
    private final String message;
}
