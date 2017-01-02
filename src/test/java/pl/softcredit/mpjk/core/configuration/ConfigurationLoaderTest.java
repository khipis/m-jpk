package pl.softcredit.mpjk.core.configuration;

import org.junit.Test;

import pl.softcredit.mpjk.JpkException;

import static org.assertj.core.api.Assertions.assertThat;
import static pl.softcredit.mpjk.core.configuration.ConfigurationLoader.load;

public class ConfigurationLoaderTest {

    private static final String CONFIG_PROPERTIES_FILENAME = "config.properties";

    @Test
    public void shouldLoadConfigFileFromRootWhenArgsAreNull() throws JpkException {
        JpkConfiguration config = load(null);
        assertThat(config).isInstanceOf(FileJpkConfiguration.class);
        assertThat(config.getConfigFilePath()).isEqualTo(CONFIG_PROPERTIES_FILENAME);
    }

    @Test
    public void shouldLoadConfigFileFromRootWhenArgsAreEmpty() throws JpkException {
        JpkConfiguration config = load(new String[]{});
        assertThat(config).isInstanceOf(FileJpkConfiguration.class);
        assertThat(config.getConfigFilePath()).isEqualTo(CONFIG_PROPERTIES_FILENAME);
    }

    @Test
    public void shouldLoadConfigFileFromArgsWhenArgsWasPassed() throws JpkException {
        JpkConfiguration config = load(new String[]{CONFIG_PROPERTIES_FILENAME});
        assertThat(config).isInstanceOf(FileJpkConfiguration.class);
        assertThat(config.getConfigFilePath()).isEqualTo(CONFIG_PROPERTIES_FILENAME);
    }

    @Test(expected = JpkException.class)
    public void shouldThrowJpkExceptionWhenPassedConfigFileNotExists() throws JpkException {
        JpkConfiguration config = load(new String[]{"passed.properties"});
        assertThat(config).isInstanceOf(FileJpkConfiguration.class);
        assertThat(config.getConfigFilePath()).isEqualTo("passed.properties");
    }
}