package pl.softcredit.mpjk.engine.processors;

import pl.softcredit.mpjk.core.configuration.ConfigurationService;

public interface JpkProcessor {

    void process(ConfigurationService configurationService);
}
