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
import org.json.JSONObject;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.AwsSessionCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URI;
import java.util.List;

import static cn.dcsy.stsy.utils.DogeUtil.dogeAPIGet;

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
        log.info(uuid);
        log.info(userDAO.isUuidExist(uuid).toString());
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
        if (!StringUtil.isBase64(storiesVO.getPhotos())) {
            return ResultUtil.error(ErrorCode.PHOTO_FORMAT_ERROR);
        }
        // 对图片进行解码
        try {
            List<BufferedImage> images = StringUtil.decodeBase64Images(storiesVO.getPhotos());
        } catch (IOException ioException) {
            return ResultUtil.error(ErrorCode.PHOTO_DECODE_ERROR);
        }
        // 将图片上传到多吉云服务器
        JSONObject body = new JSONObject();
        body.put("channel", "OSS_FULL");
        // 要操作的存储空间名称，注意不是 s3Bucket 值，也可以设置为 *
        body.append("scopes", "syst");
        // 初始化S3 SDK
        JSONObject api = dogeAPIGet("/auth/tmp_token.json", body);
        JSONObject credentials = api.getJSONObject("Credentials");
        AwsSessionCredentials awsCreds = AwsSessionCredentials.create(credentials.getString("accessKeyId"), credentials.getString("secretAccessKey"), credentials.getString("sessionToken"));
        S3Client s3 = S3Client.builder()
                .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                .region(Region.of("automatic"))
                // 修改为多吉云控制台存储空间 SDK 参数中的 s3Endpoint
                .endpointOverride(URI.create("https://cos.ap-shanghai.myqcloud.com"))
                .build();

        ClassPathResource classPathResource = new ClassPathResource("sql/CreateTables.sql");
        assert classPathResource.exists();
        // 多吉云控制台存储空间 SDK 参数中的 s3Bucket
        PutObjectRequest putOb = PutObjectRequest.builder()
                .bucket("s-sh-9611-syst-1258813047")
                .key("/images/" + classPathResource.getFilename() + ".sql")
                .build();
        PutObjectResponse response = s3.putObject(putOb, RequestBody.fromString("test"));
        System.out.println(response.toString());
        return ResultUtil.error(ErrorCode.PHOTO_UPLOAD_ERROR);
    }
}
