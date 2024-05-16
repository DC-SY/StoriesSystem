package cn.dcsy.stsy.service.impl;

import cn.dcsy.stsy.dao.UserDAO;
import cn.dcsy.stsy.models.doData.UserDataDO;
import cn.dcsy.stsy.models.voData.UserCurrentVO;
import cn.dcsy.stsy.service.UserService;
import cn.dcsy.stsy.utils.BaseResponse;
import cn.dcsy.stsy.utils.ErrorCode;
import cn.dcsy.stsy.utils.ResultUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;

/**
 * 用户服务实现类
 *
 * @author DC_DC
 * Date: 2024/5/16/16:11
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserDAO userDAO;

    @Override
    public ResponseEntity<BaseResponse> getUserCurrent(String userUuid) {
        final UserDataDO userByUuid = userDAO.getUserByUuid(userUuid);
        if (userByUuid == null) {
            return ResultUtil.error(ErrorCode.USER_IS_NOT_EXIST);
        }
        UserCurrentVO userCurrentVO = new UserCurrentVO();
        BeanUtils.copyProperties(userByUuid, userCurrentVO);
        userCurrentVO.setCreateAt(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(userByUuid.getCreateAt().getTime()));
        if (userByUuid.getUpdateAt() != null) {
            userCurrentVO.setCreateAt(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(userByUuid.getUpdateAt().getTime()));
        }
        return ResultUtil.success("用户信息获取成功", userByUuid);
    }
}
