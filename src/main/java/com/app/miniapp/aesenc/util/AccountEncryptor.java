package com.app.miniapp.aesenc.util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
/**
 * <p>PURPOSE:
 * <p>DESCRIPTION:
 * <p>CALLED BY: wanjian
 * <p>CREATE DATE: 2025/8/19
 * <p>UPDATE DATE: 2025/8/19
 * <p>UPDATE USER:
 * <p>HISTORY: 1.0
 *
 * @author wanjian
 * @version 1.0
 * @see
 * @since java 1.8
 */
public class AccountEncryptor {
    private static final String SECRET_KEY_B64 = "VU2d7zzQteMYQzDUNQG4bw==";
//    private static final String SECRET_KEY_B64 = "uJ8fK2dL5eM9nO0pQ"; // Base64编码的密钥
    private static final byte[] SECRET_KEY = Base64.getDecoder().decode(SECRET_KEY_B64);

    // 加密方法
    public static String encrypt(String account) throws Exception {
        SecretKeySpec keySpec = new SecretKeySpec(SECRET_KEY, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);
        byte[] encrypted = cipher.doFinal(account.getBytes());
        return Base64.getEncoder().encodeToString(encrypted);
    }

    // 解密方法
    public static String decrypt(String encryptedAccount) throws Exception {
        SecretKeySpec keySpec = new SecretKeySpec(SECRET_KEY, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, keySpec);
        byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(encryptedAccount));
        return new String(decrypted);
    }

    public static void main(String[] args) throws Exception {
        if (SECRET_KEY_B64 == null) {
            System.out.println("1");
        } else if (SECRET_KEY_B64.length() > 0) {
            System.out.println("2");
        }
        System.out.println("3100021729200042638-000000000000".length());
        String encrypt0 = encrypt("310002172920999");
        System.out.println("Encrypted: " + encrypt0);
        String encrypt = encrypt("3100021729200042638-00000000000");
        System.out.println("Encrypted: " + encrypt);
        String encrype = encrypt("3100021729200042638-000000000009");
        System.out.println("Encrypted: " + encrype);

//        System.out.println("Decrypted: " + decrypt("3vxr7kTz4BYSMLntRmYLsnWLFuWz5XmBs5uE40bHwV0="));
//        System.out.println("Decrypted: " + decrypt("pbaH4K7+GP1AhN9gfJE2bQ=="));
//
//        System.out.println("Decrypted: " + decrypt("fc4a+Cjs7vdO7JWBskC/YOqPuR1HTnn41HpmpO1gYtc="));
//        System.out.println("Decrypted: " + decrypt("U2eS068R6WPd2cr9qnxE4eqPuR1HTnn41HpmpO1gYtc="));
    }
}
