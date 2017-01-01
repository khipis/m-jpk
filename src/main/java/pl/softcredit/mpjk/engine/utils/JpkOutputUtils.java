package pl.softcredit.mpjk.engine.utils;

import org.slf4j.Logger;

import pl.softcredit.mpjk.core.configuration.JpkConfiguration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.io.File.separator;
import static org.apache.commons.io.FileUtils.writeStringToFile;
import static org.slf4j.LoggerFactory.getLogger;
import static pl.softcredit.mpjk.engine.utils.JpkExtensions.VALIDATION_EXTENSION;

public class JpkOutputUtils {

    private static final Logger LOGGER = getLogger(JpkOutputUtils.class);

    public static void saveFormalValidationOutput(JpkConfiguration config, String valid) {

        File formalValidationOutputFile = new File(getOutputPathForFormalValidation(config));
        try {
            writeStringToFile(formalValidationOutputFile, valid);
        } catch (IOException e) {
            LOGGER.error("Cannot save formal validation output file: " + getOutputPathForFormalValidation(config), e);
        }

    }

    public static String getOutputPathForFormalValidation(JpkConfiguration config) {
        return getOutputPath(config) + VALIDATION_EXTENSION;
    }

    public static String getOutputPath(JpkConfiguration config) {
        return config.getWorkingDirectoryPath() + separator +  extractFileNameFromInputFilePath(config);
    }

    public static String extractFileNameFromInputFilePath(JpkConfiguration config) {
        Path p = Paths.get(config.getInputFilePath());
        return p.getFileName().toString();
    }

    private JpkOutputUtils(){
    }
}
