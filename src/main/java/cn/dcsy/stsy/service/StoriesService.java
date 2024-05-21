package cn.dcsy.stsy.service;

import cn.dcsy.stsy.models.voData.StoriesAddVO;
import cn.dcsy.stsy.utils.BaseResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

/**
 * @author DC_DC
 * Date: 2024/5/13/23:53
 */
public interface StoriesService {
    // 添加故事
    ResponseEntity<BaseResponse> addStories(HttpServletRequest request, StoriesAddVO storiesVO) throws IOException;

    ResponseEntity<BaseResponse> getStories(HttpServletRequest request);

    ResponseEntity<BaseResponse> deleteStories(HttpServletRequest request);
}
