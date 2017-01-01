package pl.softcredit.mpjk.client;

import pl.softcredit.mpjk.core.configuration.JpkConfiguration;
import pl.softcredit.mpjk.engine.JpkExecutor;

import static pl.softcredit.mpjk.core.configuration.ConfigurationLoader.load;
import static pl.softcredit.mpjk.engine.processors.JpkProcessors.CLEAN_WORKING_DIRECTORY_PROCESSOR;
import static pl.softcredit.mpjk.engine.processors.JpkProcessors.FORMAL_VALIDATION_PROCESSOR;

public class JpkCLI {

    private JpkCLI(){
    }

    public static void main(String[] args) {
        JpkConfiguration config = load(args);

        new JpkExecutor(config).execute(
                CLEAN_WORKING_DIRECTORY_PROCESSOR,
                FORMAL_VALIDATION_PROCESSOR
        );
    }
}
