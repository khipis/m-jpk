package pl.softcredit.mpjk.engine.utils;

import org.slf4j.Logger;

import pl.softcredit.mpjk.JpkException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import static java.io.File.separator;
import static org.slf4j.LoggerFactory.getLogger;

public class JpkZip {

    private static final Logger LOGGER = getLogger(JpkZip.class);

    private JpkZip() {
    }

    public static void zipFile(String inputFilePath, String outputFilePath)
            throws JpkException, IOException {

        try (FileOutputStream fileOutputStream = new FileOutputStream(outputFilePath);
             ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream);
             FileInputStream fileInputStream = new FileInputStream(inputFilePath)) {

            ZipEntry ze = new ZipEntry(new File(inputFilePath).getName());
            zipOutputStream.putNextEntry(ze);

            writeToBuffer(zipOutputStream, fileInputStream);

            zipOutputStream.closeEntry();
        }
    }

    public static void unzipFile(String inputFilePath, String outputPath)
            throws JpkException, IOException {

        byte[] buffer = new byte[1024];

        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(inputFilePath));) {

            ZipEntry ze = zis.getNextEntry();

            while (ze != null) {

                String fileName = ze.getName();
                File newFile = new File(outputPath + separator + fileName);

                LOGGER.info("Unzipping file to: " + newFile.getAbsoluteFile());

                new File(newFile.getParent()).mkdirs();

                try (FileOutputStream fos = new FileOutputStream(newFile)) {
                    readFromBuffer(buffer, zis, fos);
                }

                ze = zis.getNextEntry();
            }

            zis.closeEntry();
        }
    }

    private static void readFromBuffer(byte[] buffer, ZipInputStream zis, FileOutputStream fos)
            throws IOException {
        int len;
        while ((len = zis.read(buffer)) > 0) {
            fos.write(buffer, 0, len);
        }
    }

    private static void writeToBuffer(ZipOutputStream zipOutputStream, FileInputStream in)
            throws IOException {
        int len;
        byte[] buffer = new byte[1024];
        while ((len = in.read(buffer)) > 0) {
            zipOutputStream.write(buffer, 0, len);
        }
    }
}
