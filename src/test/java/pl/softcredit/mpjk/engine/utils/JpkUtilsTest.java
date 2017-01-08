package pl.softcredit.mpjk.engine.utils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import pl.softcredit.mpjk.core.configuration.JpkConfiguration;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static pl.softcredit.mpjk.engine.TestPaths.AES_FILE_PATH_FROM_MF;
import static pl.softcredit.mpjk.engine.TestPaths.TEMP_FILE_NAME;
import static pl.softcredit.mpjk.engine.TestPaths.TEMP_WORKING_DIR;
import static pl.softcredit.mpjk.engine.TestPaths.VALID_FILE_FROM_MF_PATH_FROM_RESOURCES;
import static pl.softcredit.mpjk.engine.TestPaths.VALID_FILE_PATH_FROM_RESOURCES;
import static pl.softcredit.mpjk.engine.TestPaths.ZIP_FILE_PATH_FROM_MF;
import static pl.softcredit.mpjk.engine.utils.JpkUtils.extractFileNameFromInputFilePath;
import static pl.softcredit.mpjk.engine.utils.JpkUtils.extractFileNameWithoutExtension;
import static pl.softcredit.mpjk.engine.utils.JpkUtils.getContentLength;
import static pl.softcredit.mpjk.engine.utils.JpkUtils.getOutputPath;
import static pl.softcredit.mpjk.engine.utils.JpkUtils.getPathForAesDecryptStage;
import static pl.softcredit.mpjk.engine.utils.JpkUtils.getPathForAesEncryptStage;
import static pl.softcredit.mpjk.engine.utils.JpkUtils.getPathForFormalValidation;
import static pl.softcredit.mpjk.engine.utils.JpkUtils.getPathForKeyGeneratorStage;
import static pl.softcredit.mpjk.engine.utils.JpkUtils.getPathForKeyRsaEncryptStage;
import static pl.softcredit.mpjk.engine.utils.JpkUtils.getPathForMd5GeneratorStage;
import static pl.softcredit.mpjk.engine.utils.JpkUtils.getPathForShaGeneratorStage;
import static pl.softcredit.mpjk.engine.utils.JpkUtils.getPathForVectorGeneratorStage;
import static pl.softcredit.mpjk.engine.utils.JpkUtils.getPathForVectorRsaEncryptStage;
import static pl.softcredit.mpjk.engine.utils.JpkUtils.getPathForZipStage;

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
    public void shouldGetOutputPathForShaGeneratorStage() throws Exception {
        String result = getPathForShaGeneratorStage(config);

        assertThat(result).isEqualTo("target/working-dir\\tempfile.sha");
    }

    @Test
    public void shouldGetOutputPathForMd5GeneratorStage() throws Exception {
        when(config.getInputFilePath()).thenReturn("target/working-dir\\tempfile.xml.zip.aes");

        String result = getPathForMd5GeneratorStage(config);

        assertThat(result).isEqualTo("target/working-dir\\tempfile.xml.zip.aes.md5");
    }

    @Test
    public void shouldGetOutputPathForKeyRsaEncryptStage() throws Exception {
        String result = getPathForKeyRsaEncryptStage(config);

        assertThat(result).isEqualTo("target/working-dir\\tempfile.key.rsa");
    }

    @Test
    public void shouldGetOutputPathForVecRsaEncryptStage() throws Exception {
        String result = getPathForVectorRsaEncryptStage(config);

        assertThat(result).isEqualTo("target/working-dir\\tempfile.vec.rsa");
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

    @Test
    public void shouldGetContentLength() throws Exception {
        long result = getContentLength(new File(AES_FILE_PATH_FROM_MF));

        assertThat(result).isEqualTo(800L);

        result = getContentLength(new File(ZIP_FILE_PATH_FROM_MF));

        assertThat(result).isEqualTo(797L);

        result = getContentLength(new File(VALID_FILE_PATH_FROM_RESOURCES));

        assertThat(result).isEqualTo(1393L);

        result = getContentLength(new File(VALID_FILE_FROM_MF_PATH_FROM_RESOURCES));

        assertThat(result).isEqualTo(1393L);
    }

}
