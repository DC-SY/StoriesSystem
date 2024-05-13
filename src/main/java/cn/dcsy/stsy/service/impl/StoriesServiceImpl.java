package cn.dcsy.stsy.service.impl;

import cn.dcsy.stsy.models.voData.StoriesAddVO;
import cn.dcsy.stsy.service.StoriesService;
import cn.dcsy.stsy.utils.BaseResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

/**
 * @author DC_DC
 * Date: 2024/5/13/23:54
 */
public class StoriesServiceImpl implements StoriesService {
    // 添加故事
    @Override
    public ResponseEntity<BaseResponse> addStories(HttpServletRequest request, StoriesAddVO storiesVO) {
        return null;
    }
}
