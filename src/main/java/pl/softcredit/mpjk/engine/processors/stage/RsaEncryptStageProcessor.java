package pl.softcredit.mpjk.engine.processors.stage;

import org.slf4j.Logger;

import pl.softcredit.mpjk.JpkException;
import pl.softcredit.mpjk.core.configuration.JpkConfiguration;
import pl.softcredit.mpjk.engine.processors.JpkProcessor;

import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import static java.nio.file.Files.readAllBytes;
import static java.nio.file.Paths.get;
import static org.apache.commons.io.FileUtils.writeStringToFile;
import static org.slf4j.LoggerFactory.getLogger;
import static pl.softcredit.mpjk.engine.utils.JpkCrypt.encodeBase64;
import static pl.softcredit.mpjk.engine.utils.JpkCrypt.encryptRsa;
import static pl.softcredit.mpjk.engine.utils.JpkUtils.getPathForKeyGeneratorStage;
import static pl.softcredit.mpjk.engine.utils.JpkUtils.getPathForKeyRsaEncryptStage;

public class RsaEncryptStageProcessor implements JpkProcessor {

    private static final Logger LOGGER = getLogger(RsaEncryptStageProcessor.class);

    @Override
    public void process(JpkConfiguration config) throws JpkException {
        String keyRsaFileOutputPath = getPathForKeyRsaEncryptStage(config);
        LOGGER.info("Encrypt Key RSA to: " + keyRsaFileOutputPath);
        LOGGER.info("Loading certificate file from: " + config.getRsaKeyPath());
        try {
            byte[] key = readAllBytes(get(getPathForKeyGeneratorStage(config)));
            byte[] rsa = encryptRsa(config.getRsaKeyPath(), key);
            writeStringToFile(new File(keyRsaFileOutputPath), encodeBase64(rsa));
        } catch (IOException e) {
            LOGGER.error("Problem while saving RSA encrypted key to file");
            throw new JpkException(e);
        } catch (CertificateException e) {
            LOGGER.error("Probably invalid certificate");
            throw new JpkException(e);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            LOGGER.error("Problem with AES algorithm (probably no JCE in JRE)");
            throw new JpkException(e);
        } catch (InvalidKeyException e) {
            LOGGER.error("Problem with generated key");
            throw new JpkException(e);
        } catch (BadPaddingException | IllegalBlockSizeException e) {
            LOGGER.error("Problem with RSA encrypt configuration");
            throw new JpkException(e);
        }
    }
}
