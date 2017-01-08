package pl.softcredit.mpjk.engine.utils;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static java.io.File.separator;
import static org.apache.commons.io.FileUtils.cleanDirectory;
import static org.apache.commons.io.FileUtils.readFileToString;
import static org.assertj.core.api.Assertions.assertThat;
import static pl.softcredit.mpjk.engine.TestPaths.WORKING_DIR_PATH_IN_TARGET;
import static pl.softcredit.mpjk.engine.TestPaths.XML_FILE_NAME_SCHEME_VERSION_1;
import static pl.softcredit.mpjk.engine.TestPaths.XML_FILE_PATH_IN_RESOURCES_VERSION_1;
import static pl.softcredit.mpjk.engine.TestPaths.ZIP_FILE_PATH_IN_WORKING_DIR_VERSION_1;
import static pl.softcredit.mpjk.engine.utils.JpkZip.unzipFile;
import static pl.softcredit.mpjk.engine.utils.JpkZip.zipFile;

public class JpkZipTest {

    @Before
    public void setUp() throws IOException {
        cleanDirectory(new File(WORKING_DIR_PATH_IN_TARGET));
    }

    @Test
    public void shouldZipAndUnzipFile() throws Exception {
        zipFile(XML_FILE_PATH_IN_RESOURCES_VERSION_1, ZIP_FILE_PATH_IN_WORKING_DIR_VERSION_1);

        assertThat(new File(ZIP_FILE_PATH_IN_WORKING_DIR_VERSION_1)).exists();

        unzipFile(ZIP_FILE_PATH_IN_WORKING_DIR_VERSION_1, WORKING_DIR_PATH_IN_TARGET);

        File unzippedFile = new File(WORKING_DIR_PATH_IN_TARGET + separator + XML_FILE_NAME_SCHEME_VERSION_1);
        File fileFromResources = new File(XML_FILE_PATH_IN_RESOURCES_VERSION_1);

        assertThat(unzippedFile).exists();
        assertThat(fileFromResources).exists();

        String fileContent = readFileToString(fileFromResources);
        String unzippedFileContent = readFileToString(unzippedFile);
        assertThat(fileContent).isEqualTo(unzippedFileContent);
    }
}
