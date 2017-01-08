package pl.softcredit.mpjk.engine;

import java.io.File;
import java.io.IOException;

import static java.io.File.separator;
import static org.assertj.core.api.Assertions.assertThat;
import static pl.softcredit.mpjk.engine.utils.JpkExtensions.AES_EXTENSION;
import static pl.softcredit.mpjk.engine.utils.JpkExtensions.BASE64_EXTENSION;
import static pl.softcredit.mpjk.engine.utils.JpkExtensions.KEY_EXTENSION;
import static pl.softcredit.mpjk.engine.utils.JpkExtensions.MD5_EXTENSION;
import static pl.softcredit.mpjk.engine.utils.JpkExtensions.RSA_EXTENSION;
import static pl.softcredit.mpjk.engine.utils.JpkExtensions.SHA256_EXTENSION;
import static pl.softcredit.mpjk.engine.utils.JpkExtensions.VALIDATION_EXTENSION;
import static pl.softcredit.mpjk.engine.utils.JpkExtensions.VEC_EXTENSION;
import static pl.softcredit.mpjk.engine.utils.JpkExtensions.XML_EXTENSION;
import static pl.softcredit.mpjk.engine.utils.JpkExtensions.ZIP_EXTENSION;

public class TestPaths {

    //Directories
    public static final String SCHEMES_DIR_PATH_IN_TEST_RESOURCES = "src/test/resources/schemes/";
    public static final String INPUT_FILES_DIR_PATH_IN_TEST_RESOURCES = "src/test/resources/input-files/";
    public static final String RESOURCES_DIR_PATH_IN_TEST_RESOURCES = "src/test/resources/resources/";
    public static final String WORKING_DIR_PATH_IN_TARGET = "target/working-dir";

    //Common
    public static final String VALID_FILE_CONTENT = "VALID";
    public static final String INVALID_SCHEME_NAME = "invalidScheme.xsd";
    public static final String NOT_EXISTING_FILE_NAME = "not_existed_file";
    public static final String TEMP_FILE_NAME = "tempfile.xml";
    public static final String TEMP_INPUT_FILE = WORKING_DIR_PATH_IN_TARGET + separator + "tempfile.xml";
    public static final String INVALID_XML_FILE = "invalid_JPK_VAT(1)_file.xml";
    
    //File names valid for scheme version 1 Schemat_JPK_VAT(1)_v1-0.xsd
    public static final String VALID_FILE_NAME_WITHOUT_EXTENSION_VERSION_1 = "valid_JPK_VAT(1)_file";
    public static final String XML_FILE_NAME_SCHEME_VERSION_1 = VALID_FILE_NAME_WITHOUT_EXTENSION_VERSION_1 + XML_EXTENSION;
    public static final String ZIP_FILE_NAME_SCHEME_VERSION_1 = XML_FILE_NAME_SCHEME_VERSION_1 + ZIP_EXTENSION;
    public static final String AES_FILE_NAME_SCHEME_VERSION_1 = XML_FILE_NAME_SCHEME_VERSION_1 + ZIP_EXTENSION + AES_EXTENSION;
    public static final String MD5_FILE_NAME_SCHEME_VERSION_1 = AES_FILE_NAME_SCHEME_VERSION_1 + MD5_EXTENSION;
    public static final String KEY_FILE_NAME_SCHEME_VERSION_1 = VALID_FILE_NAME_WITHOUT_EXTENSION_VERSION_1 + KEY_EXTENSION;
    public static final String RSA_KEY_FILE_NAME_SCHEME_VERSION_1 = KEY_FILE_NAME_SCHEME_VERSION_1 + RSA_EXTENSION + BASE64_EXTENSION;
    public static final String SHA_FILE_NAME_SCHEME_VERSION_1 = VALID_FILE_NAME_WITHOUT_EXTENSION_VERSION_1 + SHA256_EXTENSION;
    public static final String VEC_FILE_NAME_SCHEME_VERSION_1 = VALID_FILE_NAME_WITHOUT_EXTENSION_VERSION_1 + VEC_EXTENSION;
    public static final String VEC_FILE_NAME_BASE64_SCHEME_VERSION_1 = VEC_FILE_NAME_SCHEME_VERSION_1 + BASE64_EXTENSION;
    public static final String VALIDATION_FILE_NAME_SCHEME_VERSION_1 = XML_FILE_NAME_SCHEME_VERSION_1 + VALIDATION_EXTENSION;


    //Scheme files
    public static final String JPK_VAT_SCHEME_FILE_NAME_VERSION_1 = "Schemat_JPK_VAT(1)_v1-0.xsd";
    public static final String JPK_VAT_SCHEME_FILE_NAME_VERSION_2 = "Schemat_JPK_VAT(2)_v1-0.xsd";

    //File full paths valid for scheme version 1 Schemat_JPK_VAT(1)_v1-0.xsd
    public static final String XML_FILE_PATH_IN_RESOURCES_VERSION_1 = INPUT_FILES_DIR_PATH_IN_TEST_RESOURCES + separator + XML_FILE_NAME_SCHEME_VERSION_1;
    public static final String XML_FILE_FROM_MF_IN_RESOURCES_VERSION_1 = INPUT_FILES_DIR_PATH_IN_TEST_RESOURCES + separator + "JPK-VAT-TEST-0000.xml";
    public static final String AES_FILE_PATH_IN_RESOURCES_VERSION_1 = INPUT_FILES_DIR_PATH_IN_TEST_RESOURCES + separator + AES_FILE_NAME_SCHEME_VERSION_1;
    public static final String AES_FILE_PATH_FROM_MF_IN_RESOURCES_VERSION_1 = INPUT_FILES_DIR_PATH_IN_TEST_RESOURCES + "JPK-VAT-TEST-0000.xml.zip.aes";
    public static final String ZIP_FILE_PATH_FROM_MF_IN_RESOURCES_VERSION_1 = INPUT_FILES_DIR_PATH_IN_TEST_RESOURCES + "JPK-VAT-TEST-0000.xml.zip";
    public static final String RSA_KEY_FILE_PATH_IN_RESOURCES_VERSION_1 = RESOURCES_DIR_PATH_IN_TEST_RESOURCES + "JPKMFTest-klucz publiczny do szyfrowania.pem";
    public static final String JPK_VAT_SCHEME_FILE_NAME_VERSION_1_PATH_IN_RESOURCES_VERSION_1 = SCHEMES_DIR_PATH_IN_TEST_RESOURCES + separator + JPK_VAT_SCHEME_FILE_NAME_VERSION_1;
    public static final String INVALID_SCHEME_PATH_IN_RESOURCES_VERSION_1 = SCHEMES_DIR_PATH_IN_TEST_RESOURCES + separator + INVALID_SCHEME_NAME;

    //Working directory in target generated files paths valid for version 1 Schemat_JPK_VAT(1)_v1-0.xsd
    public static final String AES_FILE_PATH_IN_WORKING_DIR_VERSION_1 = WORKING_DIR_PATH_IN_TARGET + separator + AES_FILE_NAME_SCHEME_VERSION_1;
    public static final String ZIP_FILE_PATH_IN_WORKING_DIR_VERSION_1 = WORKING_DIR_PATH_IN_TARGET + separator + XML_FILE_NAME_SCHEME_VERSION_1 + ZIP_EXTENSION;
    public static final String RSA_KEY_FILE_PATH_IN_WORKING_DIR_VERSION_1 = WORKING_DIR_PATH_IN_TARGET + separator + KEY_FILE_NAME_SCHEME_VERSION_1 + RSA_EXTENSION;
    public static final String XML_FILE_PATH_IN_WORKING_DIR_VERSION_1 = WORKING_DIR_PATH_IN_TARGET + separator + XML_FILE_NAME_SCHEME_VERSION_1;


    public static void assertFile(String inputFile) throws IOException {
        File createdFile = getValidatedFile(inputFile);
        assertThat(createdFile).exists();
    }

    private static File getValidatedFile(String inputFile) {
        return new File(WORKING_DIR_PATH_IN_TARGET + separator + inputFile);
    }
}
