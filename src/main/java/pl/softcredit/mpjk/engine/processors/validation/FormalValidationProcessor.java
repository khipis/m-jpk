package pl.softcredit.mpjk.engine.processors.validation;

import pl.softcredit.mpjk.engine.JpkProcessor;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;


public class FormalValidationProcessor implements JpkProcessor {

    public void process(String inputFile, String outputFile) {

        try {
            URL schemaFile = new URL("http://java.sun.com/xml/ns/j2ee/web-app_2_4.xsd");
            // or File schemaFile = new File("/location/to/xsd") etc.
            Source xmlFile = new StreamSource(new File(inputFile));
            SchemaFactory schemaFactory =
                    SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

            Schema schema = schemaFactory.newSchema(schemaFile);

            Validator validator = schema.newValidator();

            validator.validate(xmlFile);
            System.out.println(xmlFile.getSystemId() + " is valid");
        } catch (org.xml.sax.SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
