package pl.softcredit.mpjk.core.configuration;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DefaultJpkConfigurationTest {

    private final JpkConfiguration jpkConfiguration = new DefaultJpkConfiguration();

    @Test
    public void shouldLoadConfigurationFile() {
        assertThat(jpkConfiguration.getWorkingDirectoryPath()).isEqualTo("working-dir");
        assertThat(jpkConfiguration.getSchemeFilePath()).isEqualTo("schemes/Schemat_JPK_VAT(1)_v1-0.xsd");
        assertThat(jpkConfiguration.getInputFilePath()).isEqualTo("input-files/JPK-VAT-TEST-0000.xml");
        assertThat(jpkConfiguration.getProcessingFlow()).isEqualTo("CONFIG_PARAMETERS_VALIDATION,SCHEME_VALIDATION,CLEAN_WORKING_DIRECTORY,FORMAL_VALIDATION,KEY_GENERATOR,VECTOR_GENERATOR_STAGE,ZIP_STAGE,AES_ENCRYPT_STAGE,AES_DECRYPT");
        assertThat(jpkConfiguration.getGatewayUrl()).isEqualTo("https://test-e-dokumenty.mf.gov.pl/");
    }

}
