package pl.softcredit.mpjk.engine;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import pl.softcredit.mpjk.JpkException;
import pl.softcredit.mpjk.core.configuration.DefaultJpkConfiguration;
import pl.softcredit.mpjk.core.configuration.JpkConfiguration;
import pl.softcredit.mpjk.engine.processors.JpkProcessor;
import pl.softcredit.mpjk.engine.processors.preparation.CleanWorkingDirectoryProcessor;
import pl.softcredit.mpjk.engine.processors.validation.ConfigParametersValidationProcessor;
import pl.softcredit.mpjk.engine.processors.validation.FormalValidationProcessor;
import pl.softcredit.mpjk.engine.processors.validation.SchemeValidationProcessor;

import static org.junit.rules.ExpectedException.none;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static pl.softcredit.mpjk.engine.TestDummies.EXCEPTION_MESSAGE;

@RunWith(MockitoJUnitRunner.class)
public class JpkExecutorTest {

    @Rule
    public ExpectedException expectedException = none();

    @Mock
    private ConfigParametersValidationProcessor configParametersValidationProcessor;

    @Mock
    private CleanWorkingDirectoryProcessor cleanWorkingDirectoryProcessor;

    @Mock
    private FormalValidationProcessor formalValidationProcessor;

    @Mock
    private SchemeValidationProcessor schemeValidationProcessor;

    private JpkConfiguration config = new DefaultJpkConfiguration();

    private JpkExecutor jpkExecutor = new JpkExecutor(config);

    @Test
    public void shouldExecuteEveryPassedProcessor() throws JpkException {
        executeAllProcessors();

        verify(configParametersValidationProcessor).process(config);
        verify(cleanWorkingDirectoryProcessor).process(config);
        verify(formalValidationProcessor).process(config);
        verify(schemeValidationProcessor).process(config);
    }

    @Test
    public void shouldStopProcessingAfterExceptionInConfigParametersProcessor() throws JpkException {
        throwExceptionOnSpecificProcessor(configParametersValidationProcessor);

        executeAllProcessors();

        verify(configParametersValidationProcessor).process(config);

        verifyNoMoreInteractions(cleanWorkingDirectoryProcessor,
                                 formalValidationProcessor,
                                 schemeValidationProcessor);
    }

    @Test
    public void shouldStopProcessingAfterExceptionInCleanWorkingDirectoryProcessor() throws JpkException {
        throwExceptionOnSpecificProcessor(cleanWorkingDirectoryProcessor);

        executeAllProcessors();

        verify(configParametersValidationProcessor).process(config);
        verify(cleanWorkingDirectoryProcessor).process(config);

        verifyNoMoreInteractions(formalValidationProcessor,
                                 schemeValidationProcessor);
    }

    @Test
    public void shouldStopProcessingAfterExceptionInFormalValidationProcessor() throws JpkException {
        throwExceptionOnSpecificProcessor(formalValidationProcessor);

        executeAllProcessors();

        verify(configParametersValidationProcessor).process(config);
        verify(cleanWorkingDirectoryProcessor).process(config);
        verify(formalValidationProcessor).process(config);

        verifyNoMoreInteractions(schemeValidationProcessor);
    }

    @Test
    public void shouldStopProcessingAfterExceptionSchemeValidationProcessor() throws JpkException {
        throwExceptionOnSpecificProcessor(schemeValidationProcessor);

        executeAllProcessors();

        verify(configParametersValidationProcessor).process(config);
        verify(cleanWorkingDirectoryProcessor).process(config);
        verify(formalValidationProcessor).process(config);
        verify(schemeValidationProcessor).process(config);

        verifyZeroInteractions();
    }

    private void throwExceptionOnSpecificProcessor(JpkProcessor processor) throws JpkException {
        doThrow(new JpkException(EXCEPTION_MESSAGE))
                .when(processor).process(config);
        expectedException.expect(JpkException.class);
        expectedException.expectMessage(EXCEPTION_MESSAGE);
    }

    private void executeAllProcessors() throws JpkException {
        jpkExecutor.execute(configParametersValidationProcessor,
                            cleanWorkingDirectoryProcessor,
                            formalValidationProcessor,
                            schemeValidationProcessor);
    }
}