package com.galaxy.secret.rsa;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;

public class RsaCryptoUtil {

    public static final String RSA = "RSA";
    // 公钥加密
    public static byte[] encrypt(byte[] data, PublicKey publicKey, String algorithm) throws Exception {
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(data);
    }


    // 公钥加密
    public static byte[] encrypt(byte[] data, PublicKey publicKey, String algorithm, int publicPageSize) throws Exception {
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        int total = data.length / publicPageSize;
        if (0 != (data.length % publicPageSize)) {
            total += 1;
        }
        if (1 == total) {
            return cipher.doFinal(data);
        }
        byte[] buffer = cipher.doFinal(data, 0, publicPageSize);
        ByteArrayOutputStream baos = new ByteArrayOutputStream(buffer.length * total);

        baos.write(buffer);
        for (int index = 1; index < total; index++) {
            if (index == (total - 1)) {
                baos.write(cipher.doFinal(data, index * publicPageSize, data.length - index * publicPageSize));
            } else {
                cipher.doFinal(data, index * publicPageSize, publicPageSize, buffer);
                baos.write(buffer);
            }
        }
        return baos.toByteArray();
    }


    // 私钥解密
    public static byte[] decrypt(byte[] data, PrivateKey privateKey, String algorithm, int privatePageSize) throws Exception {
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        int total = data.length / privatePageSize;
        if (0 != (data.length % privatePageSize)) {
            total += 1;
        }
        if (1 == total) {
            return cipher.doFinal(data);
        }

        byte[] buffer = new byte[privatePageSize];
        int length = cipher.doFinal(data, 0, privatePageSize, buffer, 0);
        ByteArrayOutputStream baos = new ByteArrayOutputStream(buffer.length * total);
        baos.write(buffer, 0, length);
        for (int index = 1; index < total; index++) {
            if (index == (total - 1)) {
                baos.write(cipher.doFinal(data, index * privatePageSize, data.length - index * privatePageSize));
            } else {
                length = cipher.doFinal(data, index * privatePageSize, privatePageSize, buffer, 0);
                baos.write(buffer, 0, length);
            }
        }

        return baos.toByteArray();
    }


    // 私钥解密
    public static byte[] decrypt(byte[] data, PrivateKey privateKey, String algorithm) throws Exception {
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(data);
    }


    // 公钥加密
    public static byte[] encrypt(byte[] data, PrivateKey privateKey, String algorithm) throws Exception {
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        return cipher.doFinal(data);
    }

    // 私钥解密
    public static byte[] decrypt(byte[] data, PublicKey publicKey, String algorithm) throws Exception {
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        return cipher.doFinal(data);
    }

// SHA256withRSA
    // 使用私钥对数据签名
    public static byte[] sign(byte[] data, PrivateKey privateKey, String algorithm) throws Exception {
        Signature sig = Signature.getInstance(algorithm);
        sig.initSign(privateKey);
        sig.update(data);
        return sig.sign();
    }

    // SHA256withRSA
    // 验签
    public static boolean verify(byte[] data, byte[] signature, PublicKey publicKey, String algorithm) throws Exception {
        Signature sig = Signature.getInstance(algorithm);
        sig.initVerify(publicKey);
        sig.update(data);
        return sig.verify(signature);
    }

    public static final int PUB_MAX_COUNT = 245;
    public static final int PRI_MAX_COUNT = 256;

}

