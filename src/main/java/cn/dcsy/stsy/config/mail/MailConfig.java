package cn.dcsy.stsy.config.mail;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

/**
 * @author DC_DC
 * Date: 2024/4/27/21:07
 */
@Configuration
public class MailConfig {

    @Value("${spring.mail.host}")
    private String emailHost;

    @Value("${spring.mail.username}")
    private String emailUsername;

    @Value("${spring.mail.password}")
    private String emailPassword;

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setDefaultEncoding("UTF-8");
        mailSender.setHost(emailHost);
        mailSender.setPort(25);
        mailSender.setUsername(emailUsername);
        mailSender.setPassword(emailPassword);

        mailSender.getJavaMailProperties().put("mail.smtp.auth", "true");
        mailSender.getJavaMailProperties().put("mail.smtp.starttls.enable", "true");
        mailSender.getJavaMailProperties().put("mail.debug", "false");
        mailSender.getJavaMailProperties().put("mail.transport.protocol", "smtp");

        return mailSender;
    }
}
