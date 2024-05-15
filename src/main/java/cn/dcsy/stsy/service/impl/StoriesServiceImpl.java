package cn.dcsy.stsy.service.impl;

import cn.dcsy.stsy.dao.UserDAO;
import cn.dcsy.stsy.models.voData.StoriesAddVO;
import cn.dcsy.stsy.service.StoriesService;
import cn.dcsy.stsy.utils.BaseResponse;
import cn.dcsy.stsy.utils.ErrorCode;
import cn.dcsy.stsy.utils.ResultUtil;
import cn.dcsy.stsy.utils.StringUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

/**
 * @author DC_DC
 * Date: 2024/5/13/23:54
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class StoriesServiceImpl implements StoriesService {
    private final UserDAO userDAO;
    // 添加故事
    @Override
    public ResponseEntity<BaseResponse> addStories(HttpServletRequest request, StoriesAddVO storiesVO) {
        // 获取创建用户的uuid, 校验是否有效
        String uuid = request.getParameter("uuid");
        if (!userDAO.isUuidExist(uuid)) {
            return ResultUtil.error(ErrorCode.USER_IS_NOT_EXIST);
        }
        // 如果有参与者,校验参与者用户名是否有效
        if (storiesVO.getParticipants() != null) {
            // 校验参与者用户名是否有效
            for (String participant : storiesVO.getParticipants()) {
                if (!userDAO.isNameExist(participant)) {
                    return ResultUtil.error(ErrorCode.USER_IS_NOT_EXIST);
                }
            }
        }
        // 校验图片是否为base64格式
        if (!StringUtil.isBase64(storiesVO.getPhotos())){
            return ResultUtil.error(ErrorCode.PHOTO_FORMAT_ERROR);
        }
        // 对图片进行解码
        try {
            List<BufferedImage> images = StringUtil.decodeBase64Images(storiesVO.getPhotos());
        } catch (IOException ioException){
            return ResultUtil.error(ErrorCode.PHOTO_DECODE_ERROR);
        }
        // 将图片上传到多吉云服务器
        return ResultUtil.success("添加成功");
    }
}
