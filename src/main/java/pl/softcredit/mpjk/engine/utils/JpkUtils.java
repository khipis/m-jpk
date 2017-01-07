package pl.softcredit.mpjk.engine.utils;

import org.slf4j.Logger;

import pl.softcredit.mpjk.JpkException;
import pl.softcredit.mpjk.core.configuration.JpkConfiguration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.io.File.separator;
import static org.apache.commons.io.FileUtils.writeStringToFile;
import static org.apache.commons.io.FilenameUtils.removeExtension;
import static org.slf4j.LoggerFactory.getLogger;
import static pl.softcredit.mpjk.engine.utils.JpkExtensions.AES_EXTENSION;
import static pl.softcredit.mpjk.engine.utils.JpkExtensions.KEY_EXTENSION;
import static pl.softcredit.mpjk.engine.utils.JpkExtensions.SHA256_EXTENSION;
import static pl.softcredit.mpjk.engine.utils.JpkExtensions.VALIDATION_EXTENSION;
import static pl.softcredit.mpjk.engine.utils.JpkExtensions.VEC_EXTENSION;
import static pl.softcredit.mpjk.engine.utils.JpkExtensions.XML_EXTENSION;
import static pl.softcredit.mpjk.engine.utils.JpkExtensions.ZIP_EXTENSION;

public class JpkUtils {

    private static final Logger LOGGER = getLogger(JpkUtils.class);

    private JpkUtils() {
    }

    public static void saveFormalValidationOutput(JpkConfiguration config, String valid)
            throws JpkException {

        File formalValidationOutputFile = new File(getPathForFormalValidation(config));
        try {
            writeStringToFile(formalValidationOutputFile, valid);
        } catch (IOException e) {
            LOGGER.error("Cannot save formal validation output file: " + getPathForFormalValidation(
                    config));
            throw new JpkException(e);
        }

    }

    public static String getPathForKeyGeneratorStage(JpkConfiguration config) {
        return removeNExtensions(getOutputPath(config), 3) + KEY_EXTENSION;
    }

    public static String getPathForShaGeneratorStage(JpkConfiguration config) {
        return removeNExtensions(getOutputPath(config), 3) + SHA256_EXTENSION;
    }

    public static String getPathForVectorGeneratorStage(JpkConfiguration config) {
        return removeNExtensions(getOutputPath(config), 3) + VEC_EXTENSION;
    }

    public static String getPathForZipStage(JpkConfiguration config) {
        return removeNExtensions(getOutputPath(config), 3) + XML_EXTENSION + ZIP_EXTENSION;
    }

    public static String getPathForAesEncryptStage(JpkConfiguration config) {
        return removeNExtensions(getOutputPath(config), 3) + XML_EXTENSION + ZIP_EXTENSION + AES_EXTENSION;
    }

    public static String getPathForAesDecryptStage(JpkConfiguration config) {
        return removeNExtensions(getOutputPath(config), 3) + XML_EXTENSION + ZIP_EXTENSION;
    }

    static String getPathForFormalValidation(JpkConfiguration config) {
        return getOutputPath(config) + VALIDATION_EXTENSION;
    }

    static String getOutputPath(JpkConfiguration config) {
        return config.getWorkingDirectoryPath() + separator + extractFileNameFromInputFilePath(
                config);
    }

    static String extractFileNameFromInputFilePath(JpkConfiguration config) {
        Path inputFilePath = Paths.get(config.getInputFilePath());
        return inputFilePath.getFileName().toString();
    }

    static String extractFileNameWithoutExtension(JpkConfiguration config) {
        Path inputFilePath = Paths.get(config.getInputFilePath());
        return removeExtension(inputFilePath.getFileName().toString());
    }

    private static String removeNExtensions(String filename, int n) {
        String filenameWithoutExtensions = filename;
        for (int i = 0; i < n; i++) {
            filenameWithoutExtensions = removeExtension(filenameWithoutExtensions);
        }
        return filenameWithoutExtensions;
    }
}
