package pl.softcredit.mpjk.engine.utils;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static java.io.File.separator;
import static org.apache.commons.io.FileUtils.cleanDirectory;
import static org.apache.commons.io.FileUtils.readFileToString;
import static org.assertj.core.api.Assertions.assertThat;
import static pl.softcredit.mpjk.engine.TestDummies.TEMP_WORKING_DIR;
import static pl.softcredit.mpjk.engine.TestDummies.VALID_FILE_NAME;
import static pl.softcredit.mpjk.engine.TestDummies.VALID_FILE_PATH_FROM_RESOURCES;
import static pl.softcredit.mpjk.engine.TestDummies.ZIPPED_FILE_PATH;
import static pl.softcredit.mpjk.engine.utils.JpkZip.unzipFile;
import static pl.softcredit.mpjk.engine.utils.JpkZip.zipFile;

public class JpkZipTest {

    @Before
    public void setUp() throws IOException {
        cleanDirectory(new File(TEMP_WORKING_DIR));
    }

    @Test
    public void shouldZipAndUnzipFile() throws Exception {
        zipFile(VALID_FILE_PATH_FROM_RESOURCES, ZIPPED_FILE_PATH);

        assertThat(new File(ZIPPED_FILE_PATH)).exists();

        unzipFile(ZIPPED_FILE_PATH, TEMP_WORKING_DIR);

        File unzippedFile = new File(TEMP_WORKING_DIR + separator + VALID_FILE_NAME);

        assertThat(unzippedFile).exists();

        String fileContent = readFileToString(new File(VALID_FILE_PATH_FROM_RESOURCES));
        String unzippedFileContent = readFileToString(unzippedFile);
        assertThat(fileContent).isEqualTo(unzippedFileContent);

        System.out.println(fileContent);
        System.out.println(unzippedFileContent);
    }
}
