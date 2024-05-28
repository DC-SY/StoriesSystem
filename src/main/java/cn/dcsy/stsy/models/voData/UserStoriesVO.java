package cn.dcsy.stsy.models.voData;

import lombok.Data;

import java.util.List;

/**
 * @author DC_DC
 * Date: 2024/5/27/20:52
 */
@Data
public class UserStoriesVO {
    private String ssid;
    private String auid;
    private String uuid;
    private String name;
    private String type;
    private String startTime;
    private String endTime;
    private String content;
    private List<String> photos;
    private String mood;
    private String place;
    private String status;
}
