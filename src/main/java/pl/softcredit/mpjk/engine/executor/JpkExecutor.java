package pl.softcredit.mpjk.engine.executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.softcredit.mpjk.core.configuration.ConfigurationService;
import pl.softcredit.mpjk.engine.processors.JpkProcessor;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableList;

public class JpkExecutor {

    private static final Logger LOGGER = LoggerFactory.getLogger(JpkExecutor.class);
    private final ConfigurationService configurationService;

    public JpkExecutor(ConfigurationService configurationService) {
        this.configurationService = configurationService;

    }

    public void execute(JpkProcessor... processors) {
        List<JpkProcessor> processorsCopy = unmodifiableList(asList(processors));
        for (JpkProcessor processor : processorsCopy) {
            LOGGER.info(processors.getClass().getSimpleName());
            processor.process(configurationService);
        }
    }

}
