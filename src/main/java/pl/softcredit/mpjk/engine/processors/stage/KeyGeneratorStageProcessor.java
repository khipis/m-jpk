package pl.softcredit.mpjk.engine.processors.stage;

import org.slf4j.Logger;

import pl.softcredit.mpjk.JpkException;
import pl.softcredit.mpjk.core.configuration.JpkConfiguration;
import pl.softcredit.mpjk.engine.processors.JpkProcessor;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import static javax.crypto.KeyGenerator.getInstance;
import static org.apache.commons.io.FileUtils.writeStringToFile;
import static org.slf4j.LoggerFactory.getLogger;
import static pl.softcredit.mpjk.engine.utils.JpkOutputUtils.getOutputPathForKeyGeneratorStage;

public class KeyGeneratorStageProcessor implements JpkProcessor {

    private static final Logger LOGGER = getLogger(KeyGeneratorStageProcessor.class);
    private static final int BITS_COUNT = 256;

    @Override
    public void process(JpkConfiguration config) throws JpkException {

        String keyFileOutputPath = getOutputPathForKeyGeneratorStage(config);
        LOGGER.info("Generating client key to: " + keyFileOutputPath);

        try {
            KeyGenerator keyGen = getInstance("AES");
            keyGen.init(BITS_COUNT);

            SecretKey secretKey = keyGen.generateKey();
            String generatedKey = new String(secretKey.getEncoded());
           // generatedKey = "12345678901234567890123456789012";

            writeStringToFile(new File(keyFileOutputPath), generatedKey);

        } catch (NoSuchAlgorithmException e) {
            LOGGER.error("Problem while generating AES client key.");
            throw new JpkException(e);
        } catch (IOException e) {
            LOGGER.error("Problem while saving client key.");
            throw new JpkException(e);
        }
    }
}
