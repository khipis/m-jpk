package pl.softcredit.mpjk.engine.utils;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import static javax.crypto.Cipher.DECRYPT_MODE;
import static javax.crypto.Cipher.ENCRYPT_MODE;
import static javax.crypto.Cipher.getInstance;

public class JpkCrypt {

    private JpkCrypt() {
    }

    public static byte[] encryptAES256(byte[] key, byte[] vector, byte[] fileToEncrypt)
            throws InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException,
                   IllegalBlockSizeException, NoSuchPaddingException, NoSuchAlgorithmException {
        Cipher cipher = getCipher(key, vector, ENCRYPT_MODE);
        return cipher.doFinal(fileToEncrypt);
    }

    public static byte[] decryptAES256(byte[] key, byte[] vector, byte[] fileToDecrypt)
            throws InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException,
                   IllegalBlockSizeException, NoSuchPaddingException, NoSuchAlgorithmException {
        Cipher cipher = getCipher(key, vector, DECRYPT_MODE);
        return cipher.doFinal(fileToDecrypt);
    }

    public static byte[] calculateSHA256(byte[] fileBytes) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        return digest.digest(fileBytes);
    }

    public static String encodeBase64(byte[] bytes) {
        return DatatypeConverter.printBase64Binary(bytes);
    }

    public static byte[] decodeBase64(String base64Encoded) {
        return DatatypeConverter.parseBase64Binary(base64Encoded);
    }

    private static Cipher getCipher(byte[] key, byte[] vector, int decryptMode)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
                   InvalidAlgorithmParameterException {
        SecretKey secret = new SecretKeySpec(key, "AES");
        Cipher cipher = getInstance("AES/CBC/PKCS5Padding");
        cipher.init(decryptMode, secret, new IvParameterSpec(vector));
        return cipher;
    }
}
