package pl.softcredit.mpjk.engine.processors.validation;

import org.slf4j.Logger;

import pl.softcredit.mpjk.JpkException;
import pl.softcredit.mpjk.core.configuration.JpkConfiguration;
import pl.softcredit.mpjk.engine.processors.JpkProcessor;

import java.nio.file.Paths;

import static java.nio.file.Files.notExists;
import static org.slf4j.LoggerFactory.getLogger;

public class ConfigParametersValidationProcessor implements JpkProcessor {

    private static final Logger LOGGER = getLogger(ConfigParametersValidationProcessor.class);

    @Override
    public void process(JpkConfiguration config) throws JpkException {
        LOGGER.info("Performing config input validation.");
        if (notExists(Paths.get(config.getInputFilePath()))) {
            throw new JpkException("Input file not exists: " + config.getInputFilePath());
        }
        if (notExists(Paths.get(config.getSchemeFilePath()))) {
            throw new JpkException("Scheme file not exists: " + config.getSchemeFilePath());
        }
    }
}
