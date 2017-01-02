package pl.softcredit.mpjk.engine.processors.preparation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import pl.softcredit.mpjk.core.configuration.JpkConfiguration;

import java.io.File;

import static org.apache.commons.io.FileUtils.deleteDirectory;
import static org.apache.commons.io.FileUtils.writeStringToFile;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static pl.softcredit.mpjk.engine.TestDummies.FILE_CONTENT;
import static pl.softcredit.mpjk.engine.TestDummies.TEMP_FILE_NAME;
import static pl.softcredit.mpjk.engine.TestDummies.TEMP_INPUT_FILE;
import static pl.softcredit.mpjk.engine.TestDummies.TEMP_WORKING_DIR;

@RunWith(MockitoJUnitRunner.class)
public class CleanWorkingDirectoryProcessorTest {

    @Mock
    private JpkConfiguration config;

    private JpkProcessor cleanWorkingDirectoryProcessor =
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
