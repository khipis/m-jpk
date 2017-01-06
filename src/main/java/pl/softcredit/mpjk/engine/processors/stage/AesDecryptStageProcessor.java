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
import static pl.softcredit.mpjk.engine.utils.JpkCrypt.decryptAES256;
import static pl.softcredit.mpjk.engine.utils.JpkUtils.getPathForAesDecryptStage;
import static pl.softcredit.mpjk.engine.utils.JpkUtils.getPathForAesEncryptStage;
import static pl.softcredit.mpjk.engine.utils.JpkUtils.getPathForKeyGeneratorStage;
import static pl.softcredit.mpjk.engine.utils.JpkUtils.getPathForVectorGeneratorStage;

public class AesDecryptStageProcessor implements JpkProcessor {

    private static final Logger LOGGER = getLogger(AesDecryptStageProcessor.class);

    @Override
    public void process(JpkConfiguration config) throws JpkException {
        String decryptedFileOutputPath = getPathForAesDecryptStage(config);
        LOGGER.info("Generating AES decrypted file to: " + decryptedFileOutputPath);

        try {
            byte[] key = readAllBytes(get(getPathForKeyGeneratorStage(config)));
            byte[] vector = readAllBytes(get(getPathForVectorGeneratorStage(config)));
            byte[] encryptedFileBytes = readAllBytes(get(getPathForAesEncryptStage(config)));
            byte[] decryptedBytes = decryptAES256(key, vector, encryptedFileBytes);

            writeByteArrayToFile(new File(decryptedFileOutputPath), decryptedBytes);
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
