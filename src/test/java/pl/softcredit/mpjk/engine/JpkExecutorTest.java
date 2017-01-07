package pl.softcredit.mpjk.engine;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import pl.softcredit.mpjk.JpkException;
import pl.softcredit.mpjk.core.configuration.DefaultJpkConfiguration;
import pl.softcredit.mpjk.core.configuration.JpkConfiguration;
import pl.softcredit.mpjk.engine.processors.JpkProcessor;
import pl.softcredit.mpjk.engine.processors.preparation.CleanWorkingDirectoryProcessor;
import pl.softcredit.mpjk.engine.processors.validation.ConfigParametersValidationProcessor;
import pl.softcredit.mpjk.engine.processors.validation.FormalValidationProcessor;
import pl.softcredit.mpjk.engine.processors.validation.SchemeValidationProcessor;

import java.io.File;
import java.io.IOException;

import static org.apache.commons.io.FileUtils.cleanDirectory;
import static org.apache.commons.io.FileUtils.readFileToString;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static pl.softcredit.mpjk.engine.TestPaths.JPK_VAT_SCHEME_FILE;
import static pl.softcredit.mpjk.engine.TestPaths.RESOURCES_INPUT_FILES;
import static pl.softcredit.mpjk.engine.TestPaths.SCHEMES_DIR;
import static pl.softcredit.mpjk.engine.TestPaths.TEMP_WORKING_DIR;
import static pl.softcredit.mpjk.engine.TestPaths.VALIDATION_FILE_NAME;
import static pl.softcredit.mpjk.engine.TestPaths.VALID_FILE_NAME;
import static pl.softcredit.mpjk.engine.TestPaths.VALID_FILE_PATH;
import static pl.softcredit.mpjk.engine.processors.JpkProcessors.getProcessingFlow;
import static pl.softcredit.mpjk.engine.utils.JpkUtils.getPathForZipStage;
import static pl.softcredit.mpjk.engine.utils.JpkZip.unzipFile;

@RunWith(MockitoJUnitRunner.class)
public class JpkExecutorTest {

    @Mock
    private ConfigParametersValidationProcessor configParametersValidationProcessor;
    @Mock
    private CleanWorkingDirectoryProcessor cleanWorkingDirectoryProcessor;
    @Mock
    private FormalValidationProcessor formalValidationProcessor;
    @Mock
    private SchemeValidationProcessor schemeValidationProcessor;

    @Mock
    private JpkConfiguration config;

    @Before
    public void setUp() throws IOException, JpkException {
        cleanDirectory(new File(TEMP_WORKING_DIR));
        whenConfigurationWith(VALID_FILE_NAME, JPK_VAT_SCHEME_FILE);
    }

    @Test
    public void shouldExecuteWholeFlowOfJpkProcessing() throws JpkException, IOException {
        JpkProcessor[] processingFlow = getProcessingFlow(new DefaultJpkConfiguration().getProcessingFlow());

        new JpkExecutor(config).execute(processingFlow);

        String fileContent = readFileToString(new File(config.getInputFilePath()));

        unzipFile(getPathForZipStage(config), TEMP_WORKING_DIR);

        String unzippedFileContent = readFileToString(new File(VALID_FILE_PATH));

        assertThat(fileContent).isEqualTo(unzippedFileContent);
    }

    @Test
    public void shouldPerformOnlyFormalValidation() throws JpkException, IOException {
        new JpkExecutor(config).execute(getProcessingFlow("FORMAL_VALIDATION"));

        File[] files = new File(TEMP_WORKING_DIR).listFiles();

        assertThat(files).hasSize(1);
        assertThat(files[0]).exists();
        assertThat(files[0].getName()).isEqualTo(VALIDATION_FILE_NAME);
    }

    private void whenConfigurationWith(String inputFile, String schemeFile) {
        when(config.getWorkingDirectoryPath()).thenReturn(TEMP_WORKING_DIR);
        when(config.getSchemeFilePath()).thenReturn(SCHEMES_DIR + schemeFile);
        when(config.getInputFilePath()).thenReturn(RESOURCES_INPUT_FILES + inputFile);
    }

}