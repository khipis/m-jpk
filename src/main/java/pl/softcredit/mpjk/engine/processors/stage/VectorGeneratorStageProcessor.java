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
import static pl.softcredit.mpjk.engine.utils.JpkUtils.getPathForVectorGeneratorStage;

public class VectorGeneratorStageProcessor implements JpkProcessor {

    private static final Logger LOGGER = getLogger(VectorGeneratorStageProcessor.class);
    private static final int BITS_COUNT = 128;

    @Override
    public void process(JpkConfiguration config) throws JpkException {

        String vectorFileOutputPath = getPathForVectorGeneratorStage(config);
        LOGGER.info("Generating client vector to: " + vectorFileOutputPath);

        try {
            KeyGenerator keyGen = getInstance("AES");
            keyGen.init(BITS_COUNT);

            SecretKey secretKey = keyGen.generateKey();
            String generatedKey = new String(secretKey.getEncoded());
            generatedKey = "1234567890123456";

            writeStringToFile(new File(vectorFileOutputPath), generatedKey);

        } catch (NoSuchAlgorithmException e) {
            LOGGER.error("Problem while generating AES client vector.");
            throw new JpkException(e);
        } catch (IOException e) {
            LOGGER.error("Problem while saving client vector to file.");
            throw new JpkException(e);
        }
    }
}
