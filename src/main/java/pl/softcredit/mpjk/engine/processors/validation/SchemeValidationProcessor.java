package pl.softcredit.mpjk.engine.processors.validation;

import org.slf4j.Logger;

import pl.softcredit.mpjk.JpkException;
import pl.softcredit.mpjk.core.configuration.JpkConfiguration;
import pl.softcredit.mpjk.engine.processors.JpkProcessor;

import static org.slf4j.LoggerFactory.getLogger;
import static pl.softcredit.mpjk.engine.utils.JpkUtils.VALID_CONTENT;
import static pl.softcredit.mpjk.engine.utils.JpkUtils.validateScheme;

public class SchemeValidationProcessor implements JpkProcessor {

    private static final Logger LOGGER = getLogger(SchemeValidationProcessor.class);

    @Override
    public void process(JpkConfiguration config) throws JpkException {

        String result = validateScheme(config.getSchemeFilePath());

        if (!VALID_CONTENT.equals(result)) {
            LOGGER.error("Scheme is invalid: " + config.getSchemeFilePath());
            throw new JpkException(result);
        }
    }
}
