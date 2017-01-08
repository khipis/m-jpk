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
import static pl.softcredit.mpjk.engine.TestPaths.INPUT_FILES_DIR_PATH_IN_TEST_RESOURCES;
import static pl.softcredit.mpjk.engine.TestPaths.JPK_VAT_SCHEME_FILE_NAME_VERSION_2;
import static pl.softcredit.mpjk.engine.TestPaths.RSA_KEY_FILE_PATH_IN_RESOURCES_VERSION_2;
import static pl.softcredit.mpjk.engine.TestPaths.SCHEMES_DIR_PATH_IN_TEST_RESOURCES;
import static pl.softcredit.mpjk.engine.TestPaths.WORKING_DIR_PATH_IN_TARGET;
import static pl.softcredit.mpjk.engine.TestPaths.XML_FILE_NAME_SCHEME_VERSION_2;
import static pl.softcredit.mpjk.engine.TestPaths.XML_FILE_PATH_IN_WORKING_DIR_VERSION_2;
import static pl.softcredit.mpjk.engine.processors.JpkProcessors.getProcessingFlow;
import static pl.softcredit.mpjk.engine.utils.JpkUtils.getPathForZipStage;
import static pl.softcredit.mpjk.engine.utils.JpkZip.unzipFile;

@RunWith(MockitoJUnitRunner.class)
public class JpkExecutorForVersion2Test {

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
        cleanDirectory(new File(WORKING_DIR_PATH_IN_TARGET));
        whenConfigurationWith(XML_FILE_NAME_SCHEME_VERSION_2, JPK_VAT_SCHEME_FILE_NAME_VERSION_2);
    }

    @Test
    public void shouldExecuteWholeFlowOfJpkProcessing() throws JpkException, IOException {
        JpkProcessor[] processingFlow = getProcessingFlow(new DefaultJpkConfiguration().getProcessingFlow());

        new JpkExecutor(config).execute(processingFlow);

        String fileContent = readFileToString(new File(config.getInputFilePath()));

        assertUnzippedContent(fileContent);
    }

    private void assertUnzippedContent(String fileContent) throws JpkException, IOException {
        unzipFile(getPathForZipStage(config), WORKING_DIR_PATH_IN_TARGET);

        String unzippedFileContent = readFileToString(new File(
                XML_FILE_PATH_IN_WORKING_DIR_VERSION_2));

        assertThat(fileContent).isEqualTo(unzippedFileContent);
    }

    private void whenConfigurationWith(String inputFile, String schemeFile) {
        when(config.getWorkingDirectoryPath()).thenReturn(WORKING_DIR_PATH_IN_TARGET);
        when(config.getSchemeFilePath()).thenReturn(SCHEMES_DIR_PATH_IN_TEST_RESOURCES + schemeFile);
        when(config.getInputFilePath()).thenReturn(INPUT_FILES_DIR_PATH_IN_TEST_RESOURCES + inputFile);
        when(config.getRsaKeyPath()).thenReturn(RSA_KEY_FILE_PATH_IN_RESOURCES_VERSION_2);
    }

}