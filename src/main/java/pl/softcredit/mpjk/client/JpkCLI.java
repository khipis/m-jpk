package pl.softcredit.mpjk.client;

import org.slf4j.Logger;

import pl.softcredit.mpjk.JpkException;
import pl.softcredit.mpjk.core.configuration.JpkConfiguration;
import pl.softcredit.mpjk.engine.JpkExecutor;
import pl.softcredit.mpjk.engine.processors.JpkProcessor;

import static org.slf4j.LoggerFactory.getLogger;
import static pl.softcredit.mpjk.core.configuration.ConfigurationLoader.load;
import static pl.softcredit.mpjk.engine.processors.JpkProcessors.getProcessingFlow;

public class JpkCLI {

    private static final Logger LOGGER = getLogger(JpkCLI.class);

    private JpkCLI() {
    }

    public static void main(String[] args) {
        try {
            JpkConfiguration config = load(args);
            JpkProcessor[] processingFlow = getProcessingFlow(config.getProcessingFlow());
            new JpkExecutor(config).execute(processingFlow);
        } catch (JpkException e) {
            LOGGER.error("Unexpected error occurs: ", e);
        }
    }
}
