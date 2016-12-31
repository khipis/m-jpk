package pl.softcredit.mpjk.engine.processors.preparation;

import org.slf4j.Logger;

import pl.softcredit.mpjk.core.configuration.ConfigurationService;
import pl.softcredit.mpjk.engine.JpkProcessor;

import java.io.File;
import java.io.IOException;

import static org.apache.commons.io.FileUtils.cleanDirectory;
import static org.slf4j.LoggerFactory.getLogger;

public class CleanWorkingDirectoryProcessor implements JpkProcessor {

    private static final Logger LOGGER = getLogger(CleanWorkingDirectoryProcessor.class);

    public void process(ConfigurationService configurationService) {
        LOGGER.info("Cleaning working directory path: " + configurationService.getWorkingDirectoryPath());
        File workingDirectoryPath = new File(configurationService.getWorkingDirectoryPath());
        try {
            cleanDirectory(workingDirectoryPath);
        } catch (IOException e) {
            LOGGER.error("Problem while cleaning working directory: " + configurationService.getWorkingDirectoryPath(), e);
        }
    }
}
