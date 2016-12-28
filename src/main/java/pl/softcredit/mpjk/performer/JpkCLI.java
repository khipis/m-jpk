package pl.softcredit.mpjk.performer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.softcredit.mpjk.JpkException;
import pl.softcredit.mpjk.processors.JpkProcessor;
import pl.softcredit.mpjk.processors.validation.FormalValidationProcessor;


public class JpkCLI {

    final static Logger log = LoggerFactory.getLogger(JpkCLI.class);


    public JpkCLI(JpkProcessor... processors) throws JpkException {

        for (JpkProcessor processor : processors) {

            processor.process("", "");
        }
    }


    public static void main(String[] args) {
        try {
            JpkCLI auditPerformer = new JpkCLI(new FormalValidationProcessor());
        } catch (JpkException e) {
            log.error("Cannot perform audit: " + e.getMessage());
        }
    }

}
