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

import static java.lang.System.*;
import static javax.crypto.Cipher.DECRYPT_MODE;
import static javax.crypto.Cipher.ENCRYPT_MODE;
import static javax.crypto.Cipher.getInstance;
import static javax.xml.bind.DatatypeConverter.parseBase64Binary;
import static javax.xml.bind.DatatypeConverter.printBase64Binary;

public class JpkCrypt {

    private JpkCrypt() {
    }

    public static byte[] encryptRsa(String certificatePath, byte[] fileToEncrypt)
            throws NoSuchPaddingException, NoSuchAlgorithmException, IOException,
                   CertificateException, InvalidKeyException, BadPaddingException,
                   IllegalBlockSizeException {

        try (InputStream inStream = new FileInputStream(certificatePath)) {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");

            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            X509Certificate cert = (X509Certificate) cf.generateCertificate(inStream);


            RSAPublicKey pubkey = (RSAPublicKey) cert.getPublicKey();
            byte[] tempPub = pubkey.getEncoded();

            out.println("Public key from certificate file:\n" + hex(new String(tempPub)) + "\n");
            out.println("Public Key Algorithm = " + cert.getPublicKey().getAlgorithm() + "\n");
            out.println("Plain message:\n" + new String(fileToEncrypt) + "\n");

            cipher.init(Cipher.ENCRYPT_MODE, pubkey);

            return cipher.doFinal(fileToEncrypt);
        }

    }


    private static String hex(String binStr) {

        String newStr = new String();

        try {
            String hexStr = "0123456789ABCDEF";
            byte[] p = binStr.getBytes();
            for (int k = 0; k < p.length; k++) {
                int j = (p[k] >> 4) & 0xF;
                newStr = newStr + hexStr.charAt(j);
                j = p[k] & 0xF;
                newStr = newStr + hexStr.charAt(j) + " ";
            }
        } catch (Exception e) {
            out.println("Failed to convert into hex values: " + e);
        }
        return newStr;
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
