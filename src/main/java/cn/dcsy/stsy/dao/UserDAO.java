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
    public Boolean isEmailExist(String email) {
        return userMapper.isExistsEmail(email);
    }



}
