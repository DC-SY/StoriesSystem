package cn.dcsy.stsy.mappers;

import cn.dcsy.stsy.models.doData.UserStoriesDO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author DC_DC
 * Date: 2024/5/20/22:13
 */
@Mapper
public interface StoriesMapper {
    @Insert("INSERT INTO stories_system.user_stories(ssid, auid, uuid, name, type, start_time, end_time, content, photos, mood, place, status) VALUES (#{ssid}, #{auid}, #{uuid}, #{name}, #{type}, #{startTime}, #{endTime}, #{content}, #{photos}, #{mood}, #{place}, #{status})")
    Boolean addStories(UserStoriesDO userStoriesDO);

    @Select("SELECT * FROM stories_system.user_stories WHERE auid = #{auid}")
    UserStoriesDO getStoryByAuid(String auid);
}
