package pl.softcredit.mpjk.engine.processors.stage;

import org.slf4j.Logger;

import pl.softcredit.mpjk.JpkException;
import pl.softcredit.mpjk.core.configuration.JpkConfiguration;
import pl.softcredit.mpjk.engine.processors.JpkProcessor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static org.slf4j.LoggerFactory.getLogger;
import static pl.softcredit.mpjk.engine.utils.JpkOutputUtils.getPathForZipStage;

public class ZipStageProcessor implements JpkProcessor {

    private static final Logger LOGGER = getLogger(ZipStageProcessor.class);

    @Override
    public void process(JpkConfiguration config) throws JpkException {

        String zipFileOutputPath = getPathForZipStage(config);
        LOGGER.info("Zipping file to: " + zipFileOutputPath);

        try (FileOutputStream fileOutputStream = new FileOutputStream(zipFileOutputPath);
             ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream);
             FileInputStream fileInputStream = new FileInputStream(config.getInputFilePath())) {

            ZipEntry ze = new ZipEntry(new File(config.getInputFilePath()).getName());
            zipOutputStream.putNextEntry(ze);

            writeToBuffer(zipOutputStream, fileInputStream);

            zipOutputStream.closeEntry();
        } catch (IOException e) {
            LOGGER.error("Problem while zipping file");
            throw new JpkException(e);
        }
    }

    private void writeToBuffer(ZipOutputStream zipOutputStream, FileInputStream in)
            throws IOException {
        int len;
        byte[] buffer = new byte[1024];
        while ((len = in.read(buffer)) > 0) {
            zipOutputStream.write(buffer, 0, len);
        }
    }
}
