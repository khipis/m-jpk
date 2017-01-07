package pl.softcredit.mpjk.engine.processors.stage;

import org.slf4j.Logger;

import pl.softcredit.mpjk.JpkException;
import pl.softcredit.mpjk.core.configuration.JpkConfiguration;
import pl.softcredit.mpjk.engine.processors.JpkProcessor;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import static org.apache.commons.io.FileUtils.writeByteArrayToFile;
import static org.slf4j.LoggerFactory.getLogger;
import static pl.softcredit.mpjk.engine.utils.JpkCrypt.generateKeyBits;
import static pl.softcredit.mpjk.engine.utils.JpkUtils.getPathForVectorGeneratorStage;

public class VectorGeneratorStageProcessor implements JpkProcessor {

    private static final Logger LOGGER = getLogger(VectorGeneratorStageProcessor.class);
    private static final int BITS_COUNT = 128;

    @Override
    public void process(JpkConfiguration config) throws JpkException {
        String vectorFileOutputPath = getPathForVectorGeneratorStage(config);
        LOGGER.info("Generating client vector to: " + vectorFileOutputPath);
        try {
            writeByteArrayToFile(new File(vectorFileOutputPath), generateKeyBits(BITS_COUNT));
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error("Problem while generating AES client vector.");
            throw new JpkException(e);
        } catch (IOException e) {
            LOGGER.error("Problem while saving client vector to file.");
            throw new JpkException(e);
        }
    }
}
