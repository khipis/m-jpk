package pl.softcredit.mpjk.core.configuration;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ConfigurationServiceTest {

    private final ConfigurationService configurationService = new ConfigurationService("G:\\work\\m-jpk\\src\\main\\resources\\config.properties");

    @Test
    public void shouldLoadConfigurationFile() {
        assertThat(configurationService.getWorkingDirectoryPath()).isNotEmpty();
        assertThat(configurationService.getSchemeFilePath()).isNotEmpty();
        assertThat(configurationService.getInputFilePath()).isNotEmpty();
    }

}