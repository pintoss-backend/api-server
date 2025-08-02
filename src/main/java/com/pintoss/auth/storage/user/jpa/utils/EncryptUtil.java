package com.pintoss.auth.storage.user.jpa.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class EncryptUtil {

    private static SecretKeySpec secretKeySpec;
    private static String algorithm;

    private EncryptUtil() {} // 생성 방지

    public static void init(String key, String algo) {
        secretKeySpec = new SecretKeySpec(key.getBytes(), algo);
        algorithm = algo;
    }

    private static Cipher initCipher(int mode) throws Exception {
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(mode, secretKeySpec);
        return cipher;
    }

    public static String encrypt(String plainText) {
        if (plainText == null) return null;
        try {
            Cipher cipher = initCipher(Cipher.ENCRYPT_MODE);
            byte[] encrypted = cipher.doFinal(plainText.getBytes());
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            throw new RuntimeException("암호화 실패", e);
        }
    }

    public static String decrypt(String encryptedText) {
        if (encryptedText == null) return null;
        try {
            Cipher cipher = initCipher(Cipher.DECRYPT_MODE);
            byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
            return new String(decrypted);
        } catch (Exception e) {
            throw new RuntimeException("복호화 실패", e);
        }
    }
}
