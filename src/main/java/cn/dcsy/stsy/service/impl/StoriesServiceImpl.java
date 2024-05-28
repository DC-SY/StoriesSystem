package cn.dcsy.stsy.service.impl;

import cn.dcsy.stsy.dao.StoriesDAO;
import cn.dcsy.stsy.dao.UserDAO;
import cn.dcsy.stsy.models.doData.UserDataDO;
import cn.dcsy.stsy.models.doData.UserStoriesDO;
import cn.dcsy.stsy.models.voData.StoriesAddVO;
import cn.dcsy.stsy.models.voData.UserStoriesVO;
import cn.dcsy.stsy.service.StoriesService;
import cn.dcsy.stsy.utils.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.reflect.Type;
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
    private final StoriesDAO storiesDAO;

    // 添加故事
    @Override
    public ResponseEntity<BaseResponse> addStories(HttpServletRequest request, StoriesAddVO storiesVO) throws IOException {
        UserStoriesDO userStoriesDO = new UserStoriesDO();
        BeanUtils.copyProperties(storiesVO, userStoriesDO);
        // 创建故事唯一识别码
        String ssid = StringUtil.createUuid();
        userStoriesDO.setSsid(ssid);
        // 获取创建用户的uuid, 校验是否有效
        String uuid = request.getParameter("uuid");
        if (!userDAO.isUuidExist(uuid)) {
            return ResultUtil.error(ErrorCode.USER_IS_NOT_EXIST);
        }
        userStoriesDO.setAuid(uuid);
        // 如果有参与者,校验参与者用户名是否有效
        List<String> uuidList = new ArrayList<>();
        if (storiesVO.getParticipants() != null) {
            // 校验参与者用户名是否有效
            for (String participant : storiesVO.getParticipants()) {
                UserDataDO userDataDO = userDAO.getUserByName(participant);
                if (userDataDO == null) {
                    return ResultUtil.error("故事参与者不存在", ErrorCode.USER_IS_NOT_EXIST, null);
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
        // 将故事信息添加到故事表中
        if (!storiesDAO.addStories(userStoriesDO)) {
            return ResultUtil.error(ErrorCode.INSERT_DATA_ERROR);
        }
        // 根据故事ssid获取到所有相关的用户uuid
        Type type = new TypeToken<List<String>>() {
        }.getType();
        // 参与者的uuid列表加上创建者的uuid
        uuidList.add(uuid);
        // 将ssid添加到每一位用户的all_stories字段中
        for (String userUuid : uuidList) {
            UserDataDO userDataDO = userDAO.getUserByUuid(userUuid);
            List<String> allStories = new Gson().fromJson(userDataDO.getAllStories(), type);
            if (allStories == null) {
                allStories = new ArrayList<>();
            }
            allStories.add(ssid);
            userDataDO.setAllStories(new Gson().toJson(allStories));
            if (!userDAO.updateUserStories(userDataDO)) {
                return ResultUtil.error(ErrorCode.UPDATE_DATA_ERROR);
            }
        }
        return ResultUtil.success("故事添加成功");
    }

    @Override
    public ResponseEntity<BaseResponse> getStories(HttpServletRequest request) {
        // 获取用户的uuid, 校验是否有效
        String uuid = request.getHeader("X-USER-UUID");
        if (!userDAO.isUuidExist(uuid)) {
            return ResultUtil.error(ErrorCode.USER_IS_NOT_EXIST);
        }
        // 获取用户表中的all_stories字段, 该字段存储了用户参与的所有故事的ssid
        UserDataDO userDataDO = userDAO.getUserByUuid(uuid);
        String allStories = userDataDO.getAllStories();
        if (allStories == null) {
            return ResultUtil.success("没有故事");
        }
        // 解析all_stories字段, 获取到所有故事的ssid
        Type type = new TypeToken<List<String>>() {
        }.getType();
        List<String> ssidList = new Gson().fromJson(allStories, type);
        if (ssidList.isEmpty()) {
            return ResultUtil.success("没有故事");
        }
        List<UserStoriesVO> userStoriesList = new ArrayList<>();
        for (String ssid : ssidList) {
            // 根据ssid获取故事信息
            UserStoriesDO userStoriesDO = storiesDAO.getStoriesBySsid(ssid);
            if (userStoriesDO == null) {
                return ResultUtil.error(ErrorCode.SELECT_DATA_ERROR);
            }
            UserStoriesVO userStoriesVO = new UserStoriesVO();
            BeanUtils.copyProperties(userStoriesDO, userStoriesVO);
            userStoriesVO.setPhotos(new Gson().fromJson(userStoriesDO.getPhotos(), type));
            userStoriesList.add(userStoriesVO);
        }
        return ResultUtil.success("用户所有故事已经准备完毕", userStoriesList);
    }

    @Override
    public ResponseEntity<BaseResponse> deleteStories(HttpServletRequest request) {
        // 获取用户的uuid, 校验是否有效
        String auid = request.getParameter("uuid");
        if (!userDAO.isUuidExist(auid)) {
            return ResultUtil.error(ErrorCode.USER_IS_NOT_EXIST);
        }
        // 获取故事ssid
        String ssid = request.getParameter("ssid");
        // 根据故事ssid获取到所有参与者uuid
        UserStoriesDO userStoriesDO = storiesDAO.getStoriesBySsid(ssid);
        if (userStoriesDO == null) {
            return ResultUtil.error("故事不存在", ErrorCode.SELECT_DATA_ERROR, null);
        }
        // 所有参与者uuid
        String uuidJson = userStoriesDO.getUuid();
        Type type = new TypeToken<List<String>>() {
        }.getType();
        List<String> uuidList = new Gson().fromJson(uuidJson, type);
        if (uuidList == null) {
            uuidList = new ArrayList<>();
        }
        uuidList.add(auid);
        // 更新每一位参与者和创建者的all_stories字段
        for (String uuid : uuidList) {
            UserDataDO userDataDO = userDAO.getUserByUuid(uuid);
            List<String> allStories = new Gson().fromJson(userDataDO.getAllStories(), type);
            allStories.remove(ssid);
            userDataDO.setAllStories(new Gson().toJson(allStories));
            if (!userDAO.updateUserStories(userDataDO)) {
                return ResultUtil.error(ErrorCode.UPDATE_DATA_ERROR);
            }
        }
        // 删除云储存的图片列表
        // 获取图片url列表
        String photos = userStoriesDO.getPhotos();
        if (!DogeUtil.deleteUtil(photos)) {
            return ResultUtil.error(ErrorCode.PHOTO_DELETE_ERROR);
        }
        // 数据库删除故事信息
        if (!storiesDAO.deleteStories(ssid)) {
            return ResultUtil.error(ErrorCode.DELETE_DATA_ERROR);
        }
        return ResultUtil.success("故事删除成功");
    }

    @Override
    public ResponseEntity<BaseResponse> editStories(HttpServletRequest request, StoriesAddVO storiesVO) {
//        // 获取用户的uuid, 校验是否有效
//        String auid = request.getParameter("uuid");
//        if (!userDAO.isUuidExist(auid)) {
//            return ResultUtil.error(ErrorCode.USER_IS_NOT_EXIST);
//        }
//        // 获取故事ssid
//        String ssid = request.getParameter("ssid");
//        // 根据故事ssid获取到该故事所有信息
//        UserStoriesDO userStoriesDO = storiesDAO.getStoriesBySsid(ssid);
//        if (userStoriesDO == null) {
//            return ResultUtil.error("故事不存在", ErrorCode.SELECT_DATA_ERROR, null);
//        }
//        if (!auid.equals(userStoriesDO.getAuid())) {
//            return ResultUtil.error("无权修改他人故事", ErrorCode.USER_NOT_PERMISSION, null);
//        }
//        // 更新故事信息
//        BeanUtils.copyProperties(storiesVO, userStoriesDO);
//        // 如果有参与者,校验参与者用户名是否有效
//        List<String> uuidList = new ArrayList<>();
//        if (storiesVO.getParticipants() != null) {
//            // 校验参与者用户名是否有效
//            for (String participant : storiesVO.getParticipants()) {
//                UserDataDO userDataDO = userDAO.getUserByName(participant);
//                if (userDataDO == null) {
//                    return ResultUtil.error(ErrorCode.USER_IS_NOT_EXIST);
//                }
//                uuidList.add(userDataDO.getUuid());
//            }
//            userStoriesDO.setUuid(new Gson().toJson(uuidList));
//        }
//        // 如果有图片,校验图片是否为base64格式
        return null;
    }
}
