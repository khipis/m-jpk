package pl.softcredit.mpjk.engine.executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.softcredit.mpjk.core.configuration.JpkConfiguration;
import pl.softcredit.mpjk.engine.processors.JpkProcessor;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableList;

public class JpkExecutor {

    private static final Logger LOGGER = LoggerFactory.getLogger(JpkExecutor.class);
    private final JpkConfiguration fileJpkConfiguration;

    public JpkExecutor(JpkConfiguration fileJpkConfiguration) {
        this.fileJpkConfiguration = fileJpkConfiguration;

    }

    public void execute(JpkProcessor... processors) {
        List<JpkProcessor> processorsCopy = unmodifiableList(asList(processors));
        LOGGER.info("Processors to execute: " + processorsCopy.size());
        for (JpkProcessor processor : processorsCopy) {
            processor.process(fileJpkConfiguration);
        }
    }

}
