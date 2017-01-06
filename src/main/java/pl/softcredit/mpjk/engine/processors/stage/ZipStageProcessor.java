package pl.softcredit.mpjk.engine.processors.stage;

import org.slf4j.Logger;

import pl.softcredit.mpjk.JpkException;
import pl.softcredit.mpjk.core.configuration.JpkConfiguration;
import pl.softcredit.mpjk.engine.processors.JpkProcessor;

import java.io.IOException;

import static org.slf4j.LoggerFactory.getLogger;
import static pl.softcredit.mpjk.engine.utils.JpkUtils.getPathForZipStage;
import static pl.softcredit.mpjk.engine.utils.JpkZip.zipFile;

public class ZipStageProcessor implements JpkProcessor {

    private static final Logger LOGGER = getLogger(ZipStageProcessor.class);

    @Override
    public void process(JpkConfiguration config) throws JpkException {

        String zipFileOutputPath = getPathForZipStage(config);
        LOGGER.info("Zipping file to: " + zipFileOutputPath);

        try {
            zipFile(config.getInputFilePath(), zipFileOutputPath);
        } catch (IOException e) {
            LOGGER.error("Problem while zipping file");
            throw new JpkException(e);
        }
    }

}
