package cn.dcsy.stsy.service.impl;

import cn.dcsy.stsy.dao.UserDAO;
import cn.dcsy.stsy.models.doData.UserDemoDO;
import cn.dcsy.stsy.service.UserService;
import cn.dcsy.stsy.utils.BaseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * @author DC_DC
 * Date: 2024/4/16/21:53
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserDAO userDAO;

    @Override
    public ResponseEntity<BaseResponse> login(String username, String password) {
        UserDemoDO userDemoDO = userDAO.getPasswordByUserName(username);
        log.info(String.valueOf(userDemoDO));
        if (userDemoDO != null){
            if (userDemoDO.getPassword().equals(password)){
                BaseResponse response = new BaseResponse("登录成功", 200, "Success", "用户已登录");
                return ResponseEntity.ok(response);
            } else {
                BaseResponse response = new BaseResponse("登录失败", 404, "Error", "密码错误");
                return ResponseEntity.status(404).body(response);
            }
        } else {
            BaseResponse response = new BaseResponse("登录失败", 404, "Error", "用户名不存在");
            return ResponseEntity.status(404).body(response);
        }
    }
}
