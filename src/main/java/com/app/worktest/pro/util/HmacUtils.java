package com.app.worktest.pro.util;

import com.app.worktest.pro.constant.NhsaConstants;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

/**
 * HMAC签名工具类
 * <p>
 * 提供HMAC-SHA256签名生成功能，用于Token获取接口的请求签名。
 * </p>
 *
 * @author MiniMax Agent
 * @version 1.0
 * @since 2026-03-23
 */
@Slf4j
public final class HmacUtils {

    /**
     * 私有构造函数，防止实例化
     */
    private HmacUtils() {
        throw new IllegalStateException("工具类不允许实例化");
    }

    /**
     * 生成HMAC-SHA256签名
     * <p>
     * 签名算法：HmacSHA256(ak + timestamp, sk)，输出十六进制字符串。
     * </p>
     *
     * @param ak        访问密钥
     * @param sk        秘钥
     * @param timestamp 时间戳
     * @return 十六进制签名字符串
     * @throws HmacException HMAC签名失败时抛出
     */
    public static String createSignature(String ak, String sk, String timestamp) throws HmacException {
        try {
            Mac mac = Mac.getInstance(NhsaConstants.HMAC_ALGORITHM);
            SecretKeySpec secretKeySpec = new SecretKeySpec(
                    sk.getBytes(StandardCharsets.UTF_8),
                    NhsaConstants.HMAC_ALGORITHM
            );
            mac.init(secretKeySpec);

            String data = ak + timestamp;
            byte[] signatureBytes = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));

            String signature = bytesToHex(signatureBytes);
            log.debug("HMAC签名生成成功 - AK: {}, Timestamp: {}", maskAk(ak), timestamp);
            return signature;
        } catch (Exception e) {
            log.error("HMAC签名生成失败: {}", e.getMessage());
            throw new HmacException("HMAC签名生成失败", e);
        }
    }

    /**
     * 字节数组转十六进制字符串
     *
     * @param bytes 字节数组
     * @return 十六进制字符串
     */
    private static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int i = 0; i < bytes.length; i++) {
            int v = bytes[i] & 0xFF;
            hexChars[i * 2] = Character.forDigit(v >>> 4, 16);
            hexChars[i * 2 + 1] = Character.forDigit(v & 0x0F, 16);
        }
        return new String(hexChars);
    }

    /**
     * 屏蔽访问密钥（用于日志输出）
     *
     * @param ak 访问密钥
     * @return 屏蔽后的密钥
     */
    private static String maskAk(String ak) {
        if (ak == null || ak.length() <= 8) {
            return "****";
        }
        return ak.substring(0, 4) + "****";
    }

    /**
     * HMAC签名异常类
     */
    public static class HmacException extends Exception {
        public HmacException(String message) {
            super(message);
        }

        public HmacException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
