package cn.dcsy.stsy.controllers;

import cn.dcsy.stsy.service.UserService;
import cn.dcsy.stsy.utils.BaseResponse;
import cn.dcsy.stsy.utils.ErrorCode;
import cn.dcsy.stsy.utils.ResultUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 用户控制器
 *
 * @author DC_DC
 * Date: 2024/5/16/16:06
 */
@Slf4j
@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    /**
     * 获取当前用户信息
     */
    @GetMapping("/current")
    public ResponseEntity<BaseResponse> userCurrent(
            @RequestHeader("X-USER-UUID") String userUuid
    ) {
        if (userUuid == null) {
            return ResultUtil.error(ErrorCode.USER_NOT_LOGIN);
        }
        return userService.getUserCurrent(userUuid);
    }
}
