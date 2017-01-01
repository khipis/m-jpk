package pl.softcredit.mpjk.core.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public final class DefaultJpkConfiguration implements JpkConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultJpkConfiguration.class);
    private static final Properties properties = new Properties();

    @Override
    public String getWorkingDirectoryPath() {
        return "working-dir";
    }

    @Override
    public String getSchemeFilePath() {
        return "Schemat_JPK_VAT(1)_v1-0.xsd";
    }

    @Override
    public String getInputFilePath() {
        return "JPK-VAT-TEST-0000.xml";
    }

}
