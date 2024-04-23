package cn.dcsy.stsy.controllers;

import cn.dcsy.stsy.models.voData.MailSendCodeVO;
import cn.dcsy.stsy.utils.BaseResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author DC_DC
 * Date: 2024/4/23/20:26
 */

public class MailController {
    /**
    * sendMailCode
    * <hr/>
    * 用于发送邮件验证码，用户可以通过该接口发送邮件验证码
    *
     * @param mailSendCodeVO 邮件发送验证码的请求参数
     * @param request        请求对象
     * @param bindingResult  请求参数的校验结果
     * @return 返回发送邮件验证码的结果
    * */
    @PostMapping("/send")
    public ResponseEntity<BaseResponse> sendMailCode(
            @RequestBody @Validated MailSendCodeVO mailSendCodeVO,
            @NotNull BindingResult bindingResult,
            @NotNull HttpServletRequest request
    ){
        return null;
    }
}
