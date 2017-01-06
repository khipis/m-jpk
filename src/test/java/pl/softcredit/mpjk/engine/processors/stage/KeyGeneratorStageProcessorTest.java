package pl.softcredit.mpjk.engine.processors.stage;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import pl.softcredit.mpjk.core.configuration.JpkConfiguration;
import pl.softcredit.mpjk.engine.processors.JpkProcessor;

import java.io.File;
import java.io.IOException;

import static java.io.File.separator;
import static org.apache.commons.io.FileUtils.cleanDirectory;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.rules.ExpectedException.none;
import static org.mockito.Mockito.when;
import static pl.softcredit.mpjk.engine.TestDummies.INPUT_FILES_DIR;
import static pl.softcredit.mpjk.engine.TestDummies.JPK_VAT_SCHEME_FILE;
import static pl.softcredit.mpjk.engine.TestDummies.KEY_FILE_NAME;
import static pl.softcredit.mpjk.engine.TestDummies.SCHEMES_DIR;
import static pl.softcredit.mpjk.engine.TestDummies.TEMP_WORKING_DIR;
import static pl.softcredit.mpjk.engine.TestDummies.VALID_FILE_NAME;

@RunWith(MockitoJUnitRunner.class)
public class KeyGeneratorStageProcessorTest {

    @Rule
    public ExpectedException expectedException = none();

    @Mock
    private JpkConfiguration config;

    private JpkProcessor keyGeneratorStageProcessor = new KeyGeneratorStageProcessor();

    @Before
    public void setUp() throws IOException {
        cleanDirectory(new File(TEMP_WORKING_DIR));
    }

    @Test
    public void shouldSaveFileWithGeneratedKeyIntoWorkingDirectory() throws Exception {
        whenConfigurationWith(VALID_FILE_NAME, JPK_VAT_SCHEME_FILE);

        keyGeneratorStageProcessor.process(config);

        assertFile(KEY_FILE_NAME);
    }

    private void assertFile(String inputFile) throws IOException {
        File createdFile = getValidatedFile(inputFile);
        assertThat(createdFile).exists();
    }

    private void whenConfigurationWith(String inputFile, String schemeFile) {
        when(config.getWorkingDirectoryPath()).thenReturn(TEMP_WORKING_DIR);
        when(config.getSchemeFilePath()).thenReturn(SCHEMES_DIR + schemeFile);
        when(config.getInputFilePath()).thenReturn(INPUT_FILES_DIR + inputFile);
    }

    private File getValidatedFile(String inputFile) {
        return new File(TEMP_WORKING_DIR + separator + inputFile);
    }

}