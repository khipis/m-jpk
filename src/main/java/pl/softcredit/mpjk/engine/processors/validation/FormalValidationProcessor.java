package pl.softcredit.mpjk.engine.processors.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import pl.softcredit.mpjk.core.configuration.ConfigurationService;
import pl.softcredit.mpjk.engine.JpkProcessor;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;


public class FormalValidationProcessor implements JpkProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(FormalValidationProcessor.class);

    public void process(ConfigurationService configuration) {
        try {
            LOGGER.info("Input file: " + configuration.getInputFilePath());
            LOGGER.info("Used scheme: " + configuration.getSchemeFilePath());

            File schemaFile = new File(configuration.getSchemeFilePath());
            Source xmlFile = new StreamSource(new File(configuration.getInputFilePath()));

            SchemaFactory schemaFactory =
                    SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

            Schema schema = schemaFactory.newSchema(schemaFile);
            schema.newValidator().validate(xmlFile);

            LOGGER.info(xmlFile.getSystemId() + " is valid");

        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<File> getSchemesFiles(String schemesFolderPath) {
        final List<File> schemesList = new LinkedList<>();
        final File folder = new File(schemesFolderPath);
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isFile()) {
                schemesList.add(fileEntry);
            }
        }
        return schemesList;
    }
}
