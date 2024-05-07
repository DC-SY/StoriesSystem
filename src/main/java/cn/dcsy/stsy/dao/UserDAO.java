package cn.dcsy.stsy.dao;

import cn.dcsy.stsy.mappers.UserMapper;
import cn.dcsy.stsy.models.doData.UserDataDO;
import cn.dcsy.stsy.models.voData.BasicRegisterVO;
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

    /**
     * 检查指定的电子邮件是否已存在于用户数据库中。
     *
     * @param email 需要检查的电子邮件地址，类型为String。
     * @return 返回一个Boolean值，如果电子邮件已存在，则返回true；否则返回false。
     */
    public Boolean isEmailExist(String email) {
        // 通过userMapper接口查询数据库中是否存在指定的email
        return userMapper.isExistsEmail(email);
    }


    public boolean insertUser(UserDataDO userDataDO) {
        return userMapper.insertUser(userDataDO);
    }
}
