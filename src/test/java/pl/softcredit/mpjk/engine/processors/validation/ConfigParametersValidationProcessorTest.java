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

import static org.junit.rules.ExpectedException.*;
import static org.mockito.Mockito.when;
import static pl.softcredit.mpjk.engine.TestPaths.INPUT_FILES_DIR_PATH_IN_TEST_RESOURCES;
import static pl.softcredit.mpjk.engine.TestPaths.JPK_VAT_SCHEME_FILE_NAME_VERSION_1;
import static pl.softcredit.mpjk.engine.TestPaths.NOT_EXISTING_FILE_NAME;
import static pl.softcredit.mpjk.engine.TestPaths.SCHEMES_DIR_PATH_IN_TEST_RESOURCES;
import static pl.softcredit.mpjk.engine.TestPaths.WORKING_DIR_PATH_IN_TARGET;
import static pl.softcredit.mpjk.engine.TestPaths.XML_FILE_NAME_SCHEME_VERSION_1;

@RunWith(MockitoJUnitRunner.class)
public class ConfigParametersValidationProcessorTest {

    @Rule
    public ExpectedException expectedException = none();

    @Mock
    private JpkConfiguration config;

    private JpkProcessor configParametersValidationProcessor = new ConfigParametersValidationProcessor();

    @Test
    public void shouldThrowJpkExceptionWhenSchemeExistsButInputFileNot() throws Exception {
        setupConfiguration(NOT_EXISTING_FILE_NAME, SCHEMES_DIR_PATH_IN_TEST_RESOURCES
                                                   + JPK_VAT_SCHEME_FILE_NAME_VERSION_1);

        expectedExceptionWithMessage("Input file not exists: " + INPUT_FILES_DIR_PATH_IN_TEST_RESOURCES
                                     + NOT_EXISTING_FILE_NAME);

        configParametersValidationProcessor.process(config);
    }

    @Test
    public void shouldThrowJpkExceptionWhenInputFileExistsButSchemeFileNot() throws Exception {
        setupConfiguration(XML_FILE_NAME_SCHEME_VERSION_1, NOT_EXISTING_FILE_NAME);

        expectedExceptionWithMessage("Scheme file not exists: " + SCHEMES_DIR_PATH_IN_TEST_RESOURCES
                                     + NOT_EXISTING_FILE_NAME);

        configParametersValidationProcessor.process(config);
    }

    @Test
    public void shouldNotThrowAnyExceptionWhenInputFileAndSchemeExists() throws Exception {
        setupConfiguration(XML_FILE_NAME_SCHEME_VERSION_1, JPK_VAT_SCHEME_FILE_NAME_VERSION_1);

        configParametersValidationProcessor.process(config);
    }

    private void expectedExceptionWithMessage(String exceptionMessage) {
        expectedException.expect(JpkException.class);
        expectedException.expectMessage(exceptionMessage);
    }

    private void setupConfiguration(String inputFile, String schemeFile) {
        when(config.getWorkingDirectoryPath()).thenReturn(WORKING_DIR_PATH_IN_TARGET);
        when(config.getSchemeFilePath()).thenReturn(SCHEMES_DIR_PATH_IN_TEST_RESOURCES + schemeFile);
        when(config.getInputFilePath()).thenReturn(INPUT_FILES_DIR_PATH_IN_TEST_RESOURCES + inputFile);
    }
}