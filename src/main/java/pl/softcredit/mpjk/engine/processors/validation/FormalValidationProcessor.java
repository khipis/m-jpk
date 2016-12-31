package pl.softcredit.mpjk.engine.processors.validation;

import org.slf4j.Logger;
import org.xml.sax.SAXException;

import pl.softcredit.mpjk.core.configuration.ConfigurationService;
import pl.softcredit.mpjk.engine.processors.JpkProcessor;

import java.io.File;
import java.io.IOException;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import static javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI;
import static javax.xml.validation.SchemaFactory.newInstance;
import static org.slf4j.LoggerFactory.getLogger;

public class FormalValidationProcessor implements JpkProcessor {

    private static final Logger LOGGER = getLogger(FormalValidationProcessor.class);

    public void process(ConfigurationService config) {
        try {
            LOGGER.info("Input file: " + config.getInputFilePath());
            LOGGER.info("Used scheme: " + config.getSchemeFilePath());

            File schemaFile = new File(config.getSchemeFilePath());
            Source xmlFile = new StreamSource(new File(config.getInputFilePath()));

            SchemaFactory schemaFactory = newInstance(W3C_XML_SCHEMA_NS_URI);

            Schema schema = schemaFactory.newSchema(schemaFile);
            schema.newValidator().validate(xmlFile);

            LOGGER.info(xmlFile.getSystemId() + " is valid");

        } catch (SAXException e) {
            LOGGER.error(e.toString());
        } catch (IOException e) {
            LOGGER.error("Problem while reading scheme file: " + config.getSchemeFilePath(), e);
        }
    }
}
