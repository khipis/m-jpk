package pl.softcredit.mpjk.engine.processors.stage;

import org.slf4j.Logger;

import pl.softcredit.mpjk.JpkException;
import pl.softcredit.mpjk.core.configuration.JpkConfiguration;
import pl.softcredit.mpjk.engine.processors.JpkProcessor;

import java.io.File;
import java.io.IOException;

import static org.apache.commons.io.FileUtils.writeStringToFile;
import static org.slf4j.LoggerFactory.getLogger;
import static pl.softcredit.mpjk.engine.utils.JpkUtils.getPathForKeyRsaEncryptStage;

public class RsaEncryptStageProcessor implements JpkProcessor {

    private static final Logger LOGGER = getLogger(RsaEncryptStageProcessor.class);

    @Override
    public void process(JpkConfiguration config) throws JpkException {
        String keyRsaFileOutputPath = getPathForKeyRsaEncryptStage(config);
        LOGGER.info("Encrypt Key RSA to: " + keyRsaFileOutputPath);

        try {
            writeStringToFile(new File(keyRsaFileOutputPath), "key");
        } catch (IOException e) {
            LOGGER.error("Problem while saving RSA encrypted key to file");
            throw new JpkException(e);
        }
    }
}
