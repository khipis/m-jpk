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

@RunWith(MockitoJUnitRunner.class)
public class CleanWorkingDirectoryProcessorTest {

    private static final String FILE_CONTENT = "VALID";

    @Mock
    private JpkConfiguration config;

    private CleanWorkingDirectoryProcessor cleanWorkingDirectoryProcessor = new CleanWorkingDirectoryProcessor();

    @Test
    public void shouldCreateWorkingDirIfItNotExists() throws Exception {
        deleteDirectory(new File("target/working-dir"));
        whenConfiguration();

        cleanWorkingDirectoryProcessor.process(config);

        assertThat(new File("target/working-dir")).exists();
    }

    @Test
    public void shouldCleanWorkingDirIfExists() throws Exception {
        whenConfiguration();
        writeStringToFile(new File("target/working-dir/tempfile.xml"), "Some content");

        cleanWorkingDirectoryProcessor.process(config);

        assertThat(new File("target/working-dir")).exists();
        assertThat(new File("target/working-dir/tempfile.xml")).doesNotExist();
    }

    private void whenConfiguration() {
        when(config.getWorkingDirectoryPath()).thenReturn("target/working-dir");
        when(config.getInputFilePath()).thenReturn("tempfile.xml");
    }

}
