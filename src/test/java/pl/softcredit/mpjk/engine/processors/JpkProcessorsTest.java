package pl.softcredit.mpjk.engine.processors;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import pl.softcredit.mpjk.JpkException;
import pl.softcredit.mpjk.core.configuration.DefaultJpkConfiguration;
import pl.softcredit.mpjk.engine.processors.preparation.CleanWorkingDirectoryProcessor;
import pl.softcredit.mpjk.engine.processors.stage.AesEncryptStageProcessor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.rules.ExpectedException.none;
import static pl.softcredit.mpjk.engine.processors.JpkProcessors.getProcessingFlow;
import static pl.softcredit.mpjk.engine.processors.JpkProcessors.getProcessorByString;

@RunWith(MockitoJUnitRunner.class)
public class JpkProcessorsTest {

    @Rule
    public ExpectedException expectedException = none();

    @Test
    public void shouldReturnProcessorByName() throws JpkException {
        JpkProcessor result = getProcessorByString("AES_ENCRYPT");

        assertThat(result).isInstanceOf(AesEncryptStageProcessor.class);

        result = getProcessorByString("aes_ENCRYPT");

        assertThat(result).isInstanceOf(AesEncryptStageProcessor.class);

        result = getProcessorByString("clean_working_directory");

        assertThat(result).isInstanceOf(CleanWorkingDirectoryProcessor.class);
    }

    @Test
    public void shouldConstructProcessingFlowFromPassedString() throws JpkException {
        JpkProcessor[] result = getProcessingFlow(
                "CONFIG_VALIDATION,SCHEME_VALIDATION,CLEAN_WORKING_DIRECTORY,FORMAL_VALIDATION,KEY_GENERATOR,VECTOR_GENERATOR,ZIP,AES_ENCRYPT");

        assertThat(result).hasSize(8);

        result = getProcessingFlow(
                "clean_WORKING_DIRECTORY,FORMAL_VALIDATION,KEY_GENERATOR,vector_generator");

        assertThat(result).hasSize(4);
    }

    @Test
    public void shouldConstructEveryProcessorFromDefaultConfig() throws JpkException {
        getProcessingFlow(new DefaultJpkConfiguration().getProcessingFlow());
    }

    @Test
    public void shouldThrowJpkExceptionWhenProcessorNameIsInvalid() throws JpkException {
        expectedException.expect(JpkException.class);
        expectedException.expectMessage("There is no processor with name: INVALID_PROCESSOR_NAME");

        getProcessorByString("INVALID_PROCESSOR_NAME");
    }

    @Test
    public void shouldThrowJpkExceptionWhenOneOfProcessorsIsInvalid() throws JpkException {
        expectedException.expect(JpkException.class);
        expectedException.expectMessage("There is no processor with name: INVALID_PROCESSOR_NAME");

        getProcessingFlow("CONFIG_VALIDATION,invalid_processor_name");
    }

}