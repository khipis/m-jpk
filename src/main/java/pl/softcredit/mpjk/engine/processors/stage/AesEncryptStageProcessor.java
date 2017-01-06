package pl.softcredit.mpjk.engine.processors.stage;

import org.slf4j.Logger;

import pl.softcredit.mpjk.JpkException;
import pl.softcredit.mpjk.core.configuration.JpkConfiguration;
import pl.softcredit.mpjk.engine.processors.JpkProcessor;

import java.io.File;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import static java.nio.file.Files.readAllBytes;
import static java.nio.file.Paths.get;
import static org.apache.commons.io.FileUtils.writeByteArrayToFile;
import static org.slf4j.LoggerFactory.getLogger;
import static pl.softcredit.mpjk.engine.utils.JpkCrypt.encryptAES256;
import static pl.softcredit.mpjk.engine.utils.JpkUtils.getPathForAesEncryptStage;
import static pl.softcredit.mpjk.engine.utils.JpkUtils.getPathForKeyGeneratorStage;
import static pl.softcredit.mpjk.engine.utils.JpkUtils.getPathForVectorGeneratorStage;
import static pl.softcredit.mpjk.engine.utils.JpkUtils.getPathForZipStage;

public class AesEncryptStageProcessor implements JpkProcessor {

    private static final Logger LOGGER = getLogger(AesEncryptStageProcessor.class);

    @Override
    public void process(JpkConfiguration config) throws JpkException {

        String aesFileOutputPath = getPathForAesEncryptStage(config);
        LOGGER.info("Generating AES encrypted file to: " + aesFileOutputPath);

        try {
            byte[] key = readAllBytes(get(getPathForKeyGeneratorStage(config)));
            byte[] vector = readAllBytes(get(getPathForVectorGeneratorStage(config)));
            byte[] zippedFileToEncrypt = readAllBytes(get(getPathForZipStage(config)));
            byte[] aesEncryptedBytes = encryptAES256(key, vector, zippedFileToEncrypt);

            writeByteArrayToFile(new File(aesFileOutputPath), aesEncryptedBytes);
        } catch (IOException e) {
            throw new JpkException(e);
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error("Problem with AES algorithm (probably no JCE in JRE)");
            throw new JpkException(e);
        } catch (InvalidKeyException e) {
            throw new JpkException(e);
        } catch (InvalidAlgorithmParameterException e) {
            LOGGER.error("Problem with AES algorithm parameters");
            throw new JpkException(e);
        } catch (NoSuchPaddingException e) {
            LOGGER.error("Problem with AES algorithm (probably no JCE in JRE)");
            throw new JpkException(e);
        } catch (BadPaddingException e) {
            LOGGER.error("Problem configured AES encrypt");
            throw new JpkException(e);
        } catch (IllegalBlockSizeException e) {
            LOGGER.error("Problem configured AES encrypt");
            throw new JpkException(e);
        }
    }
}
