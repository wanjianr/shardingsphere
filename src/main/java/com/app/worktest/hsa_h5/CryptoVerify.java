package com.app.worktest.hsa_h5;

import org.apache.commons.collections.SetUtils;
import org.bouncycastle.crypto.engines.SM4Engine;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashSet;
import java.util.Set;

/**
 * H5 请求加解密工具类
 * <p>
 * 对应 Python 版 crypto_verify.py，算法：
 *   - 加密：SM4-CBC + PKCS7 填充，输出 Base64
 *   - 签名：SHA-256(noceStr + param + timestamp + key) → Base64
 *   - 密钥：先用硬编码密钥换取动态密钥（getOAuthKey），后续接口使用动态密钥
 * </p>
 */
public class CryptoVerify {

    // -----------------------------------------------------------------------
    // 1. 密钥常量
    // -----------------------------------------------------------------------

    /** 前端硬编码密钥（Pr=true 时 Qr() 返回此值） */
    public static final String HARDCODED_KEY = "fedcba9876543210fedcba9876543210";

    /**
     * 动态密钥：由 getOAuthKey 接口解密后获得，使用前须先赋值。
     * 多线程场景建议改用 volatile 或 AtomicReference。
     */
    public static volatile String dynamicKey = "";

    // -----------------------------------------------------------------------
    // 2. URL 分类
    // -----------------------------------------------------------------------

    /** 使用硬编码密钥的接口路径集合（对应 Python USE_PRIVATE_KEY_URLS） */
    private static final Set<String> USE_PRIVATE_KEY_URLS = new HashSet<>(
            Arrays.asList(
            "/auth/login/appToken",
            "/auth/login/wxOpenidToken",
            "/auth/login/alipayOpenidToken",
            "/webService/getPsnCertTypeList",
            "/notice/getStatus",
            "/ali/service/getAliLogin",
            "/auth/security/getOAuthKey",
            "/ali/service/getAliPayUid",
            "/wx/service/getWxLogin",
            "/wx/service/getAuthCodeDetails",
            "/auth/login/getDetailsByAppAccessToken",
            "/auth/login/getDialogContent"
    ));

    /** 响应需要 SM4 解密的接口路径集合（对应 Python NEED_DECRYPT_URLS） */
    private static final Set<String> NEED_DECRYPT_URLS = new HashSet<>(
            Arrays.asList(
            "/auth/login/v2/checkToken",
            "/wx/service/getAuthCodeDetails",
            "/ali/service/getAliPayUid",
            "/auth/insuAuth/listOptLogByPsnNo",
            "/auth/insuAuth/listIntfUseLogByPsnNo",
            "/auth/insuAuth/getInsuInfoByCert",
            "/auth/insuAuth/getSetlList",
            "/setl/query/getSetlDetailsById"
    ));


    private static final SecureRandom RANDOM = new SecureRandom();

    // -----------------------------------------------------------------------
    // 3. 路径工具
    // -----------------------------------------------------------------------

    /** 去掉 /aaoi/web 前缀，与集合中的短路径对齐（对应 Python _normalize） */
    private static String normalize(String path) {
        return path.replace("/aaoi/web", "");
    }

    public static boolean isUsePrivateKey(String path) {
        return USE_PRIVATE_KEY_URLS.contains(normalize(path));
    }

    public static boolean isNeedDecrypt(String path) {
        return NEED_DECRYPT_URLS.contains(normalize(path));
    }

    /**
     * 根据接口路径自动选择密钥（对应 Python _get_key / 前端 Qr 函数）。
     */
    public static String getKey(String path) {
        if (isUsePrivateKey(path)) {
            return HARDCODED_KEY;
        }
        if (dynamicKey == null || dynamicKey.isEmpty()) {
            throw new IllegalStateException(
                    "动态密钥尚未初始化！请先调用 getOAuthKey 接口并将解密结果赋值给 CryptoVerify.dynamicKey。");
        }
        return dynamicKey;
    }

    // -----------------------------------------------------------------------
    // 4. 工具函数
    // -----------------------------------------------------------------------

    /**
     * 生成 32 位随机十六进制字符串，用作 SM4-CBC 的 IV（对应 Python generate_nonce / 前端 Tr）。
     */
    public static String generateNonce() {
        byte[] bytes = new byte[16];
        RANDOM.nextBytes(bytes);
        return bytesToHex(bytes);
    }

    /** 秒级 Unix 时间戳（对应 Python get_timestamp / 前端 Ir）。 */
    public static long getTimestamp() {
        return System.currentTimeMillis() / 1000L;
    }

    /**
     * SHA-256(text) → Base64（对应 Python sha256_b64 / 前端 Nr）。
     */
    public static String sha256Base64(String text) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(text.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(digest);
        } catch (Exception e) {
            throw new RuntimeException("SHA-256 计算失败", e);
        }
    }

    // -----------------------------------------------------------------------
    // 5. SM4-CBC 加密（对应 Python sm4_encrypt）
    // -----------------------------------------------------------------------

    /**
     * SM4 CBC 模式加密，PKCS7 填充，输出 Base64 字符串。
     *
     * @param plaintext 待加密的 UTF-8 字符串（通常是 JSON 序列化结果）
     * @param keyHex    32 字符十六进制密钥（16 字节）
     * @param ivHex     32 字符十六进制 IV（即请求中的 noceStr）
     * @return Base64 编码的密文字符串（即请求体中的 param 字段）
     */
    public static String sm4Encrypt(String plaintext, String keyHex, String ivHex) {
        try {
            byte[] key  = hexToBytes(keyHex);
            byte[] iv   = hexToBytes(ivHex);
            byte[] data = plaintext.getBytes(StandardCharsets.UTF_8);

            // PKCS7 填充
            int pad = 16 - (data.length % 16);
            byte[] padded = new byte[data.length + pad];
            System.arraycopy(data, 0, padded, 0, data.length);
            for (int i = data.length; i < padded.length; i++) {
                padded[i] = (byte) pad;
            }

            CBCBlockCipher cipher = new CBCBlockCipher(new SM4Engine());
            cipher.init(true, new ParametersWithIV(new KeyParameter(key), iv));

            byte[] output = new byte[padded.length];
            for (int i = 0; i < padded.length; i += 16) {
                cipher.processBlock(padded, i, output, i);
            }
            return Base64.getEncoder().encodeToString(output);
        } catch (Exception e) {
            throw new RuntimeException("SM4 加密失败", e);
        }
    }

    // -----------------------------------------------------------------------
    // 6. SM4-CBC 解密（对应 Python sm4_decrypt）
    // -----------------------------------------------------------------------

    /**
     * SM4 CBC 模式解密，去除 PKCS7 填充，返回 UTF-8 明文字符串。
     *
     * @param b64Cipher Base64 编码的密文（服务器响应的 data / param 字段）
     * @param keyHex    32 字符十六进制密钥
     * @param ivHex     32 字符十六进制 IV（对应本次请求的 noceStr）
     * @return UTF-8 明文字符串（通常是 JSON 字符串，需再反序列化）
     */
    public static String sm4Decrypt(String b64Cipher, String keyHex, String ivHex) {
        try {
            byte[] key    = hexToBytes(keyHex);
            byte[] iv     = hexToBytes(ivHex);
            byte[] cipher = Base64.getDecoder().decode(b64Cipher);

            CBCBlockCipher decCipher = new CBCBlockCipher(new SM4Engine());
            decCipher.init(false, new ParametersWithIV(new KeyParameter(key), iv));

            byte[] plain = new byte[cipher.length];
            for (int i = 0; i < cipher.length; i += 16) {
                decCipher.processBlock(cipher, i, plain, i);
            }

            // 去除 PKCS7 填充（兼容无 padding 的情况）
            int padLen = plain[plain.length - 1] & 0xFF;
            if (padLen > 0 && padLen <= 16) {
                // 验证填充字节是否合法
                boolean valid = true;
                for (int i = plain.length - padLen; i < plain.length; i++) {
                    if ((plain[i] & 0xFF) != padLen) {
                        valid = false;
                        break;
                    }
                }
                if (valid) {
                    return new String(plain, 0, plain.length - padLen, StandardCharsets.UTF_8);
                }
            }
            return new String(plain, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("SM4 解密失败", e);
        }
    }

    // -----------------------------------------------------------------------
    // 7. 请求加密（对应 Python encrypt_request / 前端 Fr 函数）
    // -----------------------------------------------------------------------

    /**
     * 自动根据 path 选择密钥，生成完整的加密请求体。
     * <p>
     * 返回值可直接序列化为 POST body：
     * <pre>
     * {
     *   "noceStr":   "32位随机IV",
     *   "param":     "Base64密文",
     *   "timestamp": 1710816000,
     *   "signature": "SHA256-Base64签名"
     * }
     * </pre>
     * ⚠️ noceStr 需自行保存，响应解密时要用到。
     *
     * @param jsonData  业务参数的 JSON 字符串（使用紧凑格式，键值间无空格）
     * @param path      接口路径（自动决定使用哪个密钥）
     * @return 含加密字段的请求体对象
     */
    public static RequestBody encryptRequest(String jsonData, String path) {
        String key   = getKey(path);
        String nonce = generateNonce();
        long   ts    = getTimestamp();
        String param = sm4Encrypt(jsonData, key, nonce);
        String sig   = sha256Base64(nonce + param + ts + key);
        return new RequestBody(nonce, param, ts, sig);
    }

    // -----------------------------------------------------------------------
    // 8. 响应解密（对应 Python decrypt_response / 前端 jr 函数）
    // -----------------------------------------------------------------------

    /**
     * 自动根据 path 选择密钥，解密并返回原始业务 JSON 字符串。
     *
     * @param b64Param  服务器返回 JSON 中的密文字段（通常是 data 或 param）
     * @param nonce     本次请求的 noceStr（加密时生成，需自行保存）
     * @param path      接口路径（自动决定使用哪个密钥）
     * @return 解密后的 JSON 字符串
     */
    public static String decryptResponse(String b64Param, String nonce, String path) {
        String key = getKey(path);
        return sm4Decrypt(b64Param, key, nonce);
    }

    // -----------------------------------------------------------------------
    // 9. 独立工具函数（指定密钥，绕过路径判断，供调试用）
    // -----------------------------------------------------------------------

    public static RequestBody encryptWithKey(String jsonData, String keyHex) {
        String nonce = generateNonce();
        long   ts    = getTimestamp();
        String param = sm4Encrypt(jsonData, keyHex, nonce);
        String sig   = sha256Base64(nonce + param + ts + keyHex);
        return new RequestBody(nonce, param, ts, sig);
    }

    public static String decryptWithKey(String b64Param, String nonce, String keyHex) {
        return sm4Decrypt(b64Param, keyHex, nonce);
    }

    /** 校验请求体签名是否合法（对应 Python verify_signature）。 */
    public static boolean verifySignature(RequestBody body, String key) {
        String expected = sha256Base64(body.noceStr + body.param + body.timestamp + key);
        return expected.equals(body.signature);
    }

    // -----------------------------------------------------------------------
    // 10. Hex 工具
    // -----------------------------------------------------------------------

    public static byte[] hexToBytes(String hex) {
        int len = hex.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4)
                    + Character.digit(hex.charAt(i + 1), 16));
        }
        return data;
    }

    public static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) {
            sb.append(String.format("%02x", b & 0xFF));
        }
        return sb.toString();
    }

    // -----------------------------------------------------------------------
    // 11. 请求体 DTO
    // -----------------------------------------------------------------------

    /**
     * 加密后的请求体，直接 JSON 序列化后 POST 到服务器。
     */
    public static class RequestBody {
        public final String noceStr;
        public final String param;
        public final long   timestamp;
        public final String signature;

        public RequestBody(String noceStr, String param, long timestamp, String signature) {
            this.noceStr   = noceStr;
            this.param     = param;
            this.timestamp = timestamp;
            this.signature = signature;
        }

        @Override
        public String toString() {
            return String.format(
                    "{\"noceStr\":\"%s\",\"param\":\"%s\",\"timestamp\":%d,\"signature\":\"%s\"}",
                    noceStr, param, timestamp, signature);
        }
    }
}
