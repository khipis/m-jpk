package pl.softcredit.mpjk.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.softcredit.mpjk.JpkException;
import pl.softcredit.mpjk.core.configuration.JpkConfiguration;
import pl.softcredit.mpjk.engine.JpkExecutor;

import static pl.softcredit.mpjk.core.configuration.ConfigurationLoader.load;
import static pl.softcredit.mpjk.engine.processors.JpkProcessors.AES_ENCRYPT_STAGE_PROCESSOR;
import static pl.softcredit.mpjk.engine.processors.JpkProcessors.CLEAN_WORKING_DIRECTORY_PROCESSOR;
import static pl.softcredit.mpjk.engine.processors.JpkProcessors.CONFIG_PARAMETERS_VALIDATION_PROCESSOR;
import static pl.softcredit.mpjk.engine.processors.JpkProcessors.FORMAL_VALIDATION_PROCESSOR;
import static pl.softcredit.mpjk.engine.processors.JpkProcessors.KEY_GENERATOR_STAGE_PROCESSOR;
import static pl.softcredit.mpjk.engine.processors.JpkProcessors.SCHEME_VALIDATION_PROCESSOR;
import static pl.softcredit.mpjk.engine.processors.JpkProcessors.VECTOR_GENERATOR_STAGE_PROCESSOR;
import static pl.softcredit.mpjk.engine.processors.JpkProcessors.ZIP_STAGE_PROCESSOR;

public class JpkCLI {

    private static final Logger LOGGER = LoggerFactory.getLogger(JpkCLI.class);

    private JpkCLI() {
    }

    public static void main(String[] args) {

        try {
            JpkConfiguration config = load(args);

            new JpkExecutor(config)
                    .execute(
                            CONFIG_PARAMETERS_VALIDATION_PROCESSOR,
                            SCHEME_VALIDATION_PROCESSOR,
                            CLEAN_WORKING_DIRECTORY_PROCESSOR,
                            FORMAL_VALIDATION_PROCESSOR,
                            KEY_GENERATOR_STAGE_PROCESSOR,
                            VECTOR_GENERATOR_STAGE_PROCESSOR,
                            ZIP_STAGE_PROCESSOR,
                            AES_ENCRYPT_STAGE_PROCESSOR
                    );
        } catch (JpkException e) {
            LOGGER.error("Unexpected error occurs: ", e);
        }
    }
}
