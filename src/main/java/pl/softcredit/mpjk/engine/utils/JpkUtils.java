package pl.softcredit.mpjk.engine.utils;

import org.slf4j.Logger;
import org.xml.sax.SAXException;

import pl.softcredit.mpjk.JpkException;
import pl.softcredit.mpjk.core.configuration.JpkConfiguration;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.xml.validation.SchemaFactory;

import static java.io.File.separator;
import static javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI;
import static javax.xml.validation.SchemaFactory.newInstance;
import static org.apache.commons.io.FilenameUtils.removeExtension;
import static org.slf4j.LoggerFactory.getLogger;
import static pl.softcredit.mpjk.engine.utils.JpkExtensions.AES_EXTENSION;
import static pl.softcredit.mpjk.engine.utils.JpkExtensions.KEY_EXTENSION;
import static pl.softcredit.mpjk.engine.utils.JpkExtensions.MD5_EXTENSION;
import static pl.softcredit.mpjk.engine.utils.JpkExtensions.RSA_EXTENSION;
import static pl.softcredit.mpjk.engine.utils.JpkExtensions.SHA256_EXTENSION;
import static pl.softcredit.mpjk.engine.utils.JpkExtensions.VALIDATION_EXTENSION;
import static pl.softcredit.mpjk.engine.utils.JpkExtensions.VEC_EXTENSION;
import static pl.softcredit.mpjk.engine.utils.JpkExtensions.XML_EXTENSION;
import static pl.softcredit.mpjk.engine.utils.JpkExtensions.ZIP_EXTENSION;

public class JpkUtils {

    private static final Logger LOGGER = getLogger(JpkUtils.class);
    private static final int EXTENSIONS_TO_REMOVE_COUNT = 3;

    private JpkUtils() {
    }

    public static String validateScheme(String schemeFilePath) throws JpkException {
        try {
            File schemaFile = new File(schemeFilePath);
            SchemaFactory schemaFactory = newInstance(W3C_XML_SCHEMA_NS_URI);
            schemaFactory.newSchema(schemaFile);
            return "VALID";
        } catch (SAXException e) {
            return e.toString();
        }
    }

    public static String validateXmlFileAgainstScheme(String inputFile, String schemeFilePath)  {
        return "VALID";
    }

    public static String getPathForKeyGeneratorStage(JpkConfiguration config) {
        return removeNExtensions(getOutputPath(config), EXTENSIONS_TO_REMOVE_COUNT) + KEY_EXTENSION;
    }

    public static long getContentLength(File file) {
        return file.length();
    }

    public static String getPathForShaGeneratorStage(JpkConfiguration config) {
        return removeNExtensions(getOutputPath(config), EXTENSIONS_TO_REMOVE_COUNT) + SHA256_EXTENSION;
    }

    public static String getPathForMd5GeneratorStage(JpkConfiguration config) {
        return removeNExtensions(getOutputPath(config), EXTENSIONS_TO_REMOVE_COUNT)
               + XML_EXTENSION
               + ZIP_EXTENSION
               + AES_EXTENSION
               + MD5_EXTENSION;
    }

    public static String getPathForKeyRsaEncryptStage(JpkConfiguration config) {
        return removeNExtensions(getOutputPath(config), EXTENSIONS_TO_REMOVE_COUNT) + KEY_EXTENSION + RSA_EXTENSION;
    }

    public static String getPathForVectorRsaEncryptStage(JpkConfiguration config) {
        return removeNExtensions(getOutputPath(config), EXTENSIONS_TO_REMOVE_COUNT) + VEC_EXTENSION + RSA_EXTENSION;
    }

    public static String getPathForVectorGeneratorStage(JpkConfiguration config) {
        return removeNExtensions(getOutputPath(config), EXTENSIONS_TO_REMOVE_COUNT) + VEC_EXTENSION;
    }

    public static String getPathForZipStage(JpkConfiguration config) {
        return removeNExtensions(getOutputPath(config), EXTENSIONS_TO_REMOVE_COUNT) + XML_EXTENSION + ZIP_EXTENSION;
    }

    public static String getPathForAesEncryptStage(JpkConfiguration config) {
        return removeNExtensions(getOutputPath(config), EXTENSIONS_TO_REMOVE_COUNT) + XML_EXTENSION + ZIP_EXTENSION
               + AES_EXTENSION;
    }

    public static String getPathForAesDecryptStage(JpkConfiguration config) {
        return removeNExtensions(getOutputPath(config), EXTENSIONS_TO_REMOVE_COUNT) + XML_EXTENSION + ZIP_EXTENSION;
    }

    public static String getPathForFormalValidation(JpkConfiguration config) {
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
