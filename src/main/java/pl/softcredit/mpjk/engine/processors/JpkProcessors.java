package pl.softcredit.mpjk.engine.processors;

import pl.softcredit.mpjk.engine.processors.preparation.CleanWorkingDirectoryProcessor;
import pl.softcredit.mpjk.engine.processors.stage.AesDecryptStageProcessor;
import pl.softcredit.mpjk.engine.processors.stage.AesEncryptStageProcessor;
import pl.softcredit.mpjk.engine.processors.stage.KeyGeneratorStageProcessor;
import pl.softcredit.mpjk.engine.processors.stage.VectorGeneratorStageProcessor;
import pl.softcredit.mpjk.engine.processors.stage.ZipStageProcessor;
import pl.softcredit.mpjk.engine.processors.validation.ConfigParametersValidationProcessor;
import pl.softcredit.mpjk.engine.processors.validation.FormalValidationProcessor;
import pl.softcredit.mpjk.engine.processors.validation.SchemeValidationProcessor;

public final class JpkProcessors {

    public static final JpkProcessor CONFIG_PARAMETERS_VALIDATION_PROCESSOR = new ConfigParametersValidationProcessor();
    public static final JpkProcessor SCHEME_VALIDATION_PROCESSOR = new SchemeValidationProcessor();
    public static final JpkProcessor CLEAN_WORKING_DIRECTORY_PROCESSOR = new CleanWorkingDirectoryProcessor();
    public static final JpkProcessor FORMAL_VALIDATION_PROCESSOR = new FormalValidationProcessor();
    public static final JpkProcessor KEY_GENERATOR_STAGE_PROCESSOR = new KeyGeneratorStageProcessor();
    public static final JpkProcessor VECTOR_GENERATOR_STAGE_PROCESSOR = new VectorGeneratorStageProcessor();
    public static final JpkProcessor ZIP_STAGE_PROCESSOR = new ZipStageProcessor();
    public static final JpkProcessor AES_ENCRYPT_STAGE_PROCESSOR = new AesEncryptStageProcessor();
    public static final JpkProcessor AES_DECRYPT_STAGE_PROCESSOR = new AesDecryptStageProcessor();

    private JpkProcessors() {
    }

}
