package cn.dcsy.stsy.service;

import cn.dcsy.stsy.utils.BaseResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;

/**
 * @author DC_DC
 * Date: 2024/4/26/10:23
 */

public interface MailService {
    /**
     * 发送邮件验证码
     *
     * @param request  请求
     * @param email    邮箱
     * @param template 模板
     */
    ResponseEntity<BaseResponse> sendMailCode(HttpServletRequest request, String email, String template);

    /**
     * 验证邮件验证码
     *
     * @param request 请求
     * @param email   邮箱
     * @param code    验证码
     */
    Boolean verifyMailCode(HttpServletRequest request, String email, String code);
    Boolean sendMail(@NotNull String email, @NotNull HashMap<String, Object> prepareData, @NotNull String template);
}
