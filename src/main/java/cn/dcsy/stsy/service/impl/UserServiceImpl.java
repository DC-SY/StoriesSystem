package cn.dcsy.stsy.service.impl;

import cn.dcsy.stsy.dao.CodeDAO;
import cn.dcsy.stsy.dao.UserDAO;
import cn.dcsy.stsy.models.doData.UserDataDO;
import cn.dcsy.stsy.models.voData.BasicRegisterVO;
import cn.dcsy.stsy.service.UserService;
import cn.dcsy.stsy.utils.BaseResponse;
import cn.dcsy.stsy.utils.ErrorCode;
import cn.dcsy.stsy.utils.ResultUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @author DC_DC
 * Date: 2024/4/16/21:53
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserDAO userDAO;
    private final CodeDAO codeDAO;

    @Override
    public ResponseEntity<BaseResponse> register(HttpServletRequest request, BasicRegisterVO basicRegisterVO) {
        // 校验邮箱是否唯一
        if (userDAO.isEmailExist(basicRegisterVO.getEmail())) {
            return ResultUtil.error(ErrorCode.EMAIL_EXIST);
        }
        // 校验验证码是否匹配
        if (!(codeDAO.getCode(basicRegisterVO.getEmail()).equals(basicRegisterVO.getCode()))){
            return ResultUtil.error(ErrorCode.EMAIL_CODE_ERROR);
        }
        UserDataDO userDataDO = new UserDataDO();
        BeanUtils.copyProperties(basicRegisterVO, userDataDO);
        // 创建uuid并存入数据库
        UUID uuid = UUID.randomUUID();
        userDataDO.setUuid(String.valueOf(uuid));
        if (!userDAO.insertUser(userDataDO)){
            return ResultUtil.error("用户注册失败", ErrorCode.INSERT_DATA_ERROR, null);
        }
        return ResultUtil.success("注册成功");
    }

}
