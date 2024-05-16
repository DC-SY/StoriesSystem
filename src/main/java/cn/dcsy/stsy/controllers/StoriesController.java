package cn.dcsy.stsy.controllers;

import cn.dcsy.stsy.models.voData.StoriesAddVO;
import cn.dcsy.stsy.service.StoriesService;
import cn.dcsy.stsy.utils.BaseResponse;
import cn.dcsy.stsy.utils.ErrorCode;
import cn.dcsy.stsy.utils.ResultUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * @author DC_DC
 * Date: 2024/5/13/23:35
 */
@Slf4j
@RestController
@CrossOrigin("*")
@RequestMapping("/stories")
@RequiredArgsConstructor
public class StoriesController {
    private final StoriesService storiesService;

    @PostMapping("/add")
    public ResponseEntity<BaseResponse> addStories(
            @RequestBody @Validated StoriesAddVO storiesAddVO,
            @NotNull BindingResult bindingResult,
            HttpServletRequest request
    ) throws IOException {
        // 添加故事
        if (bindingResult.hasErrors()) {
            return ResultUtil.error("RequestBodyError", ErrorCode.REQUEST_BODY_ERROR, bindingResult.getAllErrors());
        }
        return storiesService.addStories(request, storiesAddVO);
    }
}
