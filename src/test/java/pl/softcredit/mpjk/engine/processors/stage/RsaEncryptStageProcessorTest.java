package pl.softcredit.mpjk.engine.processors.stage;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import pl.softcredit.mpjk.JpkException;
import pl.softcredit.mpjk.core.configuration.JpkConfiguration;
import pl.softcredit.mpjk.engine.processors.JpkProcessor;

import java.io.File;
import java.io.IOException;

import static org.apache.commons.io.FileUtils.cleanDirectory;
import static org.apache.commons.io.FileUtils.copyDirectory;
import static org.assertj.core.util.Files.delete;
import static org.junit.rules.ExpectedException.none;
import static org.mockito.Mockito.when;
import static pl.softcredit.mpjk.engine.TestPaths.JPK_VAT_SCHEME_FILE_NAME_VERSION_1;
import static pl.softcredit.mpjk.engine.TestPaths.INPUT_FILES_DIR_PATH_IN_TEST_RESOURCES;
import static pl.softcredit.mpjk.engine.TestPaths.RSA_KEY_FILE_NAME_SCHEME_VERSION_1;
import static pl.softcredit.mpjk.engine.TestPaths.RSA_KEY_FILE_PATH_IN_WORKING_DIR_VERSION_1;
import static pl.softcredit.mpjk.engine.TestPaths.RSA_KEY_FILE_PATH_IN_RESOURCES_VERSION_1;
import static pl.softcredit.mpjk.engine.TestPaths.SCHEMES_DIR_PATH_IN_TEST_RESOURCES;
import static pl.softcredit.mpjk.engine.TestPaths.WORKING_DIR_PATH_IN_TARGET;
import static pl.softcredit.mpjk.engine.TestPaths.XML_FILE_NAME_SCHEME_VERSION_1;
import static pl.softcredit.mpjk.engine.TestPaths.assertFile;
import static pl.softcredit.mpjk.engine.processors.JpkProcessors.RSA_ENCRYPT_STAGE_PROCESSOR;

@RunWith(MockitoJUnitRunner.class)
public class RsaEncryptStageProcessorTest {

    @Rule
    public ExpectedException expectedException = none();

    @Mock
    private JpkConfiguration config;

    private JpkProcessor rsaGeneratorStageProcessor = RSA_ENCRYPT_STAGE_PROCESSOR;

    @Before
    public void setUp() throws IOException {
        cleanDirectory(new File(WORKING_DIR_PATH_IN_TARGET));
        copyDirectory(new File(INPUT_FILES_DIR_PATH_IN_TEST_RESOURCES), new File(WORKING_DIR_PATH_IN_TARGET));
        delete(new File(RSA_KEY_FILE_PATH_IN_WORKING_DIR_VERSION_1));
        whenConfigurationWith(XML_FILE_NAME_SCHEME_VERSION_1, JPK_VAT_SCHEME_FILE_NAME_VERSION_1);
    }

    @Test
    public void shouldSaveFileWithGeneratedRsaKeyIntoWorkingDirectory() throws Exception {
        rsaGeneratorStageProcessor.process(config);

        assertFile(RSA_KEY_FILE_NAME_SCHEME_VERSION_1);
    }

    @Test
    public void shouldThrowExceptionWhenCertificateIsInvalid() throws Exception {
        when(config.getRsaKeyPath()).thenReturn("invalid_certificate");
        expectedException.expect(JpkException.class);

        rsaGeneratorStageProcessor.process(config);
    }

    private void whenConfigurationWith(String inputFile, String schemeFile) {
        when(config.getWorkingDirectoryPath()).thenReturn(WORKING_DIR_PATH_IN_TARGET);
        when(config.getSchemeFilePath()).thenReturn(SCHEMES_DIR_PATH_IN_TEST_RESOURCES + schemeFile);
        when(config.getInputFilePath()).thenReturn(INPUT_FILES_DIR_PATH_IN_TEST_RESOURCES + inputFile);
        when(config.getRsaKeyPath()).thenReturn(RSA_KEY_FILE_PATH_IN_RESOURCES_VERSION_1);
    }

}