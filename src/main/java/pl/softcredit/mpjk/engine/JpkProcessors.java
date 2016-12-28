package pl.softcredit.mpjk.engine;

import pl.softcredit.mpjk.engine.processors.validation.FormalValidationProcessor;

public final class JpkProcessors {

    public static final JpkProcessor FORMAL_VALIDATION_PROCESSOR = new FormalValidationProcessor();

    private JpkProcessors() {
    }

}
