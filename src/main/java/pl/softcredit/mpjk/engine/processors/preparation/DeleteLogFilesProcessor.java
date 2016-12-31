package pl.softcredit.mpjk.engine.processors.preparation;

import org.slf4j.Logger;

import pl.softcredit.mpjk.core.configuration.ConfigurationService;
import pl.softcredit.mpjk.engine.JpkProcessor;

import java.io.File;
import java.io.IOException;

import static org.apache.commons.io.FileUtils.forceDelete;
import static org.slf4j.LoggerFactory.getLogger;

public class DeleteLogFilesProcessor implements JpkProcessor {

    private static final Logger LOGGER = getLogger(DeleteLogFilesProcessor.class);

    public void process(ConfigurationService configurationService) {
        LOGGER.info("Delete mjpk.log file");
        try {
            forceDelete(new File("mjpk.log"));
        } catch (IOException e) {
            LOGGER.error("Problem with deletion of file: mjpk.log", e);
        }
    }
}
