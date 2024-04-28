package cn.dcsy.stsy.mappers;

import cn.dcsy.stsy.models.doData.UserDemoDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author DC_DC
 * Date: 2024/4/16/22:03
 */
@Mapper
public interface UserMapper {

    @Select("SELECT COUNT(email) > 0 FROM stories_system.user_data WHERE email = #{email}")
    Boolean isExistsEmail(String email);
}
