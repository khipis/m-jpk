package pl.softcredit.mpjk.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.softcredit.mpjk.core.configuration.ConfigurationLoader;
import pl.softcredit.mpjk.core.configuration.JpkConfiguration;
import pl.softcredit.mpjk.engine.executor.JpkExecutor;

import static pl.softcredit.mpjk.engine.processors.JpkProcessors.CLEAN_WORKING_DIRECTORY_PROCESSOR;
import static pl.softcredit.mpjk.engine.processors.JpkProcessors.FORMAL_VALIDATION_PROCESSOR;

public class JpkCLI {

    private static final Logger LOGGER = LoggerFactory.getLogger(JpkCLI.class);
    private static JpkConfiguration config;

    public static void main(String[] args) {

        config = ConfigurationLoader.load(args);

        new JpkExecutor(config).execute(
                CLEAN_WORKING_DIRECTORY_PROCESSOR,
                FORMAL_VALIDATION_PROCESSOR
        );

    }

}
