package pl.softcredit.mpjk.core.configuration;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DefaultJpkConfigurationTest {

    private final JpkConfiguration jpkConfiguration = new DefaultJpkConfiguration();

    @Test
    public void shouldLoadConfigurationFile() {
        assertThat(jpkConfiguration.getWorkingDirectoryPath()).isEqualTo("working-dir");
        assertThat(jpkConfiguration.getSchemeFilePath()).isEqualTo("schemes/Schemat_JPK_VAT(2)_v1-0.xsd");
        assertThat(jpkConfiguration.getInputFilePath()).isEqualTo("input-files/JPK_VAT_002.xml");
        assertThat(jpkConfiguration.getProcessingFlow()).isEqualTo("CONFIG_VALIDATION,SCHEME_VALIDATION,CLEAN_WORKING_DIRECTORY,FORMAL_VALIDATION,SHA256_GENERATOR,KEY_GENERATOR,VECTOR_GENERATOR,ZIP,AES_ENCRYPT,MD5_GENERATOR,AES_DECRYPT,RSA_ENCRYPT");
        assertThat(jpkConfiguration.getGatewayUrl()).isEqualTo("https://test-e-dokumenty.mf.gov.pl/");
        assertThat(jpkConfiguration.getRsaKeyPath()).isEqualTo("resources/JPKMFTest-klucz publiczny do szyfrowania.pem");
    }

}
