package cn.dcsy.stsy.exceptions;

import cn.dcsy.stsy.utils.BaseResponse;
import cn.dcsy.stsy.utils.ErrorCode;
import cn.dcsy.stsy.utils.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author DC_DC
 * Date: 2024/5/7/19:45
 */
@Slf4j
@RestControllerAdvice
public class PublicException {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse> exceptionHandler(Exception e) {
        log.error("[Exception] 系统异常", e);
        return ResultUtil.error(e.getMessage(), ErrorCode.SERVER_INTERNAL_ERROR, null);
    }
}
