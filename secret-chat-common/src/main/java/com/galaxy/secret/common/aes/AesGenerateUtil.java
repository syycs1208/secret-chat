package com.galaxy.secret.common.aes;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;

public class AesGenerateUtil {

    public static final String AES = "AES";

    private static final int LENGTH_256 = 256;  //  32字节
    private static final int LENGTH_192 = 192;  //  24字节
    private static final int LENGTH_128 = 128;  //  16字节

    public static SecretKey create(String algorithm, int length) throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(algorithm);
        keyGenerator.init(length, new SecureRandom()); // 可以选择128, 192或256位
        return keyGenerator.generateKey();
    }

    public static byte[] encode(SecretKey key) {
        return key.getEncoded();
    }

    public static SecretKey decode(byte[] data, String algorithm) {
        return new SecretKeySpec(data, algorithm);
    }


    public static byte[] encode(IvParameterSpec key) {
        return key.getIV();
    }

    public static IvParameterSpec decodeIv(byte[] ivBytes) {
        return new IvParameterSpec(ivBytes);
    }

    public static IvParameterSpec createIv(int length) throws Exception {
        byte[] ivBytes = new byte[length]; // 初始化向量长度为16字节
        new SecureRandom().nextBytes(ivBytes);
        return decodeIv(ivBytes);
    }
}
