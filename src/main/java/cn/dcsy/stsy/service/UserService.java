package cn.dcsy.stsy.service;

import cn.dcsy.stsy.models.voData.BasicLoginVO;
import cn.dcsy.stsy.models.voData.BasicRegisterVO;
import cn.dcsy.stsy.utils.BaseResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

/**
 * @author DC_DC
 * Date: 2024/4/16/21:51
 */
public interface UserService {

    ResponseEntity<BaseResponse> register(HttpServletRequest request, BasicRegisterVO basicRegisterVO);
}
