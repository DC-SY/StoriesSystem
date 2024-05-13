package cn.dcsy.stsy.models.voData;

import java.util.List;

import com.google.gson.Gson;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author DC_DC
 * Date: 2024/5/13/23:38
 */
public class StoriesAddVO {
    // 故事创建者的uuid
    private String auid;
    // 故事名称
    private String name;
    // 故事类型
    private String type;
    // 故事开始时间
    private String startTime;
    // 故事结束时间
    private String endTime;
    // 故事内容
    private String content;
    // 故事图片
    private List<MultipartFile> photos;
    // 作者情绪
    private String mood;
    // 故事发生地点
    private String place;
    // 故事状态
    private String status;
    // 故事参与者的用户名
    private Gson participants;
}
