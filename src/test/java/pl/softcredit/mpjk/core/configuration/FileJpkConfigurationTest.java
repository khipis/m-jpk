package pl.softcredit.mpjk.core.configuration;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class FileJpkConfigurationTest {

    private final FileJpkConfiguration
            fileJpkConfiguration = new FileJpkConfiguration("G:\\work\\m-jpk\\src\\main\\resources\\config.properties");

    @Test
    public void shouldLoadConfigurationFile() {
        assertThat(fileJpkConfiguration.getWorkingDirectoryPath()).isNotEmpty();
        assertThat(fileJpkConfiguration.getSchemeFilePath()).isNotEmpty();
        assertThat(fileJpkConfiguration.getInputFilePath()).isNotEmpty();
    }

}