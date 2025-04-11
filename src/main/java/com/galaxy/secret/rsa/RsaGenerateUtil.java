package com.galaxy.secret.rsa;



import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class RsaGenerateUtil {

    public static final String RSA = "RSA";
    public static final int RSA_LENGTH = 2048;

    public static KeyPair create() throws NoSuchAlgorithmException {
        return create(RSA, RSA_LENGTH);
    }

    public static KeyPair create(String algorithm, int length) throws NoSuchAlgorithmException {

        KeyPairGenerator keyGen = KeyPairGenerator.getInstance(algorithm);
        keyGen.initialize(length);
        return keyGen.generateKeyPair();

    }


    public static String encodePublic(KeyPair pair) {

        return Base64.getEncoder().encodeToString(pair.getPublic().getEncoded());

    }


    public static String encodePrivate(KeyPair pair) {

        return Base64.getEncoder().encodeToString(pair.getPrivate().getEncoded());

    }

    public static PrivateKey decodePrivate(byte[] keyArray, String algorithm) throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyArray);
        return keyFactory.generatePrivate(keySpec);
    }

    public static PublicKey decodePublic(byte[] keyArray, String algorithm) throws NoSuchAlgorithmException, InvalidKeySpecException {

        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyArray);
        return keyFactory.generatePublic(keySpec);

    }

    public static void main(String[] args) throws Exception {
        KeyPair pair = create();

        System.out.println("public:" + encodePublic(pair));
        System.out.println("private:" + encodePrivate(pair));

    }

}