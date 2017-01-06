package pl.softcredit.mpjk.core.configuration;

import org.junit.Test;

import pl.softcredit.mpjk.JpkException;

import static org.assertj.core.api.Assertions.assertThat;

public class FileJpkConfigurationTest {

    @Test
    public void shouldLoadConfigurationFile() throws JpkException {
        JpkConfiguration jpkConfiguration = new FileJpkConfiguration("config.properties");

        assertThat(jpkConfiguration.getWorkingDirectoryPath()).isNotEmpty();
        assertThat(jpkConfiguration.getSchemeFilePath()).isNotEmpty();
        assertThat(jpkConfiguration.getInputFilePath()).isNotEmpty();
        assertThat(jpkConfiguration.getProcessingFlow()).isNotEmpty();
    }

    @Test(expected = JpkException.class)
    public void shouldThrowJpkExceptionWhenConfigFileNotExists() throws JpkException {
        new FileJpkConfiguration("config.propertiese");
    }

}