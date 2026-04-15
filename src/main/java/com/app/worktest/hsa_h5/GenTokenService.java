package com.app.worktest.hsa_h5;

import com.alibaba.fastjson.JSONObject;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class GenTokenService {

    private static class WrapperResponse<T> {
        private int code = 0;
        private String type;
        private String message;
        private T data;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public T getData() {
            return data;
        }

        public void setData(T data) {
            this.data = data;
        }
    }


    private static final String ENCODING = "UTF-8";
    private static final String AES_ALGORITHM = "AES";
    private static final String CIPHER_PADDING = "AES/ECB/PKCS5Padding";

    private final static char[] hexArray = "0123456789abcdef".toCharArray();

    private final static String TOKEN_URL = "https://datashare-test.nhsa.gov.cn:30080/aaoi/web/auth/login/authToken";

    private final static String QUERY_INTFLOG_BY_TOKEN_KEY_URL = "https://datashare-test.nhsa.gov.cn:30080/aoi/web/insurance/queryIntfLogByTokenKey";

    public static void main(String[] args) throws Exception {
        String bankH5Token = getBankH5Token();
    }

    public static String getBankH5Token()  throws Exception {
        // todo 实际ak、sk通过@Value配置文件获取
        String ak = "051251d895e3408780aceba262de1f56";
        String sk = "6654bb58ecd749fc842eaca8b6d03e6f";
        // todo 实际参数根据方法入参获取
        JSONObject param = new JSONObject();
        param.put("admdvs", "100000");
        param.put("psnCertType", "01");
        param.put("certno", "210682199912120912");
        param.put("psnName", "王开疆");
        param.put("mob", "15612345678");
        param.put("agentPsnCertType", "01");
        param.put("agentCertno", "210682198011014067");
        param.put("agentPsnName", "赵德胜");
        param.put("agentMob", "15612345678");
        param.put("adultFlag", true);
        param.put("channel", "92");
        param.put("claimType","0");
        System.out.println(param.toJSONString());
        String res = getToken(ak, sk, param.toJSONString());
        System.out.println(res);
        WrapperResponse wrapperResponse = JSONObject.parseObject(res, WrapperResponse.class);
        String token = wrapperResponse.getData().toString();
        System.out.println(token);
        return token;
    }

    public static String getToken(String ak, String sk, String JsonParam) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        String timestamp = Long.toString(System.currentTimeMillis());
        String signature = createSignature(ak, sk, timestamp);
        String data = encrypt(JsonParam, signature);
        JSONObject param = new JSONObject();
        param.put("data", data);
        HttpURLConnection conn = null;
        URL url = new URL(TOKEN_URL);
        conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setRequestMethod("POST");
        conn.setUseCaches(false);
        conn.setInstanceFollowRedirects(true);
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty("hsa-dsm-signature", signature);
        conn.setRequestProperty("hsa-dsm-timestamp", timestamp);
        conn.setRequestProperty("hsa-dsm-ak", ak);
        conn.connect();
        OutputStream os = conn.getOutputStream();
        os.write(param.toJSONString().getBytes());
        os.flush();
        os.close();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        InputStream inputStream = conn.getInputStream();
        byte[] bytes = new byte[1024];
        int readBytes;
        while ((readBytes = inputStream.read(bytes)) != -1) {
            byteArrayOutputStream.write(bytes, 0, readBytes);
        }
        bytes = byteArrayOutputStream.toByteArray();
        inputStream.close();
        conn.disconnect();
        String response = new String(bytes, "utf-8");
        return response;
    }

    private static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0Xff;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    /**
     *
     * @param ak
     * @param sk
     * @param timestamp
     * @return 返回当前签名的结果
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     */
    private static String createSignature(String ak, String sk, String timestamp)
            throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secret_key = new SecretKeySpec(sk.getBytes("UTF-8"), "HmacSHA256");
        sha256_HMAC.init(secret_key);
        byte[] bytes = sha256_HMAC.doFinal((ak + timestamp).getBytes("UTF-8"));
        return bytesToHex(bytes);
    }

    /**
     *
     * @param content
     * @param aesKey
     * @return 返回加密的结果
     */
    public static String encrypt(String content, String aesKey) {
        if (content == null || content == "") {
            return null;
        }
        //判断秘钥是否大于16位
        if (aesKey != null && aesKey.length() >= 16) {
            try {
                aesKey = aesKey.substring(0, 16);
                //对密码进行编码
                byte[] bytes = aesKey.getBytes(ENCODING);
                //设置加密算法，生成秘钥
                SecretKeySpec skeySpec = new SecretKeySpec(bytes, AES_ALGORITHM);
                // "算法/模式/补码方式"
                Cipher cipher = Cipher.getInstance(CIPHER_PADDING);
                //选择加密
                cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
                //根据待加密内容生成字节数组
                byte[] encrypted = cipher.doFinal(content.getBytes(ENCODING));
                //返回base64字符串
                return Base64.getEncoder().encodeToString(encrypted);
            } catch (Exception e) {
                throw new RuntimeException("加密当前的数据失败，请检查");
            }

        } else {
            return null;
        }
    }

    /**
     *
     * @param content
     * @param aesKey
     * @return 返回解密的结果
     */
    public static String decrypt(String content, String aesKey) {
        if (content == null || content == "") {
            return null;
        }
        //判断秘钥是否大于16位
        if (aesKey != null && aesKey.length() >= 16) {
            try {
                aesKey = aesKey.substring(0, 16);
                //对密码进行编码
                byte[] bytes = aesKey.getBytes(ENCODING);
                //设置解密算法，生成秘钥
                SecretKeySpec skeySpec = new SecretKeySpec(bytes, AES_ALGORITHM);
                // "算法/模式/补码方式"
                Cipher cipher = Cipher.getInstance(CIPHER_PADDING);
                //选择解密
                cipher.init(Cipher.DECRYPT_MODE, skeySpec);

                //先进行Base64解码
                byte[] decodeBase64 = Base64.getDecoder().decode(content);

                //根据待解密内容进行解密
                byte[] decrypted = cipher.doFinal(decodeBase64);
                //将字节数组转成字符串
                return new String(decrypted, ENCODING);
            } catch (Exception e) {
                throw new RuntimeException("解密当前的数据失败，请检查");
            }

        } else {
            return null;
        }
    }
}
