package pl.softcredit.mpjk.engine;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import pl.softcredit.mpjk.core.configuration.DefaultJpkConfiguration;
import pl.softcredit.mpjk.core.configuration.JpkConfiguration;
import pl.softcredit.mpjk.engine.processors.JpkProcessor;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class JpkExecutorTest {

    @Mock
    private JpkProcessor processor1;
    @Mock
    private JpkProcessor processor2;

    private JpkConfiguration config = new DefaultJpkConfiguration();

    private JpkExecutor jpkExecutor = new JpkExecutor(config);

    @Test
    public void shouldExecuteEveryPassedProcessor() {
        jpkExecutor.execute(processor1, processor2);

        verify(processor1).process(config);
        verify(processor2).process(config);
    }

}