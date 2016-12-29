package pl.softcredit.mpjk.engine;

import pl.softcredit.mpjk.core.configuration.ConfigurationService;

public interface JpkProcessor {

    void process(ConfigurationService configurationService);
}
