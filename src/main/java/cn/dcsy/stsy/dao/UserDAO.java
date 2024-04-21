package cn.dcsy.stsy.dao;

import cn.dcsy.stsy.mappers.UserMapper;
import cn.dcsy.stsy.models.doData.UserDemoDO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

/**
 * @author DC_DC
 * Date: 2024/4/16/22:02
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class UserDAO {
    private final UserMapper userMapper;

    public UserDemoDO getPasswordByUserName(String userName) {
        log.info("[DAO] 执行 getPasswordByUserName 方法");
        log.info("\t> Mysql 读取");
        return userMapper.getPasswordByUsername(userName);
    }

    public UserDemoDO getUserById(String userId) {
        log.info("[DAO] 执行 getUserById 方法");
        log.info("\t> Mysql 读取");
        return userMapper.getUserById(Long.parseLong(userId));
    }
}
