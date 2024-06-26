package cn.dcsy.stsy.mappers;

import cn.dcsy.stsy.models.doData.MailCodeDO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

/**
 * @author DC_DC
 * Date: 2024/4/28/16:09
 */
public interface CodeMapper {
    @Insert("INSERT INTO stories_system.code (email, code, created_time, expired_time) VALUES (#{email}, #{code}, current_time(), DATE_ADD(current_time(), INTERVAL 10 MINUTE))")
    Boolean insertCode(MailCodeDO mailCodeDO);

    @Delete("DELETE FROM stories_system.code WHERE expired_time < NOW()")
    void cleanCode();

    @Select("SELECT code FROM stories_system.code WHERE email = #{email} AND expired_time > NOW()")
    String getCode(String email);
}
