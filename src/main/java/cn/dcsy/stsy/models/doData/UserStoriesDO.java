package cn.dcsy.stsy.models.doData;

import lombok.Data;

/**
 * @author DC_DC
 * Date: 2024/5/20/21:18
 */
@Data
public class UserStoriesDO {
    private String ssid;
    private String auid;
    private String uuid;
    private String name;
    private String type;
    private String startTime;
    private String endTime;
    private String content;
    private String photos;
    private String mood;
    private String place;
    private String status;
}
