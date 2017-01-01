package pl.softcredit.mpjk.core.configuration;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class FileJpkConfigurationTest {

    private final JpkConfiguration jpkConfiguration = new FileJpkConfiguration("config.properties");

    @Test
    public void shouldLoadConfigurationFile() {
        assertThat(jpkConfiguration.getWorkingDirectoryPath()).isNotEmpty();
        assertThat(jpkConfiguration.getSchemeFilePath()).isNotEmpty();
        assertThat(jpkConfiguration.getInputFilePath()).isNotEmpty();
    }

}