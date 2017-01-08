package pl.softcredit.mpjk.engine.processors.stage;

import org.slf4j.Logger;

import pl.softcredit.mpjk.JpkException;
import pl.softcredit.mpjk.core.configuration.JpkConfiguration;
import pl.softcredit.mpjk.engine.processors.JpkProcessor;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import static java.nio.file.Files.readAllBytes;
import static java.nio.file.Paths.get;
import static org.apache.commons.io.FileUtils.writeStringToFile;
import static org.slf4j.LoggerFactory.getLogger;
import static pl.softcredit.mpjk.engine.utils.JpkCrypt.calculateSHA256;
import static pl.softcredit.mpjk.engine.utils.JpkCrypt.encodeBase64;
import static pl.softcredit.mpjk.engine.utils.JpkUtils.getPathForShaGeneratorStage;

public class ShaGeneratorStageProcessor implements JpkProcessor {

    private static final Logger LOGGER = getLogger(ShaGeneratorStageProcessor.class);

    @Override
    public void process(JpkConfiguration config) throws JpkException {

        String shaFileOutputPath = getPathForShaGeneratorStage(config);
        LOGGER.info("Generating SHA256 of file to: " + shaFileOutputPath);

        try {
            byte[] bytesToCalculateSha = readAllBytes(get(config.getInputFilePath()));
            byte[] sha256bytes = calculateSHA256(bytesToCalculateSha);

            writeStringToFile(new File(shaFileOutputPath), encodeBase64(sha256bytes));

        } catch (NoSuchAlgorithmException e) {
            LOGGER.error("Problem while generating SHA256");
            throw new JpkException(e);
        } catch (IOException e) {
            LOGGER.error("Problem while saving SHA256 to file");
            throw new JpkException(e);
        }
    }
}
