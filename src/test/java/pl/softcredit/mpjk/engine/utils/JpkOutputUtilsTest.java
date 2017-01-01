package pl.softcredit.mpjk.engine.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import pl.softcredit.mpjk.core.configuration.JpkConfiguration;

import java.io.File;

import static java.io.File.separator;
import static org.apache.commons.io.FileUtils.readFileToString;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static pl.softcredit.mpjk.engine.utils.JpkExtensions.VALIDATION_EXTENSION;
import static pl.softcredit.mpjk.engine.utils.JpkOutputUtils.saveFormalValidationOutput;

@RunWith(MockitoJUnitRunner.class)
public class JpkOutputUtilsTest {

    private static final String FILE_CONTENT = "VALID";
    private static final String TEMP_FILE_NAME = "tempfile.xml";
    private static final String TEMP_WORKING_DIR = "target/working-dir";
    private static final String TEMP_INPUT_FILE = TEMP_WORKING_DIR + separator + "tempfile.xml";

    @Mock private JpkConfiguration config;

    @Test
    public void shouldSaveFormalValidationOutputWithContent() throws Exception {
        whenConfiguration();

        saveFormalValidationOutput(config, FILE_CONTENT);

        File createdFile = new File(TEMP_INPUT_FILE + VALIDATION_EXTENSION);

        assertThat(createdFile).exists();
        String result = readFileToString(createdFile);
        assertThat(result).isEqualTo(FILE_CONTENT);
    }

    private void whenConfiguration() {
        when(config.getWorkingDirectoryPath()).thenReturn(TEMP_WORKING_DIR);
        when(config.getInputFilePath()).thenReturn(TEMP_FILE_NAME);
    }

}