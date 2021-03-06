package pl.softcredit.mpjk.engine.processors.preparation;

import org.slf4j.Logger;

import pl.softcredit.mpjk.JpkException;
import pl.softcredit.mpjk.core.configuration.JpkConfiguration;
import pl.softcredit.mpjk.engine.processors.JpkProcessor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import static java.nio.file.Files.createDirectories;
import static org.apache.commons.io.FileUtils.cleanDirectory;
import static org.slf4j.LoggerFactory.getLogger;

public class CleanWorkingDirectoryProcessor implements JpkProcessor {

    private static final Logger LOGGER = getLogger(CleanWorkingDirectoryProcessor.class);

    @Override
    public void process(JpkConfiguration config) throws JpkException {
        String workingDirectoryPath = config.getWorkingDirectoryPath();
        File workingDirectory = new File(workingDirectoryPath);
        try {

            if(workingDirectory.exists()) {
                LOGGER.info("Working directory exists");
                LOGGER.info("Cleaning working directory path");
                cleanDirectory(workingDirectory);
                LOGGER.info("Cleaned: " + workingDirectoryPath);
            }
            else{
                LOGGER.info("Working directory not exists");
                LOGGER.info("Creating working directory");
                createDirectories(Paths.get(workingDirectoryPath));
                LOGGER.info("Created: " + workingDirectoryPath);
            }
        } catch (IOException e) {
            LOGGER.error("Problem while cleaning working directory: " + config.getWorkingDirectoryPath());
            throw new JpkException(e);
        }
    }
}
