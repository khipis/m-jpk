package pl.softcredit.mpjk.engine;

import pl.softcredit.mpjk.engine.processors.preparation.CleanWorkingDirectoryProcessor;
import pl.softcredit.mpjk.engine.processors.preparation.DeleteLogFilesProcessor;
import pl.softcredit.mpjk.engine.processors.validation.FormalValidationProcessor;

public final class JpkProcessors {

    public static final JpkProcessor CLEAN_WORKING_DIRECTORY_PROCESSOR = new CleanWorkingDirectoryProcessor();
    public static final JpkProcessor DELETE_LOG_FILES = new DeleteLogFilesProcessor();
    public static final JpkProcessor FORMAL_VALIDATION_PROCESSOR = new FormalValidationProcessor();

    private JpkProcessors() {
    }

}
