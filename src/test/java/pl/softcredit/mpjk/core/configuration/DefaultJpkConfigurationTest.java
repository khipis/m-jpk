package pl.softcredit.mpjk.core.configuration;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DefaultJpkConfigurationTest {

    private final JpkConfiguration jpkConfiguration = new DefaultJpkConfiguration();

    @Test
    public void shouldLoadConfigurationFile() {
        assertThat(jpkConfiguration.getWorkingDirectoryPath()).isNotEmpty();
        assertThat(jpkConfiguration.getSchemeFilePath()).isNotEmpty();
        assertThat(jpkConfiguration.getInputFilePath()).isNotEmpty();
    }

}