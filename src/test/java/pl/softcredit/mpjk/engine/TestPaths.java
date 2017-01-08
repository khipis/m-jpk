package pl.softcredit.mpjk.engine;

import static java.io.File.separator;
import static pl.softcredit.mpjk.engine.utils.JpkExtensions.ZIP_EXTENSION;

public class TestPaths {

    public static final String FILE_CONTENT = "VALID";
    public static final String TEMP_WORKING_DIR = "target/working-dir";
    public static final String SCHEMES_DIR = "src/test/resources/schemes/";
    public static final String RESOURCES_INPUT_FILES = "src/test/resources/input-files/";
    public static final String VALID_FILE_NAME = "valid_JPK_VAT(1)_file.xml";
    public static final String VALIDATION_FILE_NAME = "valid_JPK_VAT(1)_file.xml.validation";
    public static final String ZIP_FILE_NAME = "valid_JPK_VAT(1)_file.xml.zip";
    public static final String AES_FILE_NAME = "valid_JPK_VAT(1)_file.xml.zip.aes";
    public static final String MD5_FILE_NAME = "valid_JPK_VAT(1)_file.xml.zip.aes.md5";
    public static final String KEY_FILE_NAME = "valid_JPK_VAT(1)_file.key";
    public static final String SHA_FILE_NAME = "valid_JPK_VAT(1)_file.sha";
    public static final String VEC_FILE_NAME = "valid_JPK_VAT(1)_file.vec";
    public static final String INVALID_FILE = "invalid_JPK_VAT(1)_file.xml";
    public static final String JPK_VAT_SCHEME_FILE = "Schemat_JPK_VAT(1)_v1-0.xsd";
    public static final String NOT_EXISTED_FILE = "not_existed_file";
    public static final String TEMP_FILE_NAME = "tempfile.xml";
    public static final String TEMP_INPUT_FILE = TEMP_WORKING_DIR + separator + "tempfile.xml";
    public static final String VALID_FILE_PATH_FROM_RESOURCES = RESOURCES_INPUT_FILES + separator + VALID_FILE_NAME;
    public static final String AES_FILE_PATH_FROM_RESOURCES = RESOURCES_INPUT_FILES + separator + AES_FILE_NAME;
    public static final String AES_FILE_PATH_FROM_MF = RESOURCES_INPUT_FILES + separator + "JPK-VAT-TEST-0000.xml.zip.aes";
    public static final String RSA_KEY_FILE_PATH_FROM_RESOURCES = "src/test/resources/resources/JPKMFTest-klucz publiczny do szyfrowania.pem";
    public static final String AES_FILE_PATH = TEMP_WORKING_DIR + separator + AES_FILE_NAME;
    public static final String ZIPPED_FILE_PATH = TEMP_WORKING_DIR + separator + VALID_FILE_NAME + ZIP_EXTENSION;
    public static final String VALID_FILE_PATH = TEMP_WORKING_DIR + separator + VALID_FILE_NAME;
    public static final String EXCEPTION_MESSAGE = "some exception message" ;
}
