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

import static org.junit.rules.ExpectedException.none;
import static org.mockito.Mockito.when;
import static pl.softcredit.mpjk.engine.TestDummies.INPUT_FILES_DIR;
import static pl.softcredit.mpjk.engine.TestDummies.SCHEMES_DIR;
import static pl.softcredit.mpjk.engine.TestDummies.TEMP_WORKING_DIR;
import static pl.softcredit.mpjk.engine.TestDummies.VALID_FILE_NAME;

@RunWith(MockitoJUnitRunner.class)
public class SchemeValidationProcessorTest {

    @Rule
    public ExpectedException expectedException = none();

    @Mock
    private JpkConfiguration config;

    private JpkProcessor schemeValidationProcessor = new SchemeValidationProcessor();

    @Test
    public void shouldThrowJpkExceptionWhenSchemeIsInvalidAtAll() throws Exception {
        whenConfigurationWith(VALID_FILE_NAME, "invalidScheme.xsd");

        expectedException.expect(JpkException.class);

        schemeValidationProcessor.process(config);
    }

    private void whenConfigurationWith(String inputFile, String schemeFile) {
        when(config.getWorkingDirectoryPath()).thenReturn(TEMP_WORKING_DIR);
        when(config.getSchemeFilePath()).thenReturn(SCHEMES_DIR + schemeFile);
        when(config.getInputFilePath()).thenReturn(INPUT_FILES_DIR + inputFile);
    }

}