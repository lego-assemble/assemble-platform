package xyz.leo.lego.assemble.platform.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.SecureRandom;

/**
 * @author xuyangze
 * @date 2018/12/24 11:58 AM
 */
public class DesUtils {

    private final static Logger LOGGER = LoggerFactory.getLogger(DesUtils.class);

    /**
     * 加密
     * @param datasource byte[]
     * @param password String
     * @return byte[]
     */
    public static byte[] encrypt(byte[] datasource, String password) {
        try {
            SecureRandom random = new SecureRandom();
            DESKeySpec desKey = new DESKeySpec(password.getBytes());
            //创建一个密匙工厂，然后用它把DESKeySpec转换成
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("Des");
            SecretKey securekey = keyFactory.generateSecret(desKey);
            //Cipher对象实际完成加密操作
            Cipher cipher = Cipher.getInstance("Des");
            //用密匙初始化Cipher对象
            cipher.init(Cipher.ENCRYPT_MODE, securekey, random);
            //现在，获取数据并加密
            //正式执行加密操作
            return cipher.doFinal(datasource);
        } catch(Throwable e) {
            LOGGER.error("Des encrypt error", e);
        }

        return null;
    }
    /**
     * 解密
     * @param src byte[]
     * @param password String
     * @return byte[]
     * @throws Exception
     */
    public static byte[] decrypt(byte[] src, String password) {
        try {
            // DES算法要求有一个可信任的随机数源
            SecureRandom random = new SecureRandom();
            // 创建一个DESKeySpec对象
            DESKeySpec desKey = new DESKeySpec(password.getBytes());
            // 创建一个密匙工厂
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("Des");
            // 将DESKeySpec对象转换成SecretKey对象
            SecretKey securekey = keyFactory.generateSecret(desKey);
            // Cipher对象实际完成解密操作
            Cipher cipher = Cipher.getInstance("Des");
            // 用密匙初始化Cipher对象
            cipher.init(Cipher.DECRYPT_MODE, securekey, random);
            // 真正开始解密操作
            return cipher.doFinal(src);
        } catch (Exception e) {
            LOGGER.error("des error", e);
        }

        return null;
    }
}