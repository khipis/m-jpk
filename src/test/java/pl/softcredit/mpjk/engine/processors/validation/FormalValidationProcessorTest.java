package pl.softcredit.mpjk.engine.processors.validation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import pl.softcredit.mpjk.core.configuration.JpkConfiguration;

import java.io.File;
import java.io.IOException;

import static java.io.File.separator;
import static org.apache.commons.io.FileUtils.readFileToString;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static pl.softcredit.mpjk.engine.utils.JpkExtensions.VALIDATION_EXTENSION;

@RunWith(MockitoJUnitRunner.class)
public class FormalValidationProcessorTest {

    private static final String FILE_CONTENT = "VALID";
    private static final String TEMP_WORKING_DIR = "target/working-dir";
    private static final String SCHEMES_DIR = "src/test/resources/schemes/";
    private static final String INPUT_FILES_DIR = "src/test/resources/input-files/";
    private static final String VALID_FILE = "valid_JPK_VAT(1)_file.xml";
    private static final String INVALID_FILE = "invalid_JPK_VAT(1)_file.xml";
    private static final String JPK_VAT_SCHEME_FILE = "Schemat_JPK_VAT(1)_v1-0.xsd";

    @Mock
    private JpkConfiguration config;

    private FormalValidationProcessor formalValidationProcessor = new FormalValidationProcessor();

    @Test
    public void shouldReturnFileWithContentValidIfFileIsValidToScheme() throws Exception {
        setupConfiguration(VALID_FILE, JPK_VAT_SCHEME_FILE);

        formalValidationProcessor.process(config);

        assertFile(VALID_FILE, FILE_CONTENT);
    }

    @Test
    public void shouldReturnFileWithErrorDescriptionWhenFileIsInvalidToScheme() throws Exception {
        setupConfiguration(INVALID_FILE, JPK_VAT_SCHEME_FILE);

        formalValidationProcessor.process(config);

        assertFile(INVALID_FILE,
                   "Invalid content was found starting with element");
    }

    @Test
    public void shouldReturnFileWithErrorDescriptionWhenSchemeIsInvalidToFile() throws Exception {
        setupConfiguration(VALID_FILE, "Schemat_JPK_VAT(2)_v1-0.xsd");

        formalValidationProcessor.process(config);

        assertFile(VALID_FILE, "Cannot find the declaration of element 'tns:JPK'.");
    }

    private void assertFile(String inputFile, String content) throws IOException {
        File createdFile = getValidatedFile(inputFile);
        assertThat(createdFile).exists();
        assertThat(readFileToString(createdFile)).contains(content);
    }

    private void setupConfiguration(String inputFile, String schemeFile) {
        when(config.getWorkingDirectoryPath()).thenReturn(TEMP_WORKING_DIR);
        when(config.getSchemeFilePath()).thenReturn(SCHEMES_DIR + schemeFile);
        when(config.getInputFilePath()).thenReturn(INPUT_FILES_DIR + inputFile);
    }

    private File getValidatedFile(String inputFile) {
        return new File(TEMP_WORKING_DIR + separator + inputFile + VALIDATION_EXTENSION);
    }

}