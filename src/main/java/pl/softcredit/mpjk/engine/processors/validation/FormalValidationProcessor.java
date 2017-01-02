package pl.softcredit.mpjk.engine.processors.validation;

import org.slf4j.Logger;
import org.xml.sax.SAXException;

import pl.softcredit.mpjk.JpkException;
import pl.softcredit.mpjk.core.configuration.JpkConfiguration;
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
import static pl.softcredit.mpjk.engine.utils.JpkOutputUtils.saveFormalValidationOutput;

public class FormalValidationProcessor implements JpkProcessor {

    private static final Logger LOGGER = getLogger(FormalValidationProcessor.class);

    @Override
    public void process(JpkConfiguration config) throws JpkException {
        try {

            LOGGER.info("Input file: " + config.getInputFilePath());
            LOGGER.info("Used scheme: " + config.getSchemeFilePath());

            File schemaFile = new File(config.getSchemeFilePath());
            Source xmlFile = new StreamSource(new File(config.getInputFilePath()));

            SchemaFactory schemaFactory = newInstance(W3C_XML_SCHEMA_NS_URI);

            Schema schema = schemaFactory.newSchema(schemaFile);
            schema.newValidator().validate(xmlFile);

            LOGGER.info(xmlFile.getSystemId() + " is valid");
            saveFormalValidationOutput(config, "VALID");
        } catch (SAXException e) {
            saveFormalValidationOutput(config, e.toString());
            throw new JpkException(e);
        } catch (IOException e) {
            LOGGER.error("Problem while reading scheme file: " + config.getSchemeFilePath());
        }
    }
}
