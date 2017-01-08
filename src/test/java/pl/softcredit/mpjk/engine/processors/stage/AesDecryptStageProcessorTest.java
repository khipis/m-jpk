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
import static pl.softcredit.mpjk.engine.TestPaths.AES_FILE_PATH;
import static pl.softcredit.mpjk.engine.TestPaths.JPK_VAT_SCHEME_FILE;
import static pl.softcredit.mpjk.engine.TestPaths.RESOURCES_INPUT_FILES;
import static pl.softcredit.mpjk.engine.TestPaths.SCHEMES_DIR;
import static pl.softcredit.mpjk.engine.TestPaths.TEMP_WORKING_DIR;
import static pl.softcredit.mpjk.engine.TestPaths.ZIPPED_FILE_PATH;
import static pl.softcredit.mpjk.engine.TestPaths.ZIP_FILE_NAME;
import static pl.softcredit.mpjk.engine.TestPaths.assertFile;
import static pl.softcredit.mpjk.engine.processors.JpkProcessors.AES_DECRYPT_STAGE_PROCESSOR;

@RunWith(MockitoJUnitRunner.class)
public class AesDecryptStageProcessorTest {

    @Mock
    private JpkConfiguration config;

    private JpkProcessor aesDecryptStageProcessor = AES_DECRYPT_STAGE_PROCESSOR;

    @Before
    public void setUp() throws IOException, JpkException {
        cleanDirectory(new File(TEMP_WORKING_DIR));
        copyDirectory(new File(RESOURCES_INPUT_FILES), new File(TEMP_WORKING_DIR));
        delete(new File(ZIPPED_FILE_PATH));
        whenConfigurationWith(AES_FILE_PATH, JPK_VAT_SCHEME_FILE);
    }

    @Test
    public void shouldSaveAesDecryptedFileIntoWorkingDirectory() throws Exception {
        aesDecryptStageProcessor.process(config);

        assertFile(ZIP_FILE_NAME);
    }

    private void whenConfigurationWith(String inputFile, String schemeFile) {
        when(config.getWorkingDirectoryPath()).thenReturn(TEMP_WORKING_DIR);
        when(config.getSchemeFilePath()).thenReturn(SCHEMES_DIR + schemeFile);
        when(config.getInputFilePath()).thenReturn(inputFile);
    }

}