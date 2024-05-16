package cn.dcsy.stsy.utils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.apache.commons.codec.binary.Hex;

/**
 * @author 32841
 */
@Slf4j
public class DogeUtil {
    // 普通 API 请使用这个方法
    public static JSONObject dogeAPIGet(String apiPath, Map<String, String> params) {
        StringBuilder sb = new StringBuilder();
        for(Map.Entry<String, String> hm : params.entrySet()){
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

    public static JSONObject dogeAPIGet(String apiPath, String paramsText, Boolean jsonMode) throws IOException{
        // 这里返回值类型是 JSONObject，你也可以根据你的具体情况使用不同的 JSON 库并修改最下方 JSON 处理代码

        // 这里替换为你的多吉云永久 AccessKey 和 SecretKey，可在用户中心 - 密钥管理中查看
        // 请勿在客户端暴露 AccessKey 和 SecretKey，那样恶意用户将获得账号完全控制权
        String accessKey = "";
        String secretKey = "";

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
        conn.setRequestProperty( "Content-Type", jsonMode ? "application/json" : "application/x-www-form-urlencoded");
        conn.setRequestProperty( "Authorization", authorization);
        conn.setRequestProperty( "Content-Length", String.valueOf(paramsText.length()));
        OutputStream os = conn.getOutputStream();
        os.write(paramsText.getBytes());
        os.flush();
        os.close();
        StringBuilder retJSON = new StringBuilder();
        if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){
            String readLine;
            try (BufferedReader responseReader=new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                while((readLine=responseReader.readLine())!=null){
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
}