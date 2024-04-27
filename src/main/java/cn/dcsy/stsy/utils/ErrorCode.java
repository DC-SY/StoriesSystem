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

    SEND_MAIL_ERROR("SendMailError", 40090, "发送邮件失败");
    private final String output;
    private final Integer code;
    private final String message;
}
