package pl.softcredit.mpjk.engine.processors.validation;

import org.slf4j.Logger;
import org.xml.sax.SAXException;

import pl.softcredit.mpjk.JpkException;
import pl.softcredit.mpjk.core.configuration.JpkConfiguration;
import pl.softcredit.mpjk.engine.processors.JpkProcessor;

import java.io.File;

import javax.xml.validation.SchemaFactory;

import static javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI;
import static javax.xml.validation.SchemaFactory.newInstance;
import static org.slf4j.LoggerFactory.getLogger;

public class SchemeValidationProcessor implements JpkProcessor {

    private static final Logger LOGGER = getLogger(SchemeValidationProcessor.class);

    @Override
    public void process(JpkConfiguration config) throws JpkException {
        try {
            File schemaFile = new File(config.getSchemeFilePath());
            SchemaFactory schemaFactory = newInstance(W3C_XML_SCHEMA_NS_URI);
            schemaFactory.newSchema(schemaFile);
        } catch (SAXException e) {
            LOGGER.error("Scheme is invalid: " + config.getSchemeFilePath());
            throw new JpkException(e);
        }
    }
}
