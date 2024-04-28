package cn.dcsy.stsy.mappers;

import cn.dcsy.stsy.models.doData.MailCodeDO;
import org.apache.ibatis.annotations.Insert;

/**
 * @author DC_DC
 * Date: 2024/4/28/16:09
 */
public interface CodeMapper {
    @Insert("INSERT INTO stories_system.code (email, code, created_time, update_time) VALUES (#{email}, #{code}, NOW(), NOW())")
    Boolean insertCode(MailCodeDO mailCodeDO);
}
