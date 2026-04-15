package com.app.worktest.pro.util;

import com.app.worktest.pro.constant.NhsaConstants;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * AES加解密工具类
 * <p>
 * 提供AES加密解密功能，用于Token获取接口的请求参数加密。
 * </p>
 *
 * @author MiniMax Agent
 * @version 1.0
 * @since 2026-03-23
 */
@Slf4j
public final class AesUtils {

    /**
     * 私有构造函数，防止实例化
     */
    private AesUtils() {
        throw new IllegalStateException("工具类不允许实例化");
    }

    /**
     * AES加密
     * <p>
     * 使用AES/ECB/PKCS5Padding模式，将内容加密为Base64字符串。
     * </p>
     *
     * @param content 待加密内容
     * @param aesKey  AES密钥（长度需要>=16位）
     * @return Base64编码的加密字符串
     * @throws AesException AES加密失败时抛出
     */
    public static String encrypt(String content, String aesKey) throws AesException {
        if (content == null || content.isEmpty()) {
            log.warn("AES加密失败：内容为空");
            return null;
        }
        if (aesKey == null || aesKey.length() < 16) {
            log.warn("AES加密失败：密钥长度不足");
            throw new AesException("AES密钥长度必须>=16位");
        }

        try {
            // 截取前16位作为密钥
            String key = aesKey.substring(0, 16);
            byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);

            SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, NhsaConstants.AES_ALGORITHM);
            Cipher cipher = Cipher.getInstance(NhsaConstants.AES_CIPHER_PADDING);
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);

            byte[] encrypted = cipher.doFinal(content.getBytes(StandardCharsets.UTF_8));
            String result = Base64.getEncoder().encodeToString(encrypted);

            log.debug("AES加密成功，原始长度: {}，加密后长度: {}", content.length(), result.length());
            return result;
        } catch (Exception e) {
            log.error("AES加密异常: {}", e.getMessage());
            throw new AesException("AES加密失败", e);
        }
    }

    /**
     * AES解密
     * <p>
     * 使用AES/ECB/PKCS5Padding模式，解密Base64编码的密文。
     * </p>
     *
     * @param content Base64编码的加密字符串
     * @param aesKey  AES密钥（长度需要>=16位）
     * @return 解密后的字符串
     * @throws AesException AES解密失败时抛出
     */
    public static String decrypt(String content, String aesKey) throws AesException {
        if (content == null || content.isEmpty()) {
            log.warn("AES解密失败：内容为空");
            return null;
        }
        if (aesKey == null || aesKey.length() < 16) {
            log.warn("AES解密失败：密钥长度不足");
            throw new AesException("AES密钥长度必须>=16位");
        }

        try {
            // 截取前16位作为密钥
            String key = aesKey.substring(0, 16);
            byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);

            SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, NhsaConstants.AES_ALGORITHM);
            Cipher cipher = Cipher.getInstance(NhsaConstants.AES_CIPHER_PADDING);
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);

            // 先进行Base64解码
            byte[] decoded = Base64.getDecoder().decode(content);
            byte[] decrypted = cipher.doFinal(decoded);

            String result = new String(decrypted, StandardCharsets.UTF_8);
            log.debug("AES解密成功，密文长度: {}，解密后长度: {}", content.length(), result.length());
            return result;
        } catch (IllegalArgumentException | NoSuchAlgorithmException | javax.crypto.NoSuchPaddingException |
                 InvalidKeyException | javax.crypto.BadPaddingException | javax.crypto.IllegalBlockSizeException e) {
            log.error("AES解密异常: {}", e.getMessage());
            throw new AesException("AES解密失败", e);
        }
    }

    /**
     * AES加解密异常类
     */
    public static class AesException extends Exception {
        public AesException(String message) {
            super(message);
        }

        public AesException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
