package pl.softcredit.mpjk.engine.processors;

import pl.softcredit.mpjk.engine.processors.preparation.CleanWorkingDirectoryProcessor;
import pl.softcredit.mpjk.engine.processors.validation.FormalValidationProcessor;

public final class JpkProcessors {

    public static final JpkProcessor CLEAN_WORKING_DIRECTORY_PROCESSOR = new CleanWorkingDirectoryProcessor();
    public static final JpkProcessor FORMAL_VALIDATION_PROCESSOR = new FormalValidationProcessor();

    private JpkProcessors() {
    }

}
