package pl.softcredit.mpjk.engine.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPublicKey;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import static javax.crypto.Cipher.DECRYPT_MODE;
import static javax.crypto.Cipher.ENCRYPT_MODE;
import static javax.crypto.Cipher.getInstance;
import static javax.xml.bind.DatatypeConverter.parseBase64Binary;
import static javax.xml.bind.DatatypeConverter.printBase64Binary;

public class JpkCrypt {

    private JpkCrypt() {
    }

    public static byte[] encryptRsa(String certificatePath, byte[] bytesToEncrypt)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException,
                   BadPaddingException, IllegalBlockSizeException, CertificateException,
                   IOException {
        Cipher cipher = getInstance("RSA/ECB/PKCS1Padding");
        RSAPublicKey rsaPublicKey = loadRsaPublicKey(certificatePath);
        cipher.init(ENCRYPT_MODE, rsaPublicKey);

        return cipher.doFinal(bytesToEncrypt);
    }

    public static RSAPublicKey loadRsaPublicKey(String certificatePath)
            throws CertificateException, IOException {
        try (InputStream inStream = new FileInputStream(certificatePath)) {
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            X509Certificate cert = (X509Certificate) cf.generateCertificate(inStream);
            return (RSAPublicKey) cert.getPublicKey();
        }
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

    public static byte[] calculateMD5(byte[] fileBytes) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("MD5");
        return digest.digest(fileBytes);
    }

    public static String encodeBase64(byte[] bytes) {
        return printBase64Binary(bytes);
    }

    static byte[] decodeBase64(String base64Encoded) {
        return parseBase64Binary(base64Encoded);
    }

    public static byte[] generateKeyBits(int bitsCount) throws NoSuchAlgorithmException {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(bitsCount);
        SecretKey secretKey = keyGen.generateKey();
        return secretKey.getEncoded();
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
