package pl.softcredit.mpjk.engine.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import pl.softcredit.mpjk.core.configuration.JpkConfiguration;

import java.io.File;

import static org.apache.commons.io.FileUtils.readFileToString;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static pl.softcredit.mpjk.engine.TestDummies.FILE_CONTENT;
import static pl.softcredit.mpjk.engine.TestDummies.TEMP_FILE_NAME;
import static pl.softcredit.mpjk.engine.TestDummies.TEMP_INPUT_FILE;
import static pl.softcredit.mpjk.engine.TestDummies.TEMP_WORKING_DIR;
import static pl.softcredit.mpjk.engine.utils.JpkExtensions.VALIDATION_EXTENSION;
import static pl.softcredit.mpjk.engine.utils.JpkOutputUtils.saveFormalValidationOutput;

@RunWith(MockitoJUnitRunner.class)
public class JpkOutputUtilsTest {

    @Mock private JpkConfiguration config;

    @Test
    public void shouldSaveFormalValidationOutputWithContent() throws Exception {
        whenConfiguration();

        saveFormalValidationOutput(config, FILE_CONTENT);

        File createdFile = new File(TEMP_INPUT_FILE + VALIDATION_EXTENSION);

        assertThat(createdFile).exists();
        assertThat(readFileToString(createdFile)).isEqualTo(FILE_CONTENT);
    }

    private void whenConfiguration() {
        when(config.getWorkingDirectoryPath()).thenReturn(TEMP_WORKING_DIR);
        when(config.getInputFilePath()).thenReturn(TEMP_FILE_NAME);
    }
}
