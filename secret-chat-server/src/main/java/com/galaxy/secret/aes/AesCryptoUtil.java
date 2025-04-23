package com.galaxy.secret.aes;

import javax.crypto.Cipher;

import javax.crypto.SecretKey;


public class AesCryptoUtil {

    private static final String AES_CBC_PKCS5 = "AES/CBC/PKCS5Padding";

    /**
     * ECB（电子代码簿）
     *
     * CBC（密码块链接）
     *
     * CFB（密码反馈）
     *
     * OFB(输出反馈)
     *
     * CTR（计数器）
     *
     * GCM（伽罗瓦/计数器模式）
     */

    /**
     * 加密
     *
     * @param data
     * @param key
     * @param algorithm
     * @return
     * @throws Exception
     */
    public static byte[] encrypt(byte[] data, SecretKey key, String algorithm) throws Exception {
        Cipher cipher = Cipher.getInstance(algorithm); // 可以选择其他模式，如"AES/ECB/PKCS5Padding"
        cipher.init(Cipher.ENCRYPT_MODE, key); // 使用相同的密钥进行解密
        return cipher.doFinal(data);
    }

    public static byte[] decrypt(byte[] data, SecretKey key, String algorithm) throws Exception {
        Cipher cipher = Cipher.getInstance(algorithm); // 可以选择其他模式，如"AES/ECB/PKCS5Padding"
        cipher.init(Cipher.DECRYPT_MODE, key); // 使用相同的密钥进行解密
        return cipher.doFinal(data);
    }
}
