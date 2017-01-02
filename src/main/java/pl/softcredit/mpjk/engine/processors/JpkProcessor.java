package pl.softcredit.mpjk.engine.processors;

import pl.softcredit.mpjk.JpkException;
import pl.softcredit.mpjk.core.configuration.JpkConfiguration;

public interface JpkProcessor {

    void process(JpkConfiguration jpkConfiguration) throws JpkException;
}
