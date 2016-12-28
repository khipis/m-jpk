package pl.softcredit.mpjk.processors;

import pl.softcredit.mpjk.processors.validation.FormalValidationProcessor;

public final class JpkProcessors {

    public static final JpkProcessor FORMAL_VALIDATION_PROCESSOR = new FormalValidationProcessor();

    private JpkProcessors() {
    }

}
