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
import static pl.softcredit.mpjk.engine.TestDummies.INPUT_FILES_DIR;
import static pl.softcredit.mpjk.engine.TestDummies.JPK_VAT_SCHEME_FILE;
import static pl.softcredit.mpjk.engine.TestDummies.NOT_EXISTED_FILE;
import static pl.softcredit.mpjk.engine.TestDummies.SCHEMES_DIR;
import static pl.softcredit.mpjk.engine.TestDummies.TEMP_WORKING_DIR;
import static pl.softcredit.mpjk.engine.TestDummies.VALID_FILE;

@RunWith(MockitoJUnitRunner.class)
public class ConfigParametersValidationProcessorTest {

    @Rule
    public ExpectedException expectedException = none();

    @Mock
    private JpkConfiguration config;

    private JpkProcessor configParametersValidationProcessor = new ConfigParametersValidationProcessor();

    @Test
    public void shouldThrowJpkExceptionWhenSchemeExistsButInputFileNot() throws Exception {
        setupConfiguration(NOT_EXISTED_FILE, SCHEMES_DIR + JPK_VAT_SCHEME_FILE);

        expectedExceptionWithMessage("Input file not exists: " + INPUT_FILES_DIR + NOT_EXISTED_FILE);

        configParametersValidationProcessor.process(config);
    }

    @Test
    public void shouldThrowJpkExceptionWhenInputFileExistsButSchemeFileNot() throws Exception {
        setupConfiguration(VALID_FILE, NOT_EXISTED_FILE);

        expectedExceptionWithMessage("Scheme file not exists: " + SCHEMES_DIR + NOT_EXISTED_FILE);

        configParametersValidationProcessor.process(config);
    }

    @Test
    public void shouldNotThrowAnyExceptionWhenInputFileAndSchemeExists() throws Exception {
        setupConfiguration(VALID_FILE, JPK_VAT_SCHEME_FILE);

        configParametersValidationProcessor.process(config);
    }

    private void expectedExceptionWithMessage(String exceptionMessage) {
        expectedException.expect(JpkException.class);
        expectedException.expectMessage(exceptionMessage);
    }

    private void setupConfiguration(String inputFile, String schemeFile) {
        when(config.getWorkingDirectoryPath()).thenReturn(TEMP_WORKING_DIR);
        when(config.getSchemeFilePath()).thenReturn(SCHEMES_DIR + schemeFile);
        when(config.getInputFilePath()).thenReturn(INPUT_FILES_DIR + inputFile);
    }
}