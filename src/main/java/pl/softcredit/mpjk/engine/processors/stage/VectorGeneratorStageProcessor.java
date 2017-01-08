package pl.softcredit.mpjk.engine.processors.stage;

import org.slf4j.Logger;

import pl.softcredit.mpjk.JpkException;
import pl.softcredit.mpjk.core.configuration.JpkConfiguration;
import pl.softcredit.mpjk.engine.processors.JpkProcessor;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import static org.apache.commons.io.FileUtils.writeByteArrayToFile;
import static org.apache.commons.io.FileUtils.writeStringToFile;
import static org.slf4j.LoggerFactory.getLogger;
import static pl.softcredit.mpjk.engine.utils.JpkCrypt.encodeBase64;
import static pl.softcredit.mpjk.engine.utils.JpkCrypt.generateKeyBits;
import static pl.softcredit.mpjk.engine.utils.JpkExtensions.BASE64_EXTENSION;
import static pl.softcredit.mpjk.engine.utils.JpkUtils.getPathForVectorGeneratorStage;

public class VectorGeneratorStageProcessor implements JpkProcessor {

    private static final Logger LOGGER = getLogger(VectorGeneratorStageProcessor.class);
    private static final int BITS_COUNT = 128;

    @Override
    public void process(JpkConfiguration config) throws JpkException {
        String vectorFileOutputPath = getPathForVectorGeneratorStage(config);
        String vectorFileOutputPathBase64 = vectorFileOutputPath + BASE64_EXTENSION;
        LOGGER.info("Generating raw vector to: " + vectorFileOutputPath);
        LOGGER.info("Generating base64 vector to: " + vectorFileOutputPathBase64);
        try {
            byte[] keyBits = generateKeyBits(BITS_COUNT);
            writeByteArrayToFile(new File(vectorFileOutputPath), generateKeyBits(BITS_COUNT));
            //writeByteArrayToFile(new File(vectorFileOutputPath), "1234567890123456".getBytes());
            writeStringToFile(new File(vectorFileOutputPathBase64), encodeBase64(keyBits));
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error("Problem while generating raw and base64 encoded vectors.");
            throw new JpkException(e);
        } catch (IOException e) {
            LOGGER.error("Problem while saving vectors to files.");
            throw new JpkException(e);
        }

    }
}
