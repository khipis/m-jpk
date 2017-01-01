package pl.softcredit.mpjk.engine.processors.validation;

import org.junit.Test;
import org.mockito.Mock;

import pl.softcredit.mpjk.core.configuration.JpkConfiguration;

public class FormalValidationProcessorTest {

    private static final String FILE_CONTENT = "VALID";

    @Mock
    private JpkConfiguration config;

    private FormalValidationProcessor formalValidationProcessor = new FormalValidationProcessor();

    @Test
    public void shouldSaveFileWithValidationResultValidWhenValidFileIsPassed() {
/*        when(config.getWorkingDirectoryPath()).thenReturn("target/working-dir");
        when(config.getInputFilePath()).thenReturn("tempfile.xml");

        saveFormalValidationOutput(config, FILE_CONTENT);

        File createdFile = new File("target/working-dir/tempfile.xml" + VALIDATION_EXTENSION);

        assertThat(createdFile).exists();
        String result = readFileToString(createdFile);
        assertThat(result).isEqualTo(FILE_CONTENT);*/
    }




}