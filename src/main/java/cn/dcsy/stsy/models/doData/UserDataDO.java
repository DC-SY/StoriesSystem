package cn.dcsy.stsy.models.doData;

import lombok.Data;

/**
 * @author DC_DC
 * Date: 2024/4/16/22:00
 */
@Data
public class UserDataDO {
    private String uuid;
    private String name;
    private String gender;
    private String email;
    private String username;
    private String password;
    private String avatar;
    private String createAt;
    private String updateAt;
    private String allStories;
}
