package cn.dcsy.stsy.service.impl;

import cn.dcsy.stsy.dao.CodeDAO;
import cn.dcsy.stsy.dao.UserDAO;
import cn.dcsy.stsy.models.doData.UserDataDO;
import cn.dcsy.stsy.models.voData.BasicLoginVO;
import cn.dcsy.stsy.models.voData.BasicRegisterVO;
import cn.dcsy.stsy.service.UserService;
import cn.dcsy.stsy.utils.BaseResponse;
import cn.dcsy.stsy.utils.ErrorCode;
import cn.dcsy.stsy.utils.ResultUtil;
import cn.dcsy.stsy.utils.StringUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
        // 校验用户名是否唯一
        if (userDAO.isNameExist(basicRegisterVO.getName())) {
            return ResultUtil.error("用户名重复", ErrorCode.USER_IS_EXIST, null);
        }
        // 校验验证码是否匹配
        if (!(basicRegisterVO.getCode().equals(codeDAO.getCode(basicRegisterVO.getEmail())))) {
            return ResultUtil.error(ErrorCode.EMAIL_CODE_ERROR);
        }
        UserDataDO userDataDO = new UserDataDO();
        BeanUtils.copyProperties(basicRegisterVO, userDataDO);
        // 对密码进行加密
        userDataDO.setPassword(StringUtil.passwordEncryption(basicRegisterVO.getPassword()));
        // 创建uuid并存入数据库
        userDataDO.setUuid(StringUtil.createUuid());
        if (!userDAO.insertUser(userDataDO)) {
            return ResultUtil.error("用户注册失败", ErrorCode.INSERT_DATA_ERROR, null);
        }
        return ResultUtil.success("注册成功");
    }

    @Override
    public ResponseEntity<BaseResponse> login(HttpServletRequest request, BasicLoginVO basicLoginVO) {
        // 校验用户是否存在
        UserDataDO userDataDO = userDAO.getUserByName(basicLoginVO.getName());
        if (userDataDO == null) {
            return ResultUtil.error(ErrorCode.USER_IS_NOT_EXIST);
        }
        // 校验密码是否匹配
        if (!StringUtil.passwordCheck(basicLoginVO.getPassword(), userDataDO.getPassword())) {
            return ResultUtil.error(ErrorCode.USER_PASSWORD_ERROR);
        }
        request.getSession().setAttribute("user", basicLoginVO);
        return ResultUtil.success("登录成功", userDataDO.getUuid());
    }

}
