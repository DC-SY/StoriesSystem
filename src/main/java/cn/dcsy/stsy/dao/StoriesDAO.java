package cn.dcsy.stsy.dao;

import cn.dcsy.stsy.mappers.StoriesMapper;
import cn.dcsy.stsy.models.doData.UserStoriesDO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

/**
 * @author DC_DC
 * Date: 2024/5/21/20:09
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class StoriesDAO {
    private final StoriesMapper storiesMapper;

    public Boolean deleteStories(String ssid) {
        return storiesMapper.deleteStories(ssid);
    }

    public boolean addStories(UserStoriesDO userStoriesDO) {
        return storiesMapper.addStories(userStoriesDO);
    }


    public UserStoriesDO getStoriesBySsid(String ssid) {
        return storiesMapper.getStoriesBySsid(ssid);
    }
}
