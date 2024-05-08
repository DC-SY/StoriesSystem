package cn.dcsy.stsy.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.jetbrains.annotations.NotNull;
import org.mindrot.jbcrypt.BCrypt;

import java.util.UUID;

/**
 * 处理字符串的工具类
 * @author DC_DC
 * Date: 2024/5/8/11:02
 */
public class StringUtil {
    /**
     * 生成一个唯一的UUID字符串。
     *
     * @return 返回一个随机生成的UUID字符串。
     */
    public static String createUuid(){
        return UUID.randomUUID().toString();
    }

    /**
     * 对密码进行加密处理。
     * 使用BCrypt算法结合SHA-256散列函数来增强密码的安全性。
     *
     * @param password 需要加密的原始密码。
     * @return 加密后的密码字符串。
     */
    public static String passwordEncryption(String password){
        // 使用BCrypt算法生成一个安全的盐值，然后将密码经过SHA-256散列后，再用BCrypt算法进行加密
        return BCrypt.hashpw(DigestUtils.sha256Hex(password), BCrypt.gensalt());
    }

    /**
     * 检查提供的密码是否与给定的哈希匹配。
     * 该方法使用BCrypt算法来验证密码的一致性。
     *
     * @param password 需要验证的明文密码。不能为空。
     * @param hash 存储的密码哈希值。不能为空。
     * @return 返回一个布尔值，如果密码匹配，则返回true；否则返回false。
     */
    @NotNull
    public static Boolean passwordCheck(@NotNull String password, @NotNull String hash) {
        // 使用BCrypt算法验证密码是否匹配哈希值
        return BCrypt.checkpw(DigestUtils.sha256Hex(password), hash);
    }

}
