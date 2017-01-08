package pl.softcredit.mpjk.engine.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.File;

import static org.apache.commons.io.FileUtils.readFileToByteArray;
import static org.assertj.core.api.Assertions.assertThat;
import static pl.softcredit.mpjk.engine.TestPaths.AES_FILE_PATH_FROM_MF_IN_RESOURCES_VERSION_1;
import static pl.softcredit.mpjk.engine.TestPaths.AES_FILE_PATH_IN_RESOURCES_VERSION_1;
import static pl.softcredit.mpjk.engine.TestPaths.RSA_KEY_FILE_PATH_IN_RESOURCES_VERSION_1;
import static pl.softcredit.mpjk.engine.TestPaths.XML_FILE_PATH_IN_RESOURCES_VERSION_1;
import static pl.softcredit.mpjk.engine.utils.JpkCrypt.calculateMD5;
import static pl.softcredit.mpjk.engine.utils.JpkCrypt.calculateSHA256;
import static pl.softcredit.mpjk.engine.utils.JpkCrypt.decodeBase64;
import static pl.softcredit.mpjk.engine.utils.JpkCrypt.decryptAES256;
import static pl.softcredit.mpjk.engine.utils.JpkCrypt.encodeBase64;
import static pl.softcredit.mpjk.engine.utils.JpkCrypt.encryptAES256;
import static pl.softcredit.mpjk.engine.utils.JpkCrypt.encryptRsa;
import static pl.softcredit.mpjk.engine.utils.JpkCrypt.loadRsaPublicKey;

@RunWith(MockitoJUnitRunner.class)
public class JpkCryptVersion2Test {

    private static final String SHA256_BASE64_ENCODED_VALID_FILE =
            "ZZEMD0SHu8b3AeRIOU30OcT9+nrxFTWHaXNdqnif9FQ=";
    private static final String MD5_BASE64_ENCODED_AES_FILE = "IybUpbd7yqmMdp3qqrLHxA==";
    private static final String MD5_BASE64_ENCODED_AES_FILE_FROM_MF = "xWAZKMSjdsZZQQ3hrvbhsQ==";
    private static final String BASE64_ENCODED_VEC_FILE_FROM_MF = "MTIzNDU2Nzg5MDEyMzQ1Ng==";
    private static final String KEY_FROM_MF = "12345678901234567890123456789012";
    private static final String VECTOR_FROM_MF = "1234567890123456";

    @Test
    public void shouldEncryptAndDecryptByteArray() throws Exception {
        byte[] fileToEncrypt = readFileToByteArray(new File(XML_FILE_PATH_IN_RESOURCES_VERSION_1));
        byte[] aesEncryptedBytes = encryptAES256(KEY_FROM_MF.getBytes(), VECTOR_FROM_MF.getBytes(), fileToEncrypt);
        byte[] aesDecryptedBytes = decryptAES256(KEY_FROM_MF.getBytes(), VECTOR_FROM_MF.getBytes(), aesEncryptedBytes);

        assertThat(aesEncryptedBytes).isNotEqualTo(aesDecryptedBytes);
        assertThat(aesDecryptedBytes).isEqualTo(fileToEncrypt);
    }

    @Test
    public void shouldCalculateSHA256() throws Exception {
        byte[] fileBytes = readFileToByteArray(new File(XML_FILE_PATH_IN_RESOURCES_VERSION_1));
        byte[] sha256bytes = calculateSHA256(fileBytes);

        assertThat(encodeBase64(sha256bytes)).isEqualTo(SHA256_BASE64_ENCODED_VALID_FILE);
    }

    @Test
    public void shouldEncodeAndDecodeBase64() throws Exception {
        byte[] fileBytes = readFileToByteArray(new File(XML_FILE_PATH_IN_RESOURCES_VERSION_1));
        byte[] sha256bytes = calculateSHA256(fileBytes);

        assertThat(encodeBase64(sha256bytes)).isEqualTo(SHA256_BASE64_ENCODED_VALID_FILE);
        assertThat(decodeBase64(SHA256_BASE64_ENCODED_VALID_FILE)).isEqualTo(sha256bytes);
    }

    @Test
    public void shouldEncodeBase64VectorFromMf() throws Exception {
        assertThat(encodeBase64(VECTOR_FROM_MF.getBytes()))
                .isEqualTo(BASE64_ENCODED_VEC_FILE_FROM_MF);
    }

    @Test
    public void shouldCalculateMD5() throws Exception {
        byte[] fileBytes = readFileToByteArray(new File(AES_FILE_PATH_IN_RESOURCES_VERSION_1));
        byte[] md5bytes = calculateMD5(fileBytes);

        assertThat(encodeBase64(md5bytes)).isEqualTo(MD5_BASE64_ENCODED_AES_FILE);

        fileBytes = readFileToByteArray(new File(AES_FILE_PATH_FROM_MF_IN_RESOURCES_VERSION_1));
        md5bytes = calculateMD5(fileBytes);

        assertThat(encodeBase64(md5bytes)).isEqualTo(MD5_BASE64_ENCODED_AES_FILE_FROM_MF);
    }

    @Test
    public void shouldLoadPublicKeyFromTestCertificateDeliveredByMf() throws Exception {
       loadRsaPublicKey(RSA_KEY_FILE_PATH_IN_RESOURCES_VERSION_1);
    }

    @Test
    public void shouldEncryptKeyByRsaWithPublicKeyFromTestCertificateDeliveredByMF() throws Exception {
        byte[] keyBytes = KEY_FROM_MF.getBytes();
        byte[] rsaBytes = encryptRsa(RSA_KEY_FILE_PATH_IN_RESOURCES_VERSION_1, keyBytes);

        assertThat(encodeBase64(rsaBytes)).hasSize(344);
    }

}