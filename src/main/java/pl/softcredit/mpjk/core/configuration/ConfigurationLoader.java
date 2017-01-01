package pl.softcredit.mpjk.core.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public final class ConfigurationLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigurationLoader.class);

    private ConfigurationLoader(){
    }

    public static JpkConfiguration load(String[] args) {

        JpkConfiguration jpkConfiguration;

        if (args != null && args.length >= 1) {
            LOGGER.info("Using configuration passed by arguments: " + args[0]);
            jpkConfiguration = new FileJpkConfiguration(args[0]);
        } else if (new File("config.properties").exists()) {
            LOGGER.info("Using config.properties from app root: config.properties");
            jpkConfiguration = new FileJpkConfiguration("config.properties");
        } else {
            LOGGER.info("Cannot find config path in argument and in root path. Using default parameters for DEBUG");
            jpkConfiguration = new DefaultJpkConfiguration();

        }

        return jpkConfiguration;
    }

}
