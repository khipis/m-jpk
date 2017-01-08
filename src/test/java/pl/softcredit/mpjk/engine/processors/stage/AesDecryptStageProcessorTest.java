package pl.softcredit.mpjk.engine.processors.stage;

import org.junit.Before;
import org.junit.Test;
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
import static org.mockito.Mockito.when;
import static pl.softcredit.mpjk.engine.TestPaths.AES_FILE_PATH_IN_WORKING_DIR_VERSION_1;
import static pl.softcredit.mpjk.engine.TestPaths.JPK_VAT_SCHEME_FILE_NAME_VERSION_1;
import static pl.softcredit.mpjk.engine.TestPaths.INPUT_FILES_DIR_PATH_IN_TEST_RESOURCES;
import static pl.softcredit.mpjk.engine.TestPaths.SCHEMES_DIR_PATH_IN_TEST_RESOURCES;
import static pl.softcredit.mpjk.engine.TestPaths.WORKING_DIR_PATH_IN_TARGET;
import static pl.softcredit.mpjk.engine.TestPaths.ZIP_FILE_PATH_IN_WORKING_DIR_VERSION_1;
import static pl.softcredit.mpjk.engine.TestPaths.ZIP_FILE_NAME_SCHEME_VERSION_1;
import static pl.softcredit.mpjk.engine.TestPaths.assertFile;
import static pl.softcredit.mpjk.engine.processors.JpkProcessors.AES_DECRYPT_STAGE_PROCESSOR;

@RunWith(MockitoJUnitRunner.class)
public class AesDecryptStageProcessorTest {

    @Mock
    private JpkConfiguration config;

    private JpkProcessor aesDecryptStageProcessor = AES_DECRYPT_STAGE_PROCESSOR;

    @Before
    public void setUp() throws IOException, JpkException {
        cleanDirectory(new File(WORKING_DIR_PATH_IN_TARGET));
        copyDirectory(new File(INPUT_FILES_DIR_PATH_IN_TEST_RESOURCES), new File(WORKING_DIR_PATH_IN_TARGET));
        delete(new File(ZIP_FILE_PATH_IN_WORKING_DIR_VERSION_1));
        whenConfigurationWith(AES_FILE_PATH_IN_WORKING_DIR_VERSION_1, JPK_VAT_SCHEME_FILE_NAME_VERSION_1);
    }

    @Test
    public void shouldSaveAesDecryptedFileIntoWorkingDirectory() throws Exception {
        aesDecryptStageProcessor.process(config);

        assertFile(ZIP_FILE_NAME_SCHEME_VERSION_1);
    }

    private void whenConfigurationWith(String inputFile, String schemeFile) {
        when(config.getWorkingDirectoryPath()).thenReturn(WORKING_DIR_PATH_IN_TARGET);
        when(config.getSchemeFilePath()).thenReturn(SCHEMES_DIR_PATH_IN_TEST_RESOURCES + schemeFile);
        when(config.getInputFilePath()).thenReturn(inputFile);
    }

}