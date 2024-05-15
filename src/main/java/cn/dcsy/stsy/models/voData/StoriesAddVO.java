package cn.dcsy.stsy.models.voData;

import com.google.gson.Gson;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
/**
 * @author DC_DC
 * Date: 2024/5/13/23:38
 */
@Data
public class StoriesAddVO {
    // 故事创建者的uuid
    private String auid;
    // 故事名称
    @NotBlank(message = "故事名称不能为空")
    private String name;
    // 故事类型
    private String type;
    // 故事开始时间
    @NotBlank(message = "故事开始时间不能为空")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String startTime;
    // 故事结束时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String endTime;
    // 故事内容
    @NotBlank(message = "故事内容不能为空")
    private String content;
    // 故事图片
    private List<String> photos;
    // 作者情绪
    private String mood;
    // 故事发生地点
    private String place;
    // 故事状态
    @NotBlank(message = "故事状态不能为空")
    private String status;
    // 故事参与者的用户名
    private List<String> participants;
}