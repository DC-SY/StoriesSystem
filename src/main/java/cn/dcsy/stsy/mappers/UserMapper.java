package cn.dcsy.stsy.mappers;

import cn.dcsy.stsy.models.doData.UserDataDO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author DC_DC
 * Date: 2024/4/16/22:03
 */
@Mapper
public interface UserMapper {

    @Select("SELECT COUNT(email) = 1 FROM stories_system.user_data WHERE email = #{email}")
    boolean isExistsEmail(String email);

    @Select("SELECT COUNT(name) = 1 FROM stories_system.user_data WHERE name = #{name}")
    boolean isNameExist(String name);

    @Select("SELECT COUNT(uuid) = 1 FROM stories_system.user_data WHERE uuid = #{uuid}")
    boolean isUuidExist(String uuid);

    @Insert("INSERT INTO stories_system.user_data(uuid, name, gender, email, password, create_at) VALUES (#{uuid}, #{name}, #{gender}, #{email}, #{password}, NOW())")
    boolean insertUser(UserDataDO userDataDO);

    @Select("SELECT * FROM stories_system.user_data WHERE email = #{email}")
    UserDataDO getUserByEmail(String email);

    @Select("SELECT * FROM stories_system.user_data WHERE name = #{name}")
    UserDataDO getUserByName(String name);

    @Select("SELECT * FROM stories_system.user_data WHERE uuid = #{uuid}")
    UserDataDO getUserByUuid(String uuid);
}
