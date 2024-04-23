package cn.dcsy.stsy.models.voData;

import lombok.Getter;
import lombok.Setter;

/**
 * @author DC_DC
 * Date: 2024/4/23/20:17
 */
@Getter
@Setter
public class BasicRegisterVO {
    private String email;
    private String code;
    private String username;
    private String password;
}
