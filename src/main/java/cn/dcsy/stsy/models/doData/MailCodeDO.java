package cn.dcsy.stsy.models.doData;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author DC_DC
 * Date: 2024/4/28/16:10
 */
@Data
@Accessors(chain = true)
public class MailCodeDO {
    private String email;
    private String code;
}
