package pl.softcredit.mpjk.engine.utils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import pl.softcredit.mpjk.core.configuration.JpkConfiguration;

import java.io.File;

import static org.apache.commons.io.FileUtils.readFileToString;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static pl.softcredit.mpjk.engine.TestPaths.FILE_CONTENT;
import static pl.softcredit.mpjk.engine.TestPaths.TEMP_FILE_NAME;
import static pl.softcredit.mpjk.engine.TestPaths.TEMP_INPUT_FILE;
import static pl.softcredit.mpjk.engine.TestPaths.TEMP_WORKING_DIR;
import static pl.softcredit.mpjk.engine.utils.JpkExtensions.VALIDATION_EXTENSION;
import static pl.softcredit.mpjk.engine.utils.JpkUtils.extractFileNameFromInputFilePath;
import static pl.softcredit.mpjk.engine.utils.JpkUtils.extractFileNameWithoutExtension;
import static pl.softcredit.mpjk.engine.utils.JpkUtils.getOutputPath;
import static pl.softcredit.mpjk.engine.utils.JpkUtils.getPathForAesDecryptStage;
import static pl.softcredit.mpjk.engine.utils.JpkUtils.getPathForAesEncryptStage;
import static pl.softcredit.mpjk.engine.utils.JpkUtils.getPathForFormalValidation;
import static pl.softcredit.mpjk.engine.utils.JpkUtils.getPathForKeyGeneratorStage;
import static pl.softcredit.mpjk.engine.utils.JpkUtils.getPathForVectorGeneratorStage;
import static pl.softcredit.mpjk.engine.utils.JpkUtils.getPathForZipStage;
import static pl.softcredit.mpjk.engine.utils.JpkUtils.saveFormalValidationOutput;

@RunWith(MockitoJUnitRunner.class)
public class JpkUtilsTest {

    @Mock
    private JpkConfiguration config;

    @Before
    public void setUp() {
        when(config.getWorkingDirectoryPath()).thenReturn(TEMP_WORKING_DIR);
        when(config.getInputFilePath()).thenReturn(TEMP_FILE_NAME);
    }

    @Test
    public void shouldSaveFormalValidationOutputWithContent() throws Exception {
        saveFormalValidationOutput(config, FILE_CONTENT);

        File createdFile = new File(TEMP_INPUT_FILE + VALIDATION_EXTENSION);

        assertThat(createdFile).exists();
        assertThat(readFileToString(createdFile)).isEqualTo(FILE_CONTENT);
    }

    @Test
    public void shouldGetOutputPathForFormalValidation() throws Exception {
        String result = getPathForFormalValidation(config);

        assertThat(result).isEqualTo("target/working-dir\\tempfile.xml.validation");
    }

    @Test
    public void shouldGetOutputPathForKeyGeneratorStage() throws Exception {
        String result = getPathForKeyGeneratorStage(config);

        assertThat(result).isEqualTo("target/working-dir\\tempfile.key");
    }

    @Test
    public void shouldGetOutputPathForKeyGeneratorStageFromAesFile() throws Exception {
        when(config.getInputFilePath()).thenReturn("target/working-dir\\tempfile.xml.zip.aes");

        String result = getPathForKeyGeneratorStage(config);

        assertThat(result).isEqualTo("target/working-dir\\tempfile.key");
    }

    @Test
    public void shouldGetOutputPathForVectorGeneratorStageFromAesFile() throws Exception {
        when(config.getInputFilePath()).thenReturn("target/working-dir\\tempfile.xml.zip.aes");

        String result = getPathForVectorGeneratorStage(config);

        assertThat(result).isEqualTo("target/working-dir\\tempfile.vec");
    }

    @Test
    public void shouldGetOutputPathForVectorGeneratorStage() throws Exception {
        String result = getPathForVectorGeneratorStage(config);

        assertThat(result).isEqualTo("target/working-dir\\tempfile.vec");
    }

    @Test
    public void shouldGetOutputPathForZipStage() throws Exception {
        String result = getPathForZipStage(config);

        assertThat(result).isEqualTo("target/working-dir\\tempfile.xml.zip");
    }

    @Test
    public void shouldGetOutputPathForAesEncryptStage() throws Exception {
        String result = getPathForAesEncryptStage(config);

        assertThat(result).isEqualTo("target/working-dir\\tempfile.xml.zip.aes");
    }

    @Test
    public void shouldRemoveAesExtension() throws Exception {
        when(config.getInputFilePath()).thenReturn("target/working-dir\\tempfile.xml.zip.aes");

        String result = getPathForAesDecryptStage(config);

        assertThat(result).isEqualTo("target/working-dir\\tempfile.xml.zip");
    }

    @Test
    public void shouldExtractFileName() throws Exception {
        String result = extractFileNameFromInputFilePath(config);

        assertThat(result).isEqualTo("tempfile.xml");
    }

    @Test
    public void shouldExtractFileNameWithoutExtension() throws Exception {
        String result = extractFileNameWithoutExtension(config);

        assertThat(result).isEqualTo("tempfile");
    }

    @Test
    public void shouldGetOutputPath() throws Exception {
        String result = getOutputPath(config);

        assertThat(result).isEqualTo("target/working-dir\\tempfile.xml");
    }


}
