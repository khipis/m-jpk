package pl.softcredit.mpjk.engine.processors.preparation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import pl.softcredit.mpjk.core.configuration.JpkConfiguration;

import java.io.File;

import static java.io.File.separator;
import static org.apache.commons.io.FileUtils.deleteDirectory;
import static org.apache.commons.io.FileUtils.writeStringToFile;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CleanWorkingDirectoryProcessorTest {

    private static final String FILE_CONTENT = "VALID";
    private static final String TEMP_FILE_NAME = "tempfile.xml";
    private static final String TEMP_WORKING_DIR = "target/working-dir";
    private static final String TEMP_INPUT_FILE = TEMP_WORKING_DIR + separator + "tempfile.xml";

    @Mock
    private JpkConfiguration config;

    private CleanWorkingDirectoryProcessor cleanWorkingDirectoryProcessor =
            new CleanWorkingDirectoryProcessor();

    @Test
    public void shouldCreateWorkingDirIfItNotExists() throws Exception {
        deleteDirectory(new File(TEMP_WORKING_DIR));
        whenConfiguration();

        cleanWorkingDirectoryProcessor.process(config);

        assertThat(new File(TEMP_WORKING_DIR)).exists();
    }

    @Test
    public void shouldCleanWorkingDirIfExists() throws Exception {
        whenConfiguration();
        writeStringToFile(new File(TEMP_INPUT_FILE), FILE_CONTENT);

        cleanWorkingDirectoryProcessor.process(config);

        assertThat(new File(TEMP_WORKING_DIR)).exists();
        assertThat(new File(TEMP_INPUT_FILE)).doesNotExist();
    }

    private void whenConfiguration() {
        when(config.getWorkingDirectoryPath()).thenReturn(TEMP_WORKING_DIR);
        when(config.getInputFilePath()).thenReturn(TEMP_FILE_NAME);
    }

}
