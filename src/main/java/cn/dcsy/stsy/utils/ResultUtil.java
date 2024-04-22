package cn.dcsy.stsy.utils;

import org.springframework.http.ResponseEntity;

/**
 * @author DC_DC
 * Date: 2024/4/22/15:19
 */
public class ResultUtil {
    /**
     * 生成表示操作成功的响应实体，不带数据参数。
     * <hr/>
     *
     * @param message 成功时的提示消息。
     * @return 包含成功状态码和消息的响应实体。
     */
    public static ResponseEntity<BaseResponse> success(String message) {
        // 创建并返回一个包含成功代码、消息和空错误信息的响应实体
        return ResponseEntity
                .ok(new BaseResponse("Success", 200, message, null));
    }

    /**
     * 生成表示操作成功的响应实体，携带数据参数。
     * <hr/>
     *
     * @param message 成功时的提示消息。
     * @return 包含成功状态码和消息的响应实体。
     */
    public static ResponseEntity<BaseResponse> success(String message, Object data) {
        return ResponseEntity
                .ok(new BaseResponse("Success", 200, message, data));
    }

    /**
     * 生成错误响应实体，不带数据参数。
     * <hr/>
     *
     * @param errorCode 错误码
     * @return 返回一个包含错误信息的ResponseEntity对象。
     */
    public static ResponseEntity<BaseResponse> error(ErrorCode errorCode) {
        return ResponseEntity
                .status(errorCode.getCode() / 100)
                .body(new BaseResponse(errorCode.getOutput(), errorCode.getCode(), errorCode.getMessage(), null));
    }

    /**
     * 生成错误响应实体。携带数据参数
     *
     * @param errorCode 错误码对象，包含错误信息、状态码和错误输出。
     * @param data 可选的数据对象，包含与错误相关的额外数据。
     * @return 返回一个包含错误信息的ResponseEntity对象。
     */
    public static ResponseEntity<BaseResponse> error(ErrorCode errorCode, Object data) {
        // 根据错误码设置响应状态码
        return ResponseEntity
                .status(errorCode.getCode() / 100)
                // 构造包含错误信息和数据的响应体
                .body(new BaseResponse(errorCode.getOutput(), errorCode.getCode(), errorCode.getMessage(), data));
    }

    /**
     * 生成错误响应实体。
     *
     * @param errorMessage 错误信息，描述了具体的错误内容。
     * @param errorCode 错误码，包含了错误的代码和输出信息。
     * @param data 可选的数据，包含了与错误相关的额外数据或信息。
     * @return 返回一个包含错误信息的ResponseEntity对象。
     */
    public static ResponseEntity<BaseResponse> error(String errorMessage, ErrorCode errorCode, Object data) {
        // 根据错误码设置HTTP响应状态码，并构建BaseResponse对象作为响应体
        return ResponseEntity
                .status(errorCode.getCode() / 100)
                .body(new BaseResponse(errorCode.getOutput(), errorCode.getCode(), errorMessage, data));
    }
}
