package pl.softcredit.mpjk.engine.processors.validation;

import org.slf4j.Logger;

import pl.softcredit.mpjk.JpkException;
import pl.softcredit.mpjk.core.configuration.JpkConfiguration;
import pl.softcredit.mpjk.engine.processors.JpkProcessor;

import java.io.File;
import java.io.IOException;

import static org.apache.commons.io.FileUtils.writeStringToFile;
import static org.slf4j.LoggerFactory.getLogger;
import static pl.softcredit.mpjk.engine.utils.JpkUtils.VALID_CONTENT;
import static pl.softcredit.mpjk.engine.utils.JpkUtils.getPathForFormalValidation;
import static pl.softcredit.mpjk.engine.utils.JpkUtils.validateXmlFileAgainstScheme;

public class FormalValidationProcessor implements JpkProcessor {

    private static final Logger LOGGER = getLogger(FormalValidationProcessor.class);

    @Override
    public void process(JpkConfiguration config) throws JpkException {
        String formalValidationFileOutputPath = getPathForFormalValidation(config);
        LOGGER.info("Input file: " + config.getInputFilePath());
        LOGGER.info("Used scheme: " + config.getSchemeFilePath());
        LOGGER.info("Generating formal validation file to: " + formalValidationFileOutputPath);

        try {

            String result = validateXmlFileAgainstScheme(config.getInputFilePath(),
                                                         config.getSchemeFilePath());

            writeStringToFile(new File(formalValidationFileOutputPath), result);

            if (!VALID_CONTENT.equals(result)) {
                throw new JpkException(
                        config.getInputFilePath() + " is invalid to scheme : " + config
                                .getSchemeFilePath());
            }

            writeStringToFile(new File(formalValidationFileOutputPath), VALID_CONTENT);
        } catch (IOException e) {
            LOGGER.error("Problem while validation file against scheme");
            throw new JpkException(e);
        }
    }
}
