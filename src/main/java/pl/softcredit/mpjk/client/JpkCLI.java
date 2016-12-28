package pl.softcredit.mpjk.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.softcredit.mpjk.core.JpkException;
import pl.softcredit.mpjk.core.configuration.ConfigurationService;
import pl.softcredit.mpjk.engine.JpkProcessor;


public class JpkCLI {

    private static final Logger LOGGER = LoggerFactory.getLogger(JpkCLI.class);
    private static final ConfigurationService configurationService =
            new ConfigurationService("G:\\work\\m-jpk\\src\\main\\resources\\config.properties");

    public JpkCLI(JpkProcessor... processors) throws JpkException {

        for (JpkProcessor processor : processors) {

            processor.process("", "");
        }
    }


    public static void main(String[] args) {

        LOGGER.info(configurationService.getWorkingDirectoryPath());
        LOGGER.info(configurationService.getSchemeDirectoryPath());

/*        try {
            JpkCLI auditPerformer = new JpkCLI(new FormalValidationProcessor());
        } catch (JpkException e) {
            log.error("Cannot perform audit: " + e.getMessage());
        }*/
    }

}
