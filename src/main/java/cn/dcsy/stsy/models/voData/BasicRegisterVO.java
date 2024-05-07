package cn.dcsy.stsy.models.voData;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * @author DC_DC
 * Date: 2024/4/23/20:17
 */
@Getter
@Setter
public class BasicRegisterVO {
    @Email(message = "邮箱格式不正确")
    private String email;
    private String code;
    @NotBlank(message = "用户名不能为空")
    private String name;
    private String password;
}
