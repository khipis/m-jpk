package pl.softcredit.mpjk.engine.processors.validation;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import pl.softcredit.mpjk.JpkException;
import pl.softcredit.mpjk.core.configuration.JpkConfiguration;
import pl.softcredit.mpjk.engine.processors.JpkProcessor;

import java.io.File;
import java.io.IOException;

import static java.io.File.separator;
import static org.apache.commons.io.FileUtils.readFileToString;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.rules.ExpectedException.none;
import static org.mockito.Mockito.when;
import static pl.softcredit.mpjk.engine.TestDummies.FILE_CONTENT;
import static pl.softcredit.mpjk.engine.TestDummies.RESOURCES_INPUT_FILES;
import static pl.softcredit.mpjk.engine.TestDummies.INVALID_FILE;
import static pl.softcredit.mpjk.engine.TestDummies.JPK_VAT_SCHEME_FILE;
import static pl.softcredit.mpjk.engine.TestDummies.SCHEMES_DIR;
import static pl.softcredit.mpjk.engine.TestDummies.TEMP_WORKING_DIR;
import static pl.softcredit.mpjk.engine.TestDummies.VALID_FILE_NAME;
import static pl.softcredit.mpjk.engine.utils.JpkExtensions.VALIDATION_EXTENSION;

@RunWith(MockitoJUnitRunner.class)
public class FormalValidationProcessorTest {

    @Rule
    public ExpectedException expectedException = none();

    @Mock
    private JpkConfiguration config;

    private JpkProcessor formalValidationProcessor = new FormalValidationProcessor();

    @Test
    public void shouldReturnFileWithContentValidIfFileIsValidToScheme() throws Exception {
        whenConfigurationWith(VALID_FILE_NAME, JPK_VAT_SCHEME_FILE);

        formalValidationProcessor.process(config);

        assertFile(VALID_FILE_NAME, FILE_CONTENT);
    }

    @Test
    public void shouldReturnFileWithErrorDescriptionWhenFileIsInvalidToScheme() throws Exception {
        whenConfigurationWith(INVALID_FILE, JPK_VAT_SCHEME_FILE);
        expectedException.expect(JpkException.class);

        formalValidationProcessor.process(config);

        assertFile(INVALID_FILE, "Invalid content was found starting with element");
    }

    @Test
    public void shouldReturnFileWithErrorDescriptionWhenSchemeIsInvalidToFile() throws Exception {
        whenConfigurationWith(VALID_FILE_NAME, "Schemat_JPK_VAT(2)_v1-0.xsd");
        expectedException.expect(JpkException.class);

        formalValidationProcessor.process(config);

        assertFile(VALID_FILE_NAME, "Cannot find the declaration of element 'tns:JPK'.");
    }

    private void assertFile(String inputFile, String content) throws IOException {
        File createdFile = getValidatedFile(inputFile);
        assertThat(createdFile).exists();
        assertThat(readFileToString(createdFile)).contains(content);
    }

    private void whenConfigurationWith(String inputFile, String schemeFile) {
        when(config.getWorkingDirectoryPath()).thenReturn(TEMP_WORKING_DIR);
        when(config.getSchemeFilePath()).thenReturn(SCHEMES_DIR + schemeFile);
        when(config.getInputFilePath()).thenReturn(RESOURCES_INPUT_FILES + inputFile);
    }

    private File getValidatedFile(String inputFile) {
        return new File(TEMP_WORKING_DIR + separator + inputFile + VALIDATION_EXTENSION);
    }

}