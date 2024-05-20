package cn.dcsy.stsy.service.impl;

import cn.dcsy.stsy.dao.UserDAO;
import cn.dcsy.stsy.mappers.StoriesMapper;
import cn.dcsy.stsy.models.doData.UserDataDO;
import cn.dcsy.stsy.models.doData.UserStoriesDO;
import cn.dcsy.stsy.models.voData.StoriesAddVO;
import cn.dcsy.stsy.service.StoriesService;
import cn.dcsy.stsy.utils.*;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
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
    private final StoriesMapper storiesMapper;

    // 添加故事
    @Override
    public ResponseEntity<BaseResponse> addStories(HttpServletRequest request, StoriesAddVO storiesVO) throws IOException {
        UserStoriesDO userStoriesDO = new UserStoriesDO();
        BeanUtils.copyProperties(storiesVO, userStoriesDO);
        // 创建用户ssid
        userStoriesDO.setSsid(StringUtil.createUuid());
        // 获取创建用户的uuid, 校验是否有效
        String uuid = request.getParameter("uuid");
        if (!userDAO.isUuidExist(uuid)) {
            return ResultUtil.error(ErrorCode.USER_IS_NOT_EXIST);
        }
        userStoriesDO.setAuid(uuid);
        // 如果有参与者,校验参与者用户名是否有效
        if (storiesVO.getParticipants() != null) {
            List<String> uuidList = new ArrayList<>();
            // 校验参与者用户名是否有效
            for (String participant : storiesVO.getParticipants()) {
                UserDataDO userDataDO = userDAO.getUserByName(participant);
                if (userDataDO == null) {
                    return ResultUtil.error(ErrorCode.USER_IS_NOT_EXIST);
                }
                uuidList.add(userDataDO.getUuid());
            }
            userStoriesDO.setUuid(new Gson().toJson(uuidList));
        }
        // 如果有图片,校验图片是否为base64格式
        if (storiesVO.getPhotos() != null) {
            if (StringUtil.isBase64(storiesVO.getPhotos())) {
                // 对图片进行解码
                List<BufferedImage> images;
                try {
                    images = StringUtil.decodeBase64Images(storiesVO.getPhotos());
                } catch (IOException ioException) {
                    return ResultUtil.error(ErrorCode.PHOTO_DECODE_ERROR);
                }
                // 将图片上传到多吉云服务器, 抽象一个工具类方法,然后传入参数包括:1. 图片列表 2. 文件名(文件名以uuid+时间戳命名)
                List<String> urls = DogeUtil.uploadUtil(images, uuid);
                if (urls == null) {
                    return ResultUtil.error(ErrorCode.PHOTO_UPLOAD_ERROR);
                }
                userStoriesDO.setPhotos(new Gson().toJson(urls));
            } else {
                return ResultUtil.error(ErrorCode.PHOTO_FORMAT_ERROR);
            }
        }
        if (!storiesMapper.addStories(userStoriesDO)) {
            return ResultUtil.error(ErrorCode.INSERT_DATA_ERROR);
        }
        return ResultUtil.success("故事添加成功");
    }
}
