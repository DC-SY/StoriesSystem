package cn.dcsy.stsy.service;

import cn.dcsy.stsy.utils.BaseResponse;
import org.springframework.http.ResponseEntity;

/**
 * @author DC_DC
 * Date: 2024/5/16/16:11
 */
public interface UserService {
    /**
     * 获取当前用户信息
     *
     * @param userUuid 用户uuid
     */
    ResponseEntity<BaseResponse> getUserCurrent(String userUuid);
}
