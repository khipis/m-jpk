package pl.softcredit.mpjk.engine.utils;

import org.junit.Before;
import org.junit.Test;

import pl.softcredit.mpjk.JpkException;

import java.io.File;
import java.io.IOException;

import static java.io.File.separator;
import static org.apache.commons.io.FileUtils.cleanDirectory;
import static org.apache.commons.io.FileUtils.readFileToString;
import static org.assertj.core.api.Assertions.assertThat;
import static pl.softcredit.mpjk.engine.TestPaths.WORKING_DIR_PATH_IN_TARGET;
import static pl.softcredit.mpjk.engine.TestPaths.XML_FILE_NAME_SCHEME_VERSION_1;
import static pl.softcredit.mpjk.engine.TestPaths.XML_FILE_NAME_SCHEME_VERSION_2;
import static pl.softcredit.mpjk.engine.TestPaths.XML_FILE_PATH_IN_RESOURCES_VERSION_1;
import static pl.softcredit.mpjk.engine.TestPaths.XML_FILE_PATH_IN_RESOURCES_VERSION_2;
import static pl.softcredit.mpjk.engine.TestPaths.ZIP_FILE_PATH_IN_WORKING_DIR_VERSION_1;
import static pl.softcredit.mpjk.engine.TestPaths.ZIP_FILE_PATH_IN_WORKING_DIR_VERSION_2;
import static pl.softcredit.mpjk.engine.utils.JpkZip.unzipFile;
import static pl.softcredit.mpjk.engine.utils.JpkZip.zipFile;

public class JpkZipTest {

    @Before
    public void setUp() throws IOException {
        cleanDirectory(new File(WORKING_DIR_PATH_IN_TARGET));
    }

    @Test
    public void shouldZipAndUnzipFileForVersion1() throws Exception {
        zipAndUnzipFile(XML_FILE_PATH_IN_RESOURCES_VERSION_1, ZIP_FILE_PATH_IN_WORKING_DIR_VERSION_1);

        File unzippedFile = new File(WORKING_DIR_PATH_IN_TARGET + separator + XML_FILE_NAME_SCHEME_VERSION_1);
        File fileFromResources = new File(XML_FILE_PATH_IN_RESOURCES_VERSION_1);

        assertUnzippedFileContent(unzippedFile, fileFromResources);
    }

    @Test
    public void shouldZipAndUnzipFileForVersion2() throws Exception {
        zipAndUnzipFile(XML_FILE_PATH_IN_RESOURCES_VERSION_2, ZIP_FILE_PATH_IN_WORKING_DIR_VERSION_2);

        File unzippedFile = new File(WORKING_DIR_PATH_IN_TARGET + separator + XML_FILE_NAME_SCHEME_VERSION_2);
        File fileFromResources = new File(XML_FILE_PATH_IN_RESOURCES_VERSION_2);

        assertUnzippedFileContent(unzippedFile, fileFromResources);
    }

    private void zipAndUnzipFile(String inputFile, String outputFile) throws JpkException, IOException {
        zipFile(inputFile, outputFile);

        assertThat(new File(outputFile)).exists();

        unzipFile(outputFile, WORKING_DIR_PATH_IN_TARGET);
    }

    private void assertUnzippedFileContent(File unzippedFile, File fileFromResources)
            throws IOException {
        assertThat(unzippedFile).exists();
        assertThat(fileFromResources).exists();

        String fileContent = readFileToString(fileFromResources);
        String unzippedFileContent = readFileToString(unzippedFile);
        assertThat(fileContent).isEqualTo(unzippedFileContent);
    }
}
