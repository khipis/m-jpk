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
import static pl.softcredit.mpjk.engine.TestPaths.JPK_VAT_SCHEME_FILE_NAME_VERSION_2;
import static pl.softcredit.mpjk.engine.TestPaths.VALID_FILE_CONTENT;
import static pl.softcredit.mpjk.engine.TestPaths.INVALID_XML_FILE;
import static pl.softcredit.mpjk.engine.TestPaths.JPK_VAT_SCHEME_FILE_NAME_VERSION_1;
import static pl.softcredit.mpjk.engine.TestPaths.INPUT_FILES_DIR_PATH_IN_TEST_RESOURCES;
import static pl.softcredit.mpjk.engine.TestPaths.SCHEMES_DIR_PATH_IN_TEST_RESOURCES;
import static pl.softcredit.mpjk.engine.TestPaths.WORKING_DIR_PATH_IN_TARGET;
import static pl.softcredit.mpjk.engine.TestPaths.XML_FILE_NAME_SCHEME_VERSION_1;
import static pl.softcredit.mpjk.engine.TestPaths.XML_FILE_NAME_SCHEME_VERSION_2;
import static pl.softcredit.mpjk.engine.utils.JpkExtensions.VALIDATION_EXTENSION;

@RunWith(MockitoJUnitRunner.class)
public class FormalValidationProcessorTest {

    @Rule
    public ExpectedException expectedException = none();

    @Mock
    private JpkConfiguration config;

    private JpkProcessor formalValidationProcessor = new FormalValidationProcessor();

    @Test
    public void shouldReturnFileWithContentValidIfFileIsValidToSchemeForVersion1() throws Exception {
        whenConfigurationWith(XML_FILE_NAME_SCHEME_VERSION_1, JPK_VAT_SCHEME_FILE_NAME_VERSION_1);

        formalValidationProcessor.process(config);

        assertFile(XML_FILE_NAME_SCHEME_VERSION_1, VALID_FILE_CONTENT);
    }

    @Test
    public void shouldReturnFileWithErrorDescriptionWhenFileIsInvalidToSchemeForVersion1() throws Exception {
        whenConfigurationWith(INVALID_XML_FILE, JPK_VAT_SCHEME_FILE_NAME_VERSION_1);
        expectedException.expect(JpkException.class);

        formalValidationProcessor.process(config);

        assertFile(INVALID_XML_FILE, "Invalid content was found starting with element");
    }

    @Test
    public void shouldReturnFileWithContentValidIfFileIsValidToSchemeForVersion2() throws Exception {
        whenConfigurationWith(XML_FILE_NAME_SCHEME_VERSION_2, JPK_VAT_SCHEME_FILE_NAME_VERSION_2);

        formalValidationProcessor.process(config);

        assertFile(XML_FILE_NAME_SCHEME_VERSION_1, VALID_FILE_CONTENT);
    }

    @Test
    public void shouldReturnFileWithErrorDescriptionWhenFileIsInvalidToSchemeForVersion2() throws Exception {
        whenConfigurationWith(INVALID_XML_FILE, JPK_VAT_SCHEME_FILE_NAME_VERSION_2);
        expectedException.expect(JpkException.class);

        formalValidationProcessor.process(config);

        assertFile(INVALID_XML_FILE, "Invalid content was found starting with element");
    }

    @Test
    public void shouldReturnFileWithErrorDescriptionWhenSchemeIsInvalidToFile() throws Exception {
        whenConfigurationWith(XML_FILE_NAME_SCHEME_VERSION_1, "Schemat_JPK_VAT(2)_v1-0.xsd");
        expectedException.expect(JpkException.class);

        formalValidationProcessor.process(config);

        assertFile(XML_FILE_NAME_SCHEME_VERSION_1, "Cannot find the declaration of element 'tns:JPK'.");
    }

    private void assertFile(String inputFile, String content) throws IOException {
        File createdFile = getValidatedFile(inputFile);
        assertThat(createdFile).exists();
        assertThat(readFileToString(createdFile)).contains(content);
    }

    private void whenConfigurationWith(String inputFile, String schemeFile) {
        when(config.getWorkingDirectoryPath()).thenReturn(WORKING_DIR_PATH_IN_TARGET);
        when(config.getSchemeFilePath()).thenReturn(SCHEMES_DIR_PATH_IN_TEST_RESOURCES + schemeFile);
        when(config.getInputFilePath()).thenReturn(INPUT_FILES_DIR_PATH_IN_TEST_RESOURCES + inputFile);
    }

    private File getValidatedFile(String inputFile) {
        return new File(WORKING_DIR_PATH_IN_TARGET + separator + inputFile + VALIDATION_EXTENSION);
    }

}