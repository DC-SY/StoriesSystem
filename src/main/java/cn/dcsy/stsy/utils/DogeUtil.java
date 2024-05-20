package cn.dcsy.stsy.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.auth.credentials.AwsSessionCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author 32841
 */
@Component
@Slf4j
public class DogeUtil {
    private static String accessKey;
    private static String secretKey;

    // 上传文件到多吉云
    public static List<String> uploadUtil(List<BufferedImage> images, String uuid) throws IOException {
        List<InputStream> streams = new ArrayList<>();

        for (BufferedImage image : images) {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(image, "jpg", os);
            byte[] buffer = os.toByteArray();
            InputStream is = new ByteArrayInputStream(buffer);
            streams.add(is);
        }
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
        // 多吉云控制台存储空间 SDK 参数中的 s3Bucket
        List<String> urls = new ArrayList<>();
        for (InputStream stream : streams) {
            String key = "images/" + uuid + "_" + System.currentTimeMillis() + ".jpg";
            PutObjectRequest putOb = PutObjectRequest.builder()
                    .bucket("s-sh-9611-syst-1258813047")
                    .key(key)
                    .contentType("image/jpeg")
                    .build();

            PutObjectResponse response = s3.putObject(putOb, RequestBody.fromInputStream(stream, stream.available()));
            if (!response.sdkHttpResponse().isSuccessful()) {
                return null;
            }
            urls.add("https://i-cdn.dc-sy.cn/" + key);
        }
        return urls;
    }

    // 普通 API 请使用这个方法
    public static JSONObject dogeAPIGet(String apiPath, Map<String, String> params) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> hm : params.entrySet()) {
            try {
                sb.append(URLEncoder.encode(hm.getKey(), String.valueOf(StandardCharsets.UTF_8))).append('=').append(URLEncoder.encode(hm.getValue(), String.valueOf(StandardCharsets.UTF_8))).append("&");
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        }
        String bodyText = sb.toString().replace("&$", "");
        try {
            return dogeAPIGet(apiPath, bodyText, false);
        } catch (IOException e) {
            log.info(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    // 要求请求内容 Body 是一个 JSON 的 API，请使用这个方法
    public static JSONObject dogeAPIGet(String apiPath, JSONObject params) {
        String bodyText = params.toString();
        try {
            return dogeAPIGet(apiPath, bodyText, true);
        } catch (IOException e) {
            log.info(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    // 无参数 API
    public static JSONObject dogeAPIGet(String apiPath) {
        try {
            return dogeAPIGet(apiPath, "", true);
        } catch (IOException e) {
            log.info(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    public static JSONObject dogeAPIGet(String apiPath, String paramsText, Boolean jsonMode) throws IOException {
        // 这里返回值类型是 JSONObject，你也可以根据你的具体情况使用不同的 JSON 库并修改最下方 JSON 处理代码

        // 这里替换为你的多吉云永久 AccessKey 和 SecretKey，可在用户中心 - 密钥管理中查看
        // 请勿在客户端暴露 AccessKey 和 SecretKey，那样恶意用户将获得账号完全控制权


        String signStr = apiPath + "\n" + paramsText;
        String sign;
        try {
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(new SecretKeySpec(secretKey.getBytes(), "HmacSHA1"));
            // 这里 Hex 来自 org.apache.commons.codec.binary.Hex
            sign = new String(new Hex().encode(mac.doFinal(signStr.getBytes())), StandardCharsets.UTF_8);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            log.info(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
        String authorization = "TOKEN " + accessKey + ':' + sign;

        URL u = new URL("https://api.dogecloud.com" + apiPath);
        HttpURLConnection conn = (HttpURLConnection) u.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", jsonMode ? "application/json" : "application/x-www-form-urlencoded");
        conn.setRequestProperty("Authorization", authorization);
        conn.setRequestProperty("Content-Length", String.valueOf(paramsText.length()));
        OutputStream os = conn.getOutputStream();
        os.write(paramsText.getBytes());
        os.flush();
        os.close();
        StringBuilder retJSON = new StringBuilder();
        if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
            String readLine;
            try (BufferedReader responseReader = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                while ((readLine = responseReader.readLine()) != null) {
                    retJSON.append(readLine).append("\n");
                }
            }
            JSONObject ret = new JSONObject(retJSON.toString());
            if (ret.getInt("code") != 200) {
                System.err.println("{\"error\":\"API 返回错误：" + ret.getString("msg") + "\"}");
            } else {
                return ret.getJSONObject("data");
            }
        } else {
            System.err.println("{\"error\":\"网络错误：" + conn.getResponseCode() + "\"}");
        }
        return null;
    }

    @Value("${doge.AccessKey}")
    public void setAccessKey(String accessKey) {
        DogeUtil.accessKey = accessKey;
    }

    @Value("${doge.SecretKey}")
    public void setSecretKey(String secretKey) {
        DogeUtil.secretKey = secretKey;
    }
}