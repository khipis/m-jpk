package pl.softcredit.mpjk.engine.processors.stage;

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
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.rules.ExpectedException.none;
import static org.mockito.Mockito.when;
import static pl.softcredit.mpjk.engine.TestDummies.INPUT_FILES_DIR;
import static pl.softcredit.mpjk.engine.TestDummies.JPK_VAT_SCHEME_FILE;
import static pl.softcredit.mpjk.engine.TestDummies.SCHEMES_DIR;
import static pl.softcredit.mpjk.engine.TestDummies.TEMP_WORKING_DIR;
import static pl.softcredit.mpjk.engine.TestDummies.VALID_FILE;
import static pl.softcredit.mpjk.engine.TestDummies.VEC_FILE;

@RunWith(MockitoJUnitRunner.class)
public class VectorGeneratorStageProcessorTest {

    @Rule
    public ExpectedException expectedException = none();

    @Mock
    private JpkConfiguration config;

    private JpkProcessor vectorGeneratorStageProcessor = new VectorGeneratorStageProcessor();

    @Test
    public void shouldSaveFileWithGeneratedVectorIntoWorkingDirectory() throws Exception {
        whenConfigurationWith(VALID_FILE, JPK_VAT_SCHEME_FILE);

        vectorGeneratorStageProcessor.process(config);

        assertFile(VEC_FILE);
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