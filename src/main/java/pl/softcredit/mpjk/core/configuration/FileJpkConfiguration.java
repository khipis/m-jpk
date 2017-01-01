package pl.softcredit.mpjk.core.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class FileJpkConfiguration implements JpkConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileJpkConfiguration.class);
    private static final Properties properties = new Properties();

    public FileJpkConfiguration(String configFilePath) {
        try(InputStream fileInputStream = new FileInputStream(configFilePath)) {
            properties.load(fileInputStream);
        } catch (IOException e) {
            LOGGER.error("Problem while loading configuration file: " + configFilePath);
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

}