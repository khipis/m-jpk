package pl.softcredit.mpjk.engine.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.File;

import static org.apache.commons.io.FileUtils.readFileToByteArray;
import static org.assertj.core.api.Assertions.assertThat;
import static pl.softcredit.mpjk.engine.TestPaths.VALID_FILE_PATH_FROM_RESOURCES;
import static pl.softcredit.mpjk.engine.utils.JpkCrypt.*;
import static pl.softcredit.mpjk.engine.utils.JpkCrypt.calculateSHA256;
import static pl.softcredit.mpjk.engine.utils.JpkCrypt.decryptAES256;
import static pl.softcredit.mpjk.engine.utils.JpkCrypt.encryptAES256;

@RunWith(MockitoJUnitRunner.class)
public class JpkCryptTest {

    private static final String SHA256_BASE64_ENCODED_VALID_FILE = "ZZEMD0SHu8b3AeRIOU30OcT9+nrxFTWHaXNdqnif9FQ=";

    @Test
    public void shouldEncryptAndDecryptByteArray() throws Exception {
        byte[] key = "12345678901234567890123456789012".getBytes();
        byte[] vector = "1234567890123456".getBytes();
        byte[] fileToEncrypt = readFileToByteArray(new File(VALID_FILE_PATH_FROM_RESOURCES));
        byte[] aesEncryptedBytes = encryptAES256(key, vector, fileToEncrypt);
        byte[] aesDecryptedBytes = decryptAES256(key, vector, aesEncryptedBytes);

        assertThat(aesEncryptedBytes).isNotEqualTo(aesDecryptedBytes);
        assertThat(aesDecryptedBytes).isEqualTo(fileToEncrypt);
    }

    @Test
    public void shouldCalculateSHA256() throws Exception {
        byte[] fileBytes = readFileToByteArray(new File(VALID_FILE_PATH_FROM_RESOURCES));
        byte[] sha256bytes = calculateSHA256(fileBytes);

        assertThat(encodeBase64(sha256bytes)).isEqualTo(SHA256_BASE64_ENCODED_VALID_FILE);
    }

    @Test
    public void shouldEncodeAndDecodeBase64() throws Exception {
        byte[] fileBytes = readFileToByteArray(new File(VALID_FILE_PATH_FROM_RESOURCES));
        byte[] sha256bytes = calculateSHA256(fileBytes);

        assertThat(encodeBase64(sha256bytes)).isEqualTo(SHA256_BASE64_ENCODED_VALID_FILE);
        assertThat(decodeBase64(SHA256_BASE64_ENCODED_VALID_FILE)).isEqualTo(sha256bytes);
    }

    //TODO fix after implementing rsa crypt
    @Test
    public void shouldEncodeAndDecodeBase64TestKey() throws Exception {
        byte[] bytes = "12345678901234567890123456789012".getBytes();

       // assertThat(encodeBase64(bytes)).isEqualTo(SHA256_BASE64_ENCODED_VALID_FILE);
        //assertThat(decodeBase64(SHA256_BASE64_ENCODED_VALID_FILE)).isEqualTo(bytes);
    }

    @Test
    public void shouldEncodeAndDecodeBase64TestVector() throws Exception {
        byte[] bytes = "1234567890123456".getBytes();

       // assertThat(encodeBase64(bytes)).isEqualTo(SHA256_BASE64_ENCODED_VALID_FILE);
      //  assertThat(decodeBase64(SHA256_BASE64_ENCODED_VALID_FILE)).isEqualTo(bytes);
    }

}