package pl.softcredit.mpjk.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.softcredit.mpjk.core.configuration.ConfigurationService;
import pl.softcredit.mpjk.engine.executor.JpkExecutor;

import static pl.softcredit.mpjk.engine.processors.JpkProcessors.CLEAN_WORKING_DIRECTORY_PROCESSOR;
import static pl.softcredit.mpjk.engine.processors.JpkProcessors.FORMAL_VALIDATION_PROCESSOR;

public class JpkCLI {

    private static final Logger LOGGER = LoggerFactory.getLogger(JpkCLI.class);
    private static ConfigurationService config;

    public static void main(String[] args) {

        prepareConfiguration(args);

        new JpkExecutor(config).execute(
                CLEAN_WORKING_DIRECTORY_PROCESSOR,
                FORMAL_VALIDATION_PROCESSOR
        );

    }

    private static void prepareConfiguration(String[] args) {
        //TODO Change that to app root file
        String configFilePath = "G:\\work\\m-jpk\\src\\main\\resources\\config.properties";
        if (args != null && args.length >= 1) {
            configFilePath = args[0];
        }

        config = new ConfigurationService(configFilePath);
    }

}
