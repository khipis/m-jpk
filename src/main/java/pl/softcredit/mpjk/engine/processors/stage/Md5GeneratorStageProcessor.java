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
import static pl.softcredit.mpjk.engine.utils.JpkCrypt.calculateMD5;
import static pl.softcredit.mpjk.engine.utils.JpkCrypt.encodeBase64;
import static pl.softcredit.mpjk.engine.utils.JpkUtils.getPathForMd5GeneratorStage;

public class Md5GeneratorStageProcessor implements JpkProcessor {

    private static final Logger LOGGER = getLogger(Md5GeneratorStageProcessor.class);

    @Override
    public void process(JpkConfiguration config) throws JpkException {

        String md5FileOutputPath = getPathForMd5GeneratorStage(config);
        LOGGER.info("Generating MD5 of file to: " + md5FileOutputPath);

        try {
            byte[] bytesToCalculateSha = readAllBytes(get(config.getInputFilePath()));
            byte[] md5bytes = calculateMD5(bytesToCalculateSha);

            writeStringToFile(new File(md5FileOutputPath), encodeBase64(md5bytes));

        } catch (NoSuchAlgorithmException e) {
            LOGGER.error("Problem while generating MD5");
            throw new JpkException(e);
        } catch (IOException e) {
            LOGGER.error("Problem while saving MD5 to file");
            throw new JpkException(e);
        }
    }
}
