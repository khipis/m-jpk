package pl.softcredit.mpjk.core.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.softcredit.mpjk.JpkException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class FileJpkConfiguration implements JpkConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileJpkConfiguration.class);
    private static final Properties properties = new Properties();
    private final String configFilePath;

    public FileJpkConfiguration(String configFilePath) throws JpkException {
        this.configFilePath = configFilePath;
        try (InputStream fileInputStream = new FileInputStream(configFilePath)) {
            properties.load(fileInputStream);
        } catch (IOException e) {
            LOGGER.error("Problem while loading configuration file: " + configFilePath);
            throw new JpkException(e);
        }
    }

    @Override
    public String getWorkingDirectoryPath() {
        return properties.getProperty("working.directory.path");
    }

    @Override
    public String getSchemeFilePath() {
        return properties.getProperty("scheme.file.path");
    }

    @Override
    public String getInputFilePath() {
        return properties.getProperty("input.file.path");
    }

    @Override
    public String getConfigFilePath() {
        return configFilePath;
    }

    @Override
    public String getProcessingFlow() {
        return properties.getProperty("processing.flow");
    }

    @Override
    public String getGatewayUrl() {
        return properties.getProperty("gateway.url");
    }
}
