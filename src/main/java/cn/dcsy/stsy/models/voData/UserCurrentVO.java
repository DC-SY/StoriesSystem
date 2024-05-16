package cn.dcsy.stsy.models.voData;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author DC_DC
 * Date: 2024/5/16/16:13
 */
@Getter
@Setter
@NoArgsConstructor
public class UserCurrentVO {
    private String uuid;
    private String name;
    private String gender;
    private String email;
    private String username;
    private String avatar;
    private String createAt;
    private String updateAt;
}
