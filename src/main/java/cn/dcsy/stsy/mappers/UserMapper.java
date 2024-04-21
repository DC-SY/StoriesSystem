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
    @Select("SELECT * FROM stories_system.user_demo WHERE username = #{username}")
    UserDemoDO getPasswordByUsername(String username);

    @Select("SELECT * FROM stories_system.user_demo WHERE id = #{userId}")
    UserDemoDO getUserById(long userId);
}
