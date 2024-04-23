package cn.dcsy.stsy.service.impl;

import cn.dcsy.stsy.dao.UserDAO;
import cn.dcsy.stsy.models.doData.UserDemoDO;
import cn.dcsy.stsy.models.voData.BasicLoginVO;
import cn.dcsy.stsy.service.UserService;
import cn.dcsy.stsy.utils.BaseResponse;
import jakarta.servlet.http.HttpServletRequest;
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
    public ResponseEntity<BaseResponse> login(HttpServletRequest request, BasicLoginVO basicLoginVO) {
        UserDemoDO userDemoDO = userDAO.getPasswordByUserName(basicLoginVO.getUsername());
        if (userDemoDO != null){
            log.info(userDemoDO.toString());
            if (userDemoDO.getPassword().equals(basicLoginVO.getPassword())){
                BaseResponse response = new BaseResponse("登录成功", 200, "Success", userDemoDO);
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

    @Override
    public ResponseEntity<BaseResponse> getUserInfo(String userId) {
        UserDemoDO getUser = userDAO.getUserById(userId);
        if (getUser != null) {
            return ResponseEntity.ok().body(
                    new BaseResponse("Success", 200, "获取成功", getUser)
            );
        } else {
            return ResponseEntity.status(404).body(
                    new BaseResponse("UserNotExist", 40401, "用户不存在", null)
            );
        }
    }
}
