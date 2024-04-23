package cn.dcsy.stsy.models.voData;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * @author DC_DC
 * Date: 2024/4/23/19:50
 */
@Getter
@Setter
public class BasicLoginVO {
    @NotBlank(message = "用户名不能为空")
    private String username;
    @NotBlank(message = "用户名不能为空")
    private String password;
}
