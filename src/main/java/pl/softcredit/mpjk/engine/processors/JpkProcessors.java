package pl.softcredit.mpjk.engine.processors;

import com.google.inject.internal.ImmutableMap;

import pl.softcredit.mpjk.JpkException;
import pl.softcredit.mpjk.engine.processors.preparation.CleanWorkingDirectoryProcessor;
import pl.softcredit.mpjk.engine.processors.stage.AesDecryptStageProcessor;
import pl.softcredit.mpjk.engine.processors.stage.AesEncryptStageProcessor;
import pl.softcredit.mpjk.engine.processors.stage.KeyGeneratorStageProcessor;
import pl.softcredit.mpjk.engine.processors.stage.RsaEncryptStageProcessor;
import pl.softcredit.mpjk.engine.processors.stage.ShaGeneratorStageProcessor;
import pl.softcredit.mpjk.engine.processors.stage.VectorGeneratorStageProcessor;
import pl.softcredit.mpjk.engine.processors.stage.ZipStageProcessor;
import pl.softcredit.mpjk.engine.processors.validation.ConfigParametersValidationProcessor;
import pl.softcredit.mpjk.engine.processors.validation.FormalValidationProcessor;
import pl.softcredit.mpjk.engine.processors.validation.SchemeValidationProcessor;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public final class JpkProcessors {

    public static final JpkProcessor CONFIG_PARAMETERS_VALIDATION_PROCESSOR =
            new ConfigParametersValidationProcessor();

    public static final JpkProcessor SCHEME_VALIDATION_PROCESSOR = new SchemeValidationProcessor();
    public static final JpkProcessor CLEAN_WORKING_DIRECTORY_PROCESSOR =
            new CleanWorkingDirectoryProcessor();
    public static final JpkProcessor FORMAL_VALIDATION_PROCESSOR = new FormalValidationProcessor();
    public static final JpkProcessor SHA256_GENERATOR_STAGE_PROCESSOR = new ShaGeneratorStageProcessor();
    public static final JpkProcessor KEY_GENERATOR_STAGE_PROCESSOR =
            new KeyGeneratorStageProcessor();
    public static final JpkProcessor VECTOR_GENERATOR_STAGE_PROCESSOR =
            new VectorGeneratorStageProcessor();
    public static final JpkProcessor ZIP_STAGE_PROCESSOR = new ZipStageProcessor();
    public static final JpkProcessor AES_ENCRYPT_STAGE_PROCESSOR = new AesEncryptStageProcessor();
    public static final JpkProcessor AES_DECRYPT_STAGE_PROCESSOR = new AesDecryptStageProcessor();
    public static final JpkProcessor RSA_ENCRYPT_STAGE_PROCESSOR = new RsaEncryptStageProcessor();

    private static final Map<String, JpkProcessor> PROCESSORS_MAP =
            ImmutableMap.<String, JpkProcessor>builder().
                    put("CONFIG_VALIDATION", CONFIG_PARAMETERS_VALIDATION_PROCESSOR).
                    put("SCHEME_VALIDATION", SCHEME_VALIDATION_PROCESSOR).
                    put("CLEAN_WORKING_DIRECTORY", CLEAN_WORKING_DIRECTORY_PROCESSOR).
                    put("FORMAL_VALIDATION", FORMAL_VALIDATION_PROCESSOR).
                    put("SHA256_GENERATOR", SHA256_GENERATOR_STAGE_PROCESSOR).
                    put("KEY_GENERATOR", KEY_GENERATOR_STAGE_PROCESSOR).
                    put("VECTOR_GENERATOR", VECTOR_GENERATOR_STAGE_PROCESSOR).
                    put("ZIP", ZIP_STAGE_PROCESSOR).
                    put("AES_ENCRYPT", AES_ENCRYPT_STAGE_PROCESSOR).
                    put("AES_DECRYPT", AES_DECRYPT_STAGE_PROCESSOR).
                    put("RSA_ENCRYPT", RSA_ENCRYPT_STAGE_PROCESSOR).
                    build();

    static final JpkProcessor getProcessorByString(String processorName)
            throws JpkException {
        String processorNameUpperCase = processorName.toUpperCase();
        if (PROCESSORS_MAP.containsKey(processorNameUpperCase)) {
            return PROCESSORS_MAP.get(processorNameUpperCase);
        } else {
            throw new JpkException("There is no processor with name: " + processorNameUpperCase);
        }
    }

    public static final JpkProcessor[] getProcessingFlow(String commaSeparatedProcessingFlow)
            throws JpkException {
        String[] processorsNames = commaSeparatedProcessingFlow.split(",");
        List<JpkProcessor> jpkProcessorsFlow = new LinkedList<>();
        for (String processorName : processorsNames) {
            jpkProcessorsFlow.add(getProcessorByString(processorName));
        }
        return jpkProcessorsFlow.toArray(new JpkProcessor[jpkProcessorsFlow.size()]);
    }

    private JpkProcessors() {
    }

}
