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
import static pl.softcredit.mpjk.engine.utils.JpkExtensions.VALIDATION_EXTENSION;
import static pl.softcredit.mpjk.engine.utils.JpkOutputUtils.saveFormalValidationOutput;

@RunWith(MockitoJUnitRunner.class)
public class JpkOutputUtilsTest {

    private static final String FILE_CONTENT = "VALID";

    @Mock private JpkConfiguration config;

    @Test
    public void shouldSaveFormalValidationOutputWithContent() throws Exception {
        when(config.getWorkingDirectoryPath()).thenReturn("target/working-dir");
        when(config.getInputFilePath()).thenReturn("tempfile.xml");

        saveFormalValidationOutput(config, FILE_CONTENT);

        File createdFile = new File("target/working-dir/tempfile.xml" + VALIDATION_EXTENSION);

        assertThat(createdFile).exists();
        String result = readFileToString(createdFile);
        assertThat(result).isEqualTo(FILE_CONTENT);
    }

}