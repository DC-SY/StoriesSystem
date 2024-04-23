package cn.dcsy.stsy.models.voData;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author DC_DC
 * Date: 2024/4/23/20:35
 */
@Data
public class MailSendCodeVO {
    @Email(message = "邮箱格式不正确")
    public String email;
    @NotBlank(message = "模板不能为空")
    public String template;
}
