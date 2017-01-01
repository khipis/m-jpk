package pl.softcredit.mpjk.core.configuration;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static pl.softcredit.mpjk.core.configuration.ConfigurationLoader.*;

public class ConfigurationLoaderTest {

    @Test
    public void shouldLoadConfigFileFromRootWhenArgsAreNull() {
        JpkConfiguration config = load(null);
        assertThat(config).isInstanceOf(FileJpkConfiguration.class);
        assertThat(config.getConfigFilePath()).isEqualTo("config.properties");
    }

    @Test
    public void shouldLoadConfigFileFromRootWhenArgsAreEmpty() {
        JpkConfiguration config = load(new String[]{});
        assertThat(config).isInstanceOf(FileJpkConfiguration.class);
        assertThat(config.getConfigFilePath()).isEqualTo("config.properties");
    }

    @Test
    public void shouldLoadConfigFileFromArgsWhenArgsWasPassed() {
        JpkConfiguration config = load(new String[]{"passed.properties"});
        assertThat(config).isInstanceOf(FileJpkConfiguration.class);
        assertThat(config.getConfigFilePath()).isEqualTo("passed.properties");
    }
}