package cn.dcsy.stsy.controllers;

import cn.dcsy.stsy.models.voData.MailSendCodeVO;
import cn.dcsy.stsy.service.MailService;
import cn.dcsy.stsy.utils.BaseResponse;
import cn.dcsy.stsy.utils.ErrorCode;
import cn.dcsy.stsy.utils.ResultUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author DC_DC
 * Date: 2024/4/23/20:26
 */
@Slf4j
@RestController
@CrossOrigin("*")
@RequestMapping("/mail")
@RequiredArgsConstructor
public class MailController {
    private final MailService mailService;

    /**
     * sendMailCode
     * <hr/>
     * 用于发送邮件验证码，用户可以通过该接口发送邮件验证码
     *
     * @param mailSendCodeVO 邮件发送验证码的请求参数
     * @param request        请求对象
     * @param bindingResult  请求参数的校验结果
     * @return 返回发送邮件验证码的结果
     */
    @PostMapping("/send")
    public ResponseEntity<BaseResponse> sendMailCode(
            /*
             *为什么只定义一个控制器,用于控制邮件的发送呢?
             * 需要发送的邮件有很多种,比如注册邮件 找回密码 密码修改等等,其执行的逻辑都是相同的,用户点击发送邮件,然后填写验证码
             * */
            @RequestBody @Validated MailSendCodeVO mailSendCodeVO,
            @NotNull BindingResult bindingResult,
            @NotNull HttpServletRequest request
    ) {
        if (bindingResult.hasErrors()) {
            return ResultUtil.error("参数校验失败", ErrorCode.REQUEST_BODY_ERROR, bindingResult.getAllErrors());
        }
        return mailService.sendMailCode(request, mailSendCodeVO.getEmail(), mailSendCodeVO.getTemplate());
    }
}
