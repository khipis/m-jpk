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
import static pl.softcredit.mpjk.engine.TestPaths.INPUT_FILES_DIR_PATH_IN_TEST_RESOURCES;
import static pl.softcredit.mpjk.engine.TestPaths.SCHEMES_DIR_PATH_IN_TEST_RESOURCES;
import static pl.softcredit.mpjk.engine.TestPaths.WORKING_DIR_PATH_IN_TARGET;
import static pl.softcredit.mpjk.engine.TestPaths.XML_FILE_NAME_SCHEME_VERSION_1;

@RunWith(MockitoJUnitRunner.class)
public class SchemeValidationProcessorTest {

    @Rule
    public ExpectedException expectedException = none();

    @Mock
    private JpkConfiguration config;

    private JpkProcessor schemeValidationProcessor = new SchemeValidationProcessor();


    @Test
    public void shouldThrowJpkExceptionWhenSchemeIsInvalidAtAll() throws Exception {
        whenConfigurationWith(XML_FILE_NAME_SCHEME_VERSION_1, "invalidScheme.xsd");

        expectedException.expect(JpkException.class);
        expectedException.expectMessage(
                "org.xml.sax.SAXParseException; systemId: file:/G:/work/m-jpk/src/test/resources/schemes/invalidScheme.xsd; "
                + "lineNumber: 3; columnNumber: 3; The element type \"xsd:schema\" must be terminated by the matching end-tag \""
                + "</xsd:schema>\".");

        schemeValidationProcessor.process(config);
    }

    private void whenConfigurationWith(String inputFile, String schemeFile) {
        when(config.getWorkingDirectoryPath()).thenReturn(WORKING_DIR_PATH_IN_TARGET);
        when(config.getSchemeFilePath()).thenReturn(SCHEMES_DIR_PATH_IN_TEST_RESOURCES + schemeFile);
        when(config.getInputFilePath()).thenReturn(INPUT_FILES_DIR_PATH_IN_TEST_RESOURCES + inputFile);
    }

}