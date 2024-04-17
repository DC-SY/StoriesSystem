package cn.dcsy.stsy.service;

import cn.dcsy.stsy.utils.BaseResponse;
import org.springframework.http.ResponseEntity;

/**
 * @author DC_DC
 * Date: 2024/4/16/21:51
 */
public interface UserService {
    ResponseEntity<BaseResponse> login(String username, String password);
}
