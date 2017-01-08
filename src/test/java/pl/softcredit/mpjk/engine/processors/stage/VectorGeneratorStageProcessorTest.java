package pl.softcredit.mpjk.engine.processors.stage;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import pl.softcredit.mpjk.core.configuration.JpkConfiguration;
import pl.softcredit.mpjk.engine.processors.JpkProcessor;

import java.io.File;
import java.io.IOException;

import static org.apache.commons.io.FileUtils.cleanDirectory;
import static org.mockito.Mockito.when;
import static pl.softcredit.mpjk.engine.TestPaths.JPK_VAT_SCHEME_FILE_NAME_VERSION_1;
import static pl.softcredit.mpjk.engine.TestPaths.INPUT_FILES_DIR_PATH_IN_TEST_RESOURCES;
import static pl.softcredit.mpjk.engine.TestPaths.JPK_VAT_SCHEME_FILE_NAME_VERSION_2;
import static pl.softcredit.mpjk.engine.TestPaths.SCHEMES_DIR_PATH_IN_TEST_RESOURCES;
import static pl.softcredit.mpjk.engine.TestPaths.VEC_FILE_NAME_BASE64_SCHEME_VERSION_2;
import static pl.softcredit.mpjk.engine.TestPaths.VEC_FILE_NAME_SCHEME_VERSION_2;
import static pl.softcredit.mpjk.engine.TestPaths.WORKING_DIR_PATH_IN_TARGET;
import static pl.softcredit.mpjk.engine.TestPaths.XML_FILE_NAME_SCHEME_VERSION_1;
import static pl.softcredit.mpjk.engine.TestPaths.VEC_FILE_NAME_SCHEME_VERSION_1;
import static pl.softcredit.mpjk.engine.TestPaths.VEC_FILE_NAME_BASE64_SCHEME_VERSION_1;
import static pl.softcredit.mpjk.engine.TestPaths.XML_FILE_NAME_SCHEME_VERSION_2;
import static pl.softcredit.mpjk.engine.TestPaths.assertFile;
import static pl.softcredit.mpjk.engine.processors.JpkProcessors.VECTOR_GENERATOR_STAGE_PROCESSOR;

@RunWith(MockitoJUnitRunner.class)
public class VectorGeneratorStageProcessorTest {

    @Mock
    private JpkConfiguration config;

    private JpkProcessor vectorGeneratorStageProcessor = VECTOR_GENERATOR_STAGE_PROCESSOR;

    @Before
    public void setUp() throws IOException {
        cleanDirectory(new File(WORKING_DIR_PATH_IN_TARGET));
    }

    @Test
    public void shouldSaveFileWithGeneratedVectorIntoWorkingDirectoryForVersion1() throws Exception {
        whenConfigurationWith(XML_FILE_NAME_SCHEME_VERSION_1, JPK_VAT_SCHEME_FILE_NAME_VERSION_1);

        vectorGeneratorStageProcessor.process(config);

        assertFile(VEC_FILE_NAME_SCHEME_VERSION_1);
        assertFile(VEC_FILE_NAME_BASE64_SCHEME_VERSION_1);
    }

    @Test
    public void shouldSaveFileWithGeneratedVectorIntoWorkingDirectoryForVersion2() throws Exception {
        whenConfigurationWith(XML_FILE_NAME_SCHEME_VERSION_2, JPK_VAT_SCHEME_FILE_NAME_VERSION_2);

        vectorGeneratorStageProcessor.process(config);

        assertFile(VEC_FILE_NAME_SCHEME_VERSION_2);
        assertFile(VEC_FILE_NAME_BASE64_SCHEME_VERSION_2);
    }

    private void whenConfigurationWith(String inputFile, String schemeFile) {
        when(config.getWorkingDirectoryPath()).thenReturn(WORKING_DIR_PATH_IN_TARGET);
        when(config.getSchemeFilePath()).thenReturn(SCHEMES_DIR_PATH_IN_TEST_RESOURCES + schemeFile);
        when(config.getInputFilePath()).thenReturn(INPUT_FILES_DIR_PATH_IN_TEST_RESOURCES + inputFile);
    }
}