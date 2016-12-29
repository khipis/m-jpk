package pl.softcredit.mpjk.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.softcredit.mpjk.core.JpkException;
import pl.softcredit.mpjk.core.configuration.ConfigurationService;
import pl.softcredit.mpjk.engine.JpkProcessor;

import static pl.softcredit.mpjk.engine.JpkProcessors.FORMAL_VALIDATION_PROCESSOR;


public class JpkCLI {

    private static final Logger LOGGER = LoggerFactory.getLogger(JpkCLI.class);
    private static final ConfigurationService configurationService =
            new ConfigurationService("G:\\work\\m-jpk\\src\\main\\resources\\config.properties");

    private JpkCLI(JpkProcessor... processors) throws JpkException {
        for (JpkProcessor processor : processors) {
            processor.process(configurationService);
        }
    }

    public static void main(String[] args) {
       try {
            new JpkCLI(FORMAL_VALIDATION_PROCESSOR);
        } catch (JpkException e) {
            LOGGER.error("Cannot perform validation: " + e.getMessage());
        }
    }

}
