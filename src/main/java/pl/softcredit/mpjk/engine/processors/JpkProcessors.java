package pl.softcredit.mpjk.engine.processors;

import pl.softcredit.mpjk.engine.processors.preparation.CleanWorkingDirectoryProcessor;
import pl.softcredit.mpjk.engine.processors.stage.ZipStageProcessor;
import pl.softcredit.mpjk.engine.processors.validation.ConfigParametersValidationProcessor;
import pl.softcredit.mpjk.engine.processors.validation.FormalValidationProcessor;
import pl.softcredit.mpjk.engine.processors.validation.SchemeValidationProcessor;

public final class JpkProcessors {

    public static final JpkProcessor CONFIG_PARAMETERS_VALIDATION_PROCESSOR = new ConfigParametersValidationProcessor();
    public static final JpkProcessor SCHEME_VALIDATION_PROCESSOR = new SchemeValidationProcessor();
    public static final JpkProcessor CLEAN_WORKING_DIRECTORY_PROCESSOR = new CleanWorkingDirectoryProcessor();
    public static final JpkProcessor FORMAL_VALIDATION_PROCESSOR = new FormalValidationProcessor();
    public static final JpkProcessor ZIP_STAGE_PROCESSOR = new ZipStageProcessor();

    private JpkProcessors() {
    }

}
