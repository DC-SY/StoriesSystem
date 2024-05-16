package cn.dcsy.stsy.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.jetbrains.annotations.NotNull;
import org.mindrot.jbcrypt.BCrypt;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

/**
 * 处理字符串的工具类
 *
 * @author DC_DC
 * Date: 2024/5/8/11:02
 */
public class StringUtil {
    /**
     * 生成一个唯一的UUID字符串。
     *
     * @return 返回一个随机生成的UUID字符串。
     */
    public static String createUuid() {
        return UUID.randomUUID().toString();
    }

    /**
     * 对密码进行加密处理。
     * 使用BCrypt算法结合SHA-256散列函数来增强密码的安全性。
     *
     * @param password 需要加密的原始密码。
     * @return 加密后的密码字符串。
     */
    public static String passwordEncryption(String password) {
        // 使用BCrypt算法生成一个安全的盐值，然后将密码经过SHA-256散列后，再用BCrypt算法进行加密
        return BCrypt.hashpw(DigestUtils.sha256Hex(password), BCrypt.gensalt());
    }

    /**
     * 检查提供的密码是否与给定的哈希匹配。
     * 该方法使用BCrypt算法来验证密码的一致性。
     *
     * @param password 需要验证的明文密码。不能为空。
     * @param hash     存储的密码哈希值。不能为空。
     * @return 返回一个布尔值，如果密码匹配，则返回true；否则返回false。
     */
    @NotNull
    public static Boolean passwordCheck(@NotNull String password, @NotNull String hash) {
        // 使用BCrypt算法验证密码是否匹配哈希值
        return BCrypt.checkpw(DigestUtils.sha256Hex(password), hash);
    }


    /**
     * 检查提供的字符串是否是Base64编码的字符串。
     *
     * @param base64Image 需要检查的字符串列表。
     * @return 返回一个布尔值，如果字符串是Base64编码的，则返回true；否则返回false。
     */
    public static Boolean isBase64(@NotNull List<String> base64Image) {
        for (String photo : base64Image) {
            if (!photo.startsWith("data:image/")) {
                return false;
            }
        }
        return true;
    }

    /**
     * 对Base64编码的图片列表进行解码。
     *
     * @param base64Images Base64编码的图片列表。
     * @return 解码后的图片列表。
     * @throws IOException 如果解码失败。
     */
    public static List<BufferedImage> decodeBase64Images(List<String> base64Images) throws IOException {
    List<BufferedImage> images = new ArrayList<>();
    for (String base64Image : base64Images) {
        String base64Data = base64Image.split(",")[1];
        byte[] imageBytes = Base64.getDecoder().decode(base64Data);
        images.add(ImageIO.read(new ByteArrayInputStream(imageBytes)));
    }
    return images;
}
}
