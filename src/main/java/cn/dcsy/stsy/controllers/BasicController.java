package cn.dcsy.stsy.controllers;

import cn.dcsy.stsy.models.voData.BasicLoginVO;
import cn.dcsy.stsy.service.UserService;
import cn.dcsy.stsy.utils.BaseResponse;
import cn.dcsy.stsy.utils.ErrorCode;
import cn.dcsy.stsy.utils.ResultUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
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
        return ResultUtil.success("访问成功");
    }

    /**
     * 用户登录
     * */
    @PostMapping("/login")
    public ResponseEntity<BaseResponse> login(
            @RequestBody @Validated BasicLoginVO basicLoginVO,
            @NotNull BindingResult bindingResult,
            HttpServletRequest request
            ) {
        log.info("\t尝试登录 用户名: {}", basicLoginVO.getUsername());
        if (bindingResult.hasErrors()) {
            return ResultUtil.error("RequestBodyError", ErrorCode.REQUEST_BODY_ERROR, bindingResult.getAllErrors());
        }
        return userService.login(request, basicLoginVO);
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
