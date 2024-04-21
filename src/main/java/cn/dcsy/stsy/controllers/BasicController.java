package cn.dcsy.stsy.controllers;

import cn.dcsy.stsy.service.UserService;
import cn.dcsy.stsy.utils.BaseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 基本控制器
 *
 * @author DC_DC
 * Date: 2024/4/8/08:50
 */

@Slf4j
@RestController
@CrossOrigin("*")
@RequestMapping("/basic")
@RequiredArgsConstructor
public class BasicController {
    private final UserService userService;
    /**
     * 网站主页
     */
    @GetMapping("/index")
    public ResponseEntity<BaseResponse> index() {
        log.info("访问主页");
        BaseResponse response = new BaseResponse("欢迎", 200, "Success", "这是故事管理系统主页");
        return ResponseEntity.ok(response);
    }

    /*
    * 用户登录
    * */
    @PostMapping("/login")
    public ResponseEntity<BaseResponse> login(@RequestParam String username, @RequestParam String password) {
        log.info("尝试登录 用户名: {}", username);
        return userService.login(username, password);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<BaseResponse> getUserCurrent(@PathVariable String userId) {
        log.info("获取用户 {}", userId);
        if (!userId.matches("^[0-9]+$")) {
            return ResponseEntity.status(403).body(
                    new BaseResponse("PathValueError", 40301, "参数错误", null)
            );
        }
        return userService.getUserInfo(userId);
    }

}
