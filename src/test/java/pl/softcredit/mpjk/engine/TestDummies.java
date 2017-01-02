package pl.softcredit.mpjk.engine;

import static java.io.File.separator;

public class TestDummies {

    public static final String FILE_CONTENT = "VALID";
    public static final String TEMP_WORKING_DIR = "target/working-dir";
    public static final String SCHEMES_DIR = "src/test/resources/schemes/";
    public static final String INPUT_FILES_DIR = "src/test/resources/input-files/";
    public static final String VALID_FILE = "valid_JPK_VAT(1)_file.xml";
    public static final String INVALID_FILE = "invalid_JPK_VAT(1)_file.xml";
    public static final String JPK_VAT_SCHEME_FILE = "Schemat_JPK_VAT(1)_v1-0.xsd";
    public static final String NOT_EXISTED_FILE = "not_existed_file";
    public static final String TEMP_FILE_NAME = "tempfile.xml";
    public static final String TEMP_INPUT_FILE = TEMP_WORKING_DIR + separator + "tempfile.xml";
    public static final String EXCEPTION_MESSAGE = "some exception message" ;
}
