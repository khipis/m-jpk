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
import static pl.softcredit.mpjk.engine.utils.JpkExtensions.ZIP_EXTENSION;
import static pl.softcredit.mpjk.engine.utils.JpkOutputUtils.extractFileNameFromInputFilePath;
import static pl.softcredit.mpjk.engine.utils.JpkOutputUtils.extractFileNameWithoutExtension;
import static pl.softcredit.mpjk.engine.utils.JpkOutputUtils.getOutputPath;
import static pl.softcredit.mpjk.engine.utils.JpkOutputUtils.getOutputPathForAesEncryptStage;
import static pl.softcredit.mpjk.engine.utils.JpkOutputUtils.getOutputPathForFormalValidation;
import static pl.softcredit.mpjk.engine.utils.JpkOutputUtils.getOutputPathForKeyGeneratorStage;
import static pl.softcredit.mpjk.engine.utils.JpkOutputUtils.getOutputPathForZipStage;
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

    @Test
    public void shouldGetOutputPathForFormalValidation() throws Exception {
        whenConfiguration();

        String result = getOutputPathForFormalValidation(config);

        assertThat(result).isEqualTo("target/working-dir\\tempfile.xml.validation");
    }

    @Test
    public void shouldGetOutputPathForKeyGeneratorStage() throws Exception {
        whenConfiguration();

        String result = getOutputPathForKeyGeneratorStage(config);

        assertThat(result).isEqualTo("target/working-dir\\tempfile.key");
    }

    @Test
    public void shouldGetOutputPathForZipStage() throws Exception {
        whenConfiguration();

        String result = getOutputPathForZipStage(config);

        assertThat(result).isEqualTo("target/working-dir\\tempfile.xml.zip");
    }

    @Test
    public void shouldGetOutputPathForAesEncryptStage() throws Exception {
        when(config.getWorkingDirectoryPath()).thenReturn(TEMP_WORKING_DIR);
        when(config.getInputFilePath()).thenReturn(TEMP_FILE_NAME + ZIP_EXTENSION);

        String result = getOutputPathForAesEncryptStage(config);

        assertThat(result).isEqualTo("target/working-dir\\tempfile.xml.zip.aes");
    }

    @Test
    public void shouldExtractFileName() throws Exception {
        whenConfiguration();

        String result = extractFileNameFromInputFilePath(config);

        assertThat(result).isEqualTo("tempfile.xml");
    }

    @Test
    public void shouldExtractFileNameWithoutExtension() throws Exception {
        whenConfiguration();

        String result = extractFileNameWithoutExtension(config);

        assertThat(result).isEqualTo("tempfile");
    }

    @Test
    public void shouldGetOutputPath() throws Exception {
        whenConfiguration();

        String result = getOutputPath(config);

        assertThat(result).isEqualTo("target/working-dir\\tempfile.xml");
    }

    private void whenConfiguration() {
        when(config.getWorkingDirectoryPath()).thenReturn(TEMP_WORKING_DIR);
        when(config.getInputFilePath()).thenReturn(TEMP_FILE_NAME);
    }
}
