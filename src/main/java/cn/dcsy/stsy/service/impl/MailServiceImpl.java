package cn.dcsy.stsy.service.impl;

import cn.dcsy.stsy.dao.CodeDAO;
import cn.dcsy.stsy.dao.UserDAO;
import cn.dcsy.stsy.models.doData.MailCodeDO;
import cn.dcsy.stsy.service.MailService;
import cn.dcsy.stsy.utils.BaseResponse;
import cn.dcsy.stsy.utils.ErrorCode;
import cn.dcsy.stsy.utils.ResultUtil;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.HashMap;
import java.util.Random;

/**
 * 邮件服务实现类，提供发送邮件等功能
 *
 * @author DC_DC
 * Date: 2024/4/26/10:29
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {
    // 邮件发送器
    private final JavaMailSender javaMailSender;
    // 模板引擎
    private final TemplateEngine templateEngine;
    private final CodeDAO codeDAO;
    private final UserDAO userDAO;

    /**
     * 发送邮件验证码
     *
     * @param request  HttpServletRequest对象
     * @param email    接收邮件的邮箱地址
     * @param template 邮件模板名称
     * @return 返回发送结果，成功返回带有成功信息的ResponseEntity，失败返回带有错误代码的ResponseEntity
     */
    @Override
    public ResponseEntity<BaseResponse> sendMailCode(HttpServletRequest request, String email, String template) {
        // 生成验证码并准备数据，然后发送邮件
        final StringBuilder stringBuilder = getStringBuilder();
        final HashMap<String, Object> prepareData = getStringObjectHashMap(email, template, stringBuilder);
        // 用户注册账号,需要考虑邮箱重复
        if (userDAO.isEmailExist(email)){
            return ResultUtil.error(ErrorCode.USER_IS_EXIST);
        }
        MailCodeDO mailCodeDO = new MailCodeDO();
        mailCodeDO
                .setCode(stringBuilder.toString())
                .setEmail(email);
        // 将注册邮箱以及验证码数据进行存入mysql
        if (!codeDAO.insertCode(mailCodeDO)) {
            return ResultUtil.error(ErrorCode.INSERT_DATA_ERROR);
        }
        Boolean boolean1 = this.sendMail(email, prepareData, "./mail/" + template + ".html");
        if (!boolean1){
            return ResultUtil.error(ErrorCode.SEND_MAIL_ERROR);
        }
        return ResultUtil.success("发送成功");
    }

    @Value("${spring.mail.username}")
    // 发送邮件的地址
    private String from;

    /**
     * 生成6位验证码
     *
     * @return 验证码字符串
     */
    private static @NotNull StringBuilder getStringBuilder() {
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            String code;
            switch (random.nextInt(3)) {
                // 随机生成数字
                case 0 -> code = String.valueOf(random.nextInt(10));
                // 随机生成大写字母
                case 1 -> code = String.valueOf((char) (random.nextInt(26) + 65));
                // 随机生成小写字母
                default -> code = String.valueOf((char) (random.nextInt(26) + 97));
            }
            stringBuilder.append(code);
        }
        return stringBuilder;
    }

    /**
     * 准备邮件发送的数据
     *
     * @param email         接收邮件的邮箱地址
     * @param template      邮件模板名称
     * @param stringBuilder 生成的验证码字符串
     * @return 包含邮件信息的HashMap
     */
    private static @NotNull HashMap<String, Object> getStringObjectHashMap(String email, String template, StringBuilder stringBuilder) {
        String getRandomString = stringBuilder.toString();
        HashMap<String, Object> prepareData = new HashMap<>();
        prepareData.put("email", email);
        prepareData.put("code", getRandomString);
        // 根据模板名称设置邮件主题
        switch (template) {
            case "user-register" -> prepareData.put("title", "StSy - 用户注册");
            case "user-delete" -> prepareData.put("title", "StSy - 账户注销");
            case "user-forget-password" -> prepareData.put("title", "StSy - 忘记密码");
            default -> throw new RuntimeException("模板不存在");
        }
        return prepareData;
    }

    /**
     * 验证邮件验证码
     *
     * @param request HttpServletRequest对象
     * @param email   接收邮件的邮箱地址
     * @param code    用户输入的验证码
     * @return 返回验证结果，成功为true，失败为false
     */
    @Override
    public Boolean verifyMailCode(HttpServletRequest request, String email, String code) {
        // 实现邮件验证码的验证逻辑
        return null;
    }

    /**
     * 发送邮件
     *
     * @param email       接收邮件的邮箱地址
     * @param prepareData 邮件内容数据
     * @param template    邮件模板路径
     * @return 发送成功返回true，失败返回false
     */
    @Override
    public Boolean sendMail(@NotNull String email, @NotNull HashMap<String, Object> prepareData, @NotNull String template) {
        log.info("准备发送邮件");
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            Context context = new Context();
            context.setVariables(prepareData);
            // 加载并处理模板
            String emailContent = templateEngine.process(template, context);
            // 准备并发送邮件
            this.mineMessageSender(message, email, prepareData, emailContent);
            return true;
        } catch (MessagingException e) {
            log.error("\t> 发送邮件发送失败", e);
            return false;
        }
    }

    /**
     * 准备邮件信息并发送
     *
     * @param message      邮件消息对象
     * @param email        接收邮件的邮箱地址
     * @param prepareData  邮件内容数据
     * @param emailContent 邮件正文
     * @throws MessagingException 邮件发送异常
     */
    private void mineMessageSender(MimeMessage message, String email, @NotNull HashMap<String, Object> prepareData, String emailContent) throws MessagingException {
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, true);
        mimeMessageHelper.setFrom(from);
        mimeMessageHelper.setTo(email);
        mimeMessageHelper.setSubject(prepareData.get("title").toString());
        mimeMessageHelper.setText(emailContent, true);
        javaMailSender.send(message);
        log.info("\t> 发送邮件 {} 标题 {} 成功", email, prepareData.get("title").toString());
    }
}
