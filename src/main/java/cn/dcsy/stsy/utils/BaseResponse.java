package cn.dcsy.stsy.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.extern.slf4j.Slf4j;

/**
 * @author DC_DC
 * Date: 2024/4/16/21:22
 */
@Slf4j
@JsonInclude(JsonInclude.Include.NON_NULL)
public record BaseResponse(String output, Integer code, String message, Object data) {

    public BaseResponse(String output, Integer code, String message, Object data) {
        this.output = output;
        this.code = code;
        this.message = message;
        this.data = data;
        log.info("============================================================");
    }
}
