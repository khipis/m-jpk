package pl.softcredit.mpjk.core.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class ConfigurationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigurationService.class);
    private static final Properties properties = new Properties();

    public ConfigurationService(String configFilePath) {
        try(InputStream fileInputStream = new FileInputStream(configFilePath)) {
            properties.load(fileInputStream);
        } catch (IOException e) {
            LOGGER.error("Problem while loading configuration file: " + configFilePath);
        }
    }

    public String getWorkingDirectoryPath() {
        return properties.getProperty("working.directory.path");
    }

    public String getSchemeFilePath() {
        return properties.getProperty("scheme.file.path");
    }

    public String getInputFilePath() {
        return properties.getProperty("input.file.path");
    }

}
