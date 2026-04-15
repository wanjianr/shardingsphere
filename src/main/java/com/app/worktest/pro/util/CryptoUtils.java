package com.app.worktest.pro.util;


import com.app.worktest.pro.constant.NhsaConstants;
import com.app.worktest.pro.dto.request.EncryptedRequestDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * SM4加解密工具类
 * <p>
 * 提供SM4-CBC加密解密、SHA256签名等加密相关功能。
 * 封装了对医保H5接口的请求加密和响应解密逻辑。
 * </p>
 *
 * <p>
 * 主要功能：
 * <ul>
 *   <li>SM4-CBC加密（PKCS7填充）</li>
 *   <li>SM4-CBC解密</li>
 *   <li>SHA256-Base64签名</li>
 *   <li>动态密钥管理</li>
 *   <li>自动根据接口路径选择密钥</li>
 * </ul>
 * </p>
 *
 * @author MiniMax Agent
 * @version 1.0
 * @since 2026-03-23
 */
@Slf4j
public final class CryptoUtils {

    /**
     * 私有构造函数，防止实例化
     */
    private CryptoUtils() {
        throw new IllegalStateException("工具类不允许实例化");
    }

    /**
     * 动态密钥
     * <p>
     * 由getOAuthKey接口解密后获得，后续接口使用此密钥。
     * 使用volatile保证多线程可见性。
     * </p>
     */
    private static volatile String dynamicKey = "";

    // ==================== 密钥管理 ====================

    /**
     * 设置动态密钥
     *
     * @param key 动态密钥字符串
     */
    public static void setDynamicKey(String key) {
        CryptoUtils.dynamicKey = key;
        log.debug("动态密钥已更新: {}", maskKey(key));
    }

    /**
     * 获取动态密钥
     *
     * @return 动态密钥
     */
    public static String getDynamicKey() {
        return dynamicKey;
    }

    /**
     * 清空动态密钥
     */
    public static void clearDynamicKey() {
        dynamicKey = "";
        log.debug("动态密钥已清空");
    }

    /**
     * 屏蔽密钥（用于日志输出，只显示前后各4位）
     *
     * @param key 原始密钥
     * @return 屏蔽后的密钥
     */
    private static String maskKey(String key) {
        if (StringUtils.isEmpty(key) || key.length() <= 8) {
            return "****";
        }
        return key.substring(0, 4) + "****" + key.substring(key.length() - 4);
    }

    /**
     * 根据接口路径判断是否使用私钥
     *
     * @param path 接口路径
     * @return true-使用私钥
     */
    public static boolean isUsePrivateKey(String path) {
        String normalizedPath = normalizePath(path);
        return NhsaConstants.USE_PRIVATE_KEY_URLS.contains(normalizedPath);
    }

    /**
     * 根据接口路径判断响应是否需要解密
     *
     * @param path 接口路径
     * @return true-需要解密
     */
    public static boolean isNeedDecrypt(String path) {
        String normalizedPath = normalizePath(path);
        return NhsaConstants.NEED_DECRYPT_URLS.contains(normalizedPath);
    }

    /**
     * 标准化路径（去除前缀）
     *
     * @param path 原始路径
     * @return 标准化后的路径
     */
    private static String normalizePath(String path) {
        return path.replace("/aaoi/web", "");
    }

    /**
     * 根据接口路径自动选择密钥
     * <p>
     * getOAuthKey接口使用硬编码密钥，其他接口使用动态密钥。
     * </p>
     *
     * @param path 接口路径
     * @return 加密密钥
     * @throws IllegalStateException 动态密钥未初始化时抛出
     */
    public static String getKey(String path) {
        if (isUsePrivateKey(path)) {
            log.debug("使用硬编码密钥: {}", maskKey(NhsaConstants.HARDCODED_KEY));
            return NhsaConstants.HARDCODED_KEY;
        }
        if (StringUtils.isEmpty(dynamicKey)) {
            String errorMsg = "动态密钥尚未初始化，请先调用getOAuthKey接口";
            log.error(errorMsg);
            throw new IllegalStateException(errorMsg);
        }
        return dynamicKey;
    }

    // ==================== 随机数和签名 ====================

    /**
     * 安全的随机数生成器
     */
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    /**
     * 生成32位随机十六进制字符串
     * <p>
     * 用作SM4-CBC加密的IV（初始向量）。
     * </p>
     *
     * @return 32位十六进制随机字符串
     */
    public static String generateNonce() {
        byte[] bytes = new byte[16];
        SECURE_RANDOM.nextBytes(bytes);
        return bytesToHex(bytes);
    }

    /**
     * 获取当前时间戳（秒级）
     *
     * @return Unix时间戳（秒）
     */
    public static long getTimestamp() {
        return System.currentTimeMillis() / 1000L;
    }

    /**
     * SHA256哈希并Base64编码
     *
     * @param text 待哈希的文本
     * @return Base64编码的哈希值
     */
    public static String sha256Base64(String text) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(text.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hash);
        } catch (Exception e) {
            log.error("SHA256计算失败: {}", e.getMessage());
            throw new RuntimeException("SHA256计算失败", e);
        }
    }

    // ==================== SM4加密解密 ====================

    /**
     * SM4-CBC加密
     * <p>
     * 使用SM4对称加密算法，CBC模式，PKCS7填充。
     * </p>
     *
     * @param plaintext 明文（UTF-8编码）
     * @param keyHex    32位十六进制密钥
     * @param ivHex     32位十六进制IV
     * @return Base64编码的密文
     */
    public static String sm4Encrypt(String plaintext, String keyHex, String ivHex) {
        try {
            byte[] key = hexToBytes(keyHex);
            byte[] iv = hexToBytes(ivHex);
            byte[] data = plaintext.getBytes(StandardCharsets.UTF_8);

            // PKCS7填充
            byte[] padded = pkcs7Pad(data, 16);

            // SM4-CBC加密
            byte[] encrypted = sm4CbcEncrypt(padded, key, iv);

            log.debug("SM4加密完成，原始长度: {}，加密后长度: {}", data.length, encrypted.length);
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            log.error("SM4加密失败: {}", e.getMessage());
            throw new RuntimeException("SM4加密失败", e);
        }
    }

    /**
     * SM4-CBC解密
     *
     * @param b64Cipher Base64编码的密文
     * @param keyHex    32位十六进制密钥
     * @param ivHex     32位十六进制IV
     * @return UTF-8解码的明文
     */
    public static String sm4Decrypt(String b64Cipher, String keyHex, String ivHex) {
        try {
            byte[] key = hexToBytes(keyHex);
            byte[] iv = hexToBytes(ivHex);
            byte[] cipher = Base64.getDecoder().decode(b64Cipher);

            // SM4-CBC解密
            byte[] decrypted = sm4CbcDecrypt(cipher, key, iv);

            // 去除PKCS7填充
            byte[] unpadded = pkcs7Unpad(decrypted);

            log.debug("SM4解密完成，加密长度: {}，解密后长度: {}", cipher.length, unpadded.length);
            return new String(unpadded, StandardCharsets.UTF_8);
        } catch (Exception e) {
            log.error("SM4解密失败: {}", e.getMessage());
            throw new RuntimeException("SM4解密失败", e);
        }
    }

    /**
     * SM4-CBC加密实现（使用BouncyCastle）
     */
    private static byte[] sm4CbcEncrypt(byte[] data, byte[] key, byte[] iv) throws Exception {
        org.bouncycastle.crypto.engines.SM4Engine engine = new org.bouncycastle.crypto.engines.SM4Engine();
        CBCBlockCipher cipher = new CBCBlockCipher(engine);
        cipher.init(true, new org.bouncycastle.crypto.params.ParametersWithIV(
                new KeyParameter(key), iv));

        byte[] output = new byte[data.length];
        for (int i = 0; i < data.length; i += 16) {
            cipher.processBlock(data, i, output, i);
        }
        return output;
    }

    /**
     * SM4-CBC解密实现（使用BouncyCastle）
     */
    private static byte[] sm4CbcDecrypt(byte[] data, byte[] key, byte[] iv) throws Exception {
        org.bouncycastle.crypto.engines.SM4Engine engine = new org.bouncycastle.crypto.engines.SM4Engine();
        CBCBlockCipher cipher = new CBCBlockCipher(engine);
        cipher.init(false, new org.bouncycastle.crypto.params.ParametersWithIV(
                new KeyParameter(key), iv));

        byte[] output = new byte[data.length];
        for (int i = 0; i < data.length; i += 16) {
            cipher.processBlock(data, i, output, i);
        }
        return output;
    }

    // ==================== PKCS7填充 ====================

    /**
     * PKCS7填充
     *
     * @param data      原始数据
     * @param blockSize 块大小
     * @return 填充后的数据
     */
    private static byte[] pkcs7Pad(byte[] data, int blockSize) {
        int padLength = blockSize - (data.length % blockSize);
        byte[] padded = new byte[data.length + padLength];
        System.arraycopy(data, 0, padded, 0, data.length);
        for (int i = data.length; i < padded.length; i++) {
            padded[i] = (byte) padLength;
        }
        return padded;
    }

    /**
     * 去除PKCS7填充
     *
     * @param data 填充后的数据
     * @return 去除填充后的数据
     */
    private static byte[] pkcs7Unpad(byte[] data) {
        if (data.length == 0) {
            return data;
        }
        int padLength = data[data.length - 1] & 0xFF;
        if (padLength > 0 && padLength <= 16 && padLength <= data.length) {
            // 验证填充是否合法
            boolean valid = true;
            for (int i = data.length - padLength; i < data.length; i++) {
                if ((data[i] & 0xFF) != padLength) {
                    valid = false;
                    break;
                }
            }
            if (valid) {
                byte[] unpadded = new byte[data.length - padLength];
                System.arraycopy(data, 0, unpadded, 0, unpadded.length);
                return unpadded;
            }
        }
        return data;
    }

    // ==================== 十六进制转换 ====================

    /**
     * 字节数组转十六进制字符串
     *
     * @param bytes 字节数组
     * @return 十六进制字符串
     */
    public static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) {
            sb.append(String.format("%02x", b & 0xFF));
        }
        return sb.toString();
    }

    /**
     * 十六进制字符串转字节数组
     *
     * @param hex 十六进制字符串
     * @return 字节数组
     */
    public static byte[] hexToBytes(String hex) {
        int len = hex.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4)
                    + Character.digit(hex.charAt(i + 1), 16));
        }
        return data;
    }

    // ==================== 请求加密响应解密 ====================

    /**
     * 加密请求
     * <p>
     * 根据接口路径自动选择密钥，生成完整的加密请求体。
     * </p>
     *
     * <p>
     * 返回值可直接序列化为POST请求体：
     * <pre>
     * {
     *   "noceStr": "32位随机IV",
     *   "param": "Base64密文",
     *   "timestamp": 1710816000,
     *   "signature": "SHA256-Base64签名"
     * }
     * </pre>
     * </p>
     *
     * @param jsonData 业务参数的JSON字符串
     * @param path     接口路径
     * @return 加密后的请求体
     */
    public static EncryptedRequestDTO encryptRequest(String jsonData, String path) {
        String key = getKey(path);
        String nonce = generateNonce();
        long timestamp = getTimestamp();
        String param = sm4Encrypt(jsonData, key, nonce);
        String signature = sha256Base64(nonce + param + timestamp + key);

        log.debug("请求加密完成 - 接口: {}, Nonce: {}, Timestamp: {}", path, nonce, timestamp);

        return EncryptedRequestDTO.builder()
                .noceStr(nonce)
                .param(param)
                .timestamp(timestamp)
                .signature(signature)
                .build();
    }

    /**
     * 解密响应
     * <p>
     * 根据接口路径自动选择密钥，解密响应数据。
     * </p>
     *
     * @param b64Param 加密的参数
     * @param nonce    请求时的noceStr
     * @param path     接口路径
     * @return 解密后的JSON字符串
     */
    public static String decryptResponse(String b64Param, String nonce, String path) {
        String key = getKey(path);
        String decrypted = sm4Decrypt(b64Param, key, nonce);
        log.debug("响应解密完成 - 接口: {}, 长度: {}", path, decrypted.length());
        return decrypted;
    }

    /**
     * 验证签名
     *
     * @param body 加密请求体
     * @param key  密钥
     * @return true-签名正确
     */
    public static boolean verifySignature(EncryptedRequestDTO body, String key) {
        String expected = sha256Base64(body.getNoceStr() + body.getParam() + body.getTimestamp() + key);
        boolean valid = expected.equals(body.getSignature());
        if (!valid) {
            log.warn("签名验证失败");
        }
        return valid;
    }

    // ==================== 便捷方法 ====================

    /**
     * 使用指定密钥加密
     *
     * @param jsonData 业务参数的JSON字符串
     * @param keyHex   十六进制密钥
     * @return 加密后的请求体
     */
    public static EncryptedRequestDTO encryptWithKey(String jsonData, String keyHex) {
        String nonce = generateNonce();
        long timestamp = getTimestamp();
        String param = sm4Encrypt(jsonData, keyHex, nonce);
        String signature = sha256Base64(nonce + param + timestamp + keyHex);

        return EncryptedRequestDTO.builder()
                .noceStr(nonce)
                .param(param)
                .timestamp(timestamp)
                .signature(signature)
                .build();
    }

    /**
     * 使用指定密钥解密
     *
     * @param b64Param 加密的参数
     * @param nonce    请求时的noceStr
     * @param keyHex   十六进制密钥
     * @return 解密后的JSON字符串
     */
    public static String decryptWithKey(String b64Param, String nonce, String keyHex) {
        return sm4Decrypt(b64Param, keyHex, nonce);
    }
}
