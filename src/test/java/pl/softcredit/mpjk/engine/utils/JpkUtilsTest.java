package pl.softcredit.mpjk.engine.utils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import pl.softcredit.mpjk.core.configuration.JpkConfiguration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.security.Key;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.List;

import javax.xml.crypto.dsig.SignatureMethod;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import xades4j.production.DataObjectReference;
import xades4j.production.SignedDataObjects;
import xades4j.production.XadesBesSigningProfile;
import xades4j.production.XadesSigner;
import xades4j.production.XadesSigningProfile;
import xades4j.properties.DataObjectTransform;
import xades4j.providers.KeyingDataProvider;
import xades4j.providers.SigningCertChainException;
import xades4j.providers.SigningKeyException;
import xades4j.providers.impl.DefaultAlgorithmsProvider;
import xades4j.verification.UnexpectedJCAException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static pl.softcredit.mpjk.engine.TestPaths.AES_FILE_PATH_FROM_MF_IN_RESOURCES_VERSION_1;
import static pl.softcredit.mpjk.engine.TestPaths.AES_FILE_PATH_FROM_MF_IN_RESOURCES_VERSION_2;
import static pl.softcredit.mpjk.engine.TestPaths.AES_FILE_PATH_IN_RESOURCES_VERSION_1;
import static pl.softcredit.mpjk.engine.TestPaths.AES_FILE_PATH_IN_RESOURCES_VERSION_2;
import static pl.softcredit.mpjk.engine.TestPaths.INVALID_SCHEME_PATH_IN_RESOURCES_VERSION_1;
import static pl.softcredit.mpjk.engine.TestPaths.INVALID_SCHEME_PATH_IN_RESOURCES_VERSION_2;
import static pl.softcredit.mpjk.engine.TestPaths.JPK_VAT_SCHEME_FILE_NAME_PATH_IN_RESOURCES_VERSION_2;
import static pl.softcredit.mpjk.engine.TestPaths.TEMP_FILE_NAME;
import static pl.softcredit.mpjk.engine.TestPaths.WORKING_DIR_PATH_IN_TARGET;
import static pl.softcredit.mpjk.engine.TestPaths.XML_FILE_FROM_MF_IN_RESOURCES_VERSION_1;
import static pl.softcredit.mpjk.engine.TestPaths.XML_FILE_FROM_MF_IN_RESOURCES_VERSION_2;
import static pl.softcredit.mpjk.engine.TestPaths.XML_FILE_PATH_IN_RESOURCES_VERSION_1;
import static pl.softcredit.mpjk.engine.TestPaths.JPK_VAT_SCHEME_FILE_NAME_PATH_IN_RESOURCES_VERSION_1;
import static pl.softcredit.mpjk.engine.TestPaths.XML_FILE_PATH_IN_RESOURCES_VERSION_2;
import static pl.softcredit.mpjk.engine.TestPaths.ZIP_FILE_PATH_FROM_MF_IN_RESOURCES_VERSION_1;
import static pl.softcredit.mpjk.engine.TestPaths.ZIP_FILE_PATH_FROM_MF_IN_RESOURCES_VERSION_2;
import static pl.softcredit.mpjk.engine.utils.JpkUtils.extractFileNameFromInputFilePath;
import static pl.softcredit.mpjk.engine.utils.JpkUtils.extractFileNameWithoutExtension;
import static pl.softcredit.mpjk.engine.utils.JpkUtils.getContentLength;
import static pl.softcredit.mpjk.engine.utils.JpkUtils.getOutputPath;
import static pl.softcredit.mpjk.engine.utils.JpkUtils.getPathForAesDecryptStage;
import static pl.softcredit.mpjk.engine.utils.JpkUtils.getPathForAesEncryptStage;
import static pl.softcredit.mpjk.engine.utils.JpkUtils.getPathForFormalValidation;
import static pl.softcredit.mpjk.engine.utils.JpkUtils.getPathForKeyGeneratorStage;
import static pl.softcredit.mpjk.engine.utils.JpkUtils.getPathForKeyRsaEncryptStage;
import static pl.softcredit.mpjk.engine.utils.JpkUtils.getPathForMd5GeneratorStage;
import static pl.softcredit.mpjk.engine.utils.JpkUtils.getPathForShaGeneratorStage;
import static pl.softcredit.mpjk.engine.utils.JpkUtils.getPathForVectorGeneratorStage;
import static pl.softcredit.mpjk.engine.utils.JpkUtils.getPathForZipStage;
import static pl.softcredit.mpjk.engine.utils.JpkUtils.validateScheme;


@RunWith(MockitoJUnitRunner.class)
public class JpkUtilsTest {

    @Mock
    private JpkConfiguration config;

    @Before
    public void setUp() {
        when(config.getWorkingDirectoryPath()).thenReturn(WORKING_DIR_PATH_IN_TARGET);
        when(config.getInputFilePath()).thenReturn(TEMP_FILE_NAME);
    }

    @Test
    public void shouldReturnValidWhenSchemeIsValid() throws Exception {
        String result = validateScheme(JPK_VAT_SCHEME_FILE_NAME_PATH_IN_RESOURCES_VERSION_1);

        assertThat(result).isEqualTo("VALID");

        result = validateScheme(JPK_VAT_SCHEME_FILE_NAME_PATH_IN_RESOURCES_VERSION_2);

        assertThat(result).isEqualTo("VALID");
    }

    @Test
    public void shouldReturnValidationResultWhenSchemeIsInvalid() throws Exception {
        String result = validateScheme(INVALID_SCHEME_PATH_IN_RESOURCES_VERSION_1);

        assertThat(result).isEqualTo(
                "org.xml.sax.SAXParseException; systemId: file:"
                + "/G:/work/m-jpk/src/test/resources/schemes/invalidScheme.xsd; lineNumber: 3; columnNumber: 3;"
                + " The element type \"xsd:schema\" must be terminated by the matching end-tag \"</xsd:schema>\"."
        );

        result = validateScheme(INVALID_SCHEME_PATH_IN_RESOURCES_VERSION_2);

        assertThat(result).isEqualTo(
                "org.xml.sax.SAXParseException; systemId: file:"
                + "/G:/work/m-jpk/src/test/resources/schemes/invalidScheme.xsd; lineNumber: 3; columnNumber: 3;"
                + " The element type \"xsd:schema\" must be terminated by the matching end-tag \"</xsd:schema>\"."
        );
    }

    @Test
    public void shouldGetOutputPathForFormalValidation() throws Exception {
        String result = getPathForFormalValidation(config);

        assertThat(result).isEqualTo("target/working-dir\\tempfile.xml.validation");
    }

    @Test
    public void shouldGetOutputPathForKeyGeneratorStage() throws Exception {
        String result = getPathForKeyGeneratorStage(config);

        assertThat(result).isEqualTo("target/working-dir\\tempfile.key");
    }

    @Test
    public void shouldGetOutputPathForShaGeneratorStage() throws Exception {
        String result = getPathForShaGeneratorStage(config);

        assertThat(result).isEqualTo("target/working-dir\\tempfile.sha");
    }

    @Test
    public void shouldGetOutputPathForMd5GeneratorStage() throws Exception {
        when(config.getInputFilePath()).thenReturn("target/working-dir\\tempfile.xml.zip.aes");

        String result = getPathForMd5GeneratorStage(config);

        assertThat(result).isEqualTo("target/working-dir\\tempfile.xml.zip.aes.md5");
    }

    @Test
    public void shouldGetOutputPathForKeyRsaEncryptStage() throws Exception {
        String result = getPathForKeyRsaEncryptStage(config);

        assertThat(result).isEqualTo("target/working-dir\\tempfile.key.rsa.base64");
    }

    @Test
    public void shouldGetOutputPathForKeyGeneratorStageFromAesFile() throws Exception {
        when(config.getInputFilePath()).thenReturn("target/working-dir\\tempfile.xml.zip.aes");

        String result = getPathForKeyGeneratorStage(config);

        assertThat(result).isEqualTo("target/working-dir\\tempfile.key");
    }

    @Test
    public void shouldGetOutputPathForVectorGeneratorStageFromAesFile() throws Exception {
        when(config.getInputFilePath()).thenReturn("target/working-dir\\tempfile.xml.zip.aes");

        String result = getPathForVectorGeneratorStage(config);

        assertThat(result).isEqualTo("target/working-dir\\tempfile.vec");
    }

    @Test
    public void shouldGetOutputPathForVectorGeneratorStage() throws Exception {
        String result = getPathForVectorGeneratorStage(config);

        assertThat(result).isEqualTo("target/working-dir\\tempfile.vec");
    }

    @Test
    public void shouldGetOutputPathForZipStage() throws Exception {
        String result = getPathForZipStage(config);

        assertThat(result).isEqualTo("target/working-dir\\tempfile.xml.zip");
    }

    @Test
    public void shouldGetOutputPathForAesEncryptStage() throws Exception {
        String result = getPathForAesEncryptStage(config);

        assertThat(result).isEqualTo("target/working-dir\\tempfile.xml.zip.aes");
    }

    @Test
    public void shouldRemoveAesExtension() throws Exception {
        when(config.getInputFilePath()).thenReturn("target/working-dir\\tempfile.xml.zip.aes");

        String result = getPathForAesDecryptStage(config);

        assertThat(result).isEqualTo("target/working-dir\\tempfile.xml.zip");
    }

    @Test
    public void shouldExtractFileName() throws Exception {
        String result = extractFileNameFromInputFilePath(config);

        assertThat(result).isEqualTo("tempfile.xml");
    }

    @Test
    public void shouldExtractFileNameWithoutExtension() throws Exception {
        String result = extractFileNameWithoutExtension(config);

        assertThat(result).isEqualTo("tempfile");
    }

    @Test
    public void shouldGetOutputPath() throws Exception {
        String result = getOutputPath(config);

        assertThat(result).isEqualTo("target/working-dir\\tempfile.xml");
    }

    @Test
    public void shouldGetContentLengthForVersion1() throws Exception {
        long result = getContentLength(new File(AES_FILE_PATH_FROM_MF_IN_RESOURCES_VERSION_1));
        assertThat(result).isEqualTo(800L);

        result = getContentLength(new File(AES_FILE_PATH_IN_RESOURCES_VERSION_1));
        assertThat(result).isEqualTo(816L);

        result = getContentLength(new File(ZIP_FILE_PATH_FROM_MF_IN_RESOURCES_VERSION_1));
        assertThat(result).isEqualTo(797L);

        result = getContentLength(new File(XML_FILE_PATH_IN_RESOURCES_VERSION_1));
        assertThat(result).isEqualTo(1393L);

        result = getContentLength(new File(XML_FILE_FROM_MF_IN_RESOURCES_VERSION_1));
        assertThat(result).isEqualTo(1393L);
    }

    @Test
    public void shouldGetContentLengthForVersion2() throws Exception {
        long result = getContentLength(new File(AES_FILE_PATH_FROM_MF_IN_RESOURCES_VERSION_2));
        assertThat(result).isEqualTo(1856L);

        result = getContentLength(new File(AES_FILE_PATH_IN_RESOURCES_VERSION_2));
        assertThat(result).isEqualTo(1856L);

        result = getContentLength(new File(ZIP_FILE_PATH_FROM_MF_IN_RESOURCES_VERSION_2));
        assertThat(result).isEqualTo(1850L);

        result = getContentLength(new File(XML_FILE_PATH_IN_RESOURCES_VERSION_2));
        assertThat(result).isEqualTo(21102L);

        result = getContentLength(new File(XML_FILE_FROM_MF_IN_RESOURCES_VERSION_2));
        assertThat(result).isEqualTo(21102L);
    }
    @Test
    public void shouldGetOutputPat222h() throws Exception {
        try {
            String alias = "alias do klucza i certu";
            char haslo[] = "haslo".toCharArray();

            // zaladowanie pliki p12
            KeyStore store = KeyStore.getInstance("PKCS12");
            store.load(new FileInputStream("sciezka do pliku .p12"), haslo);

            //            Enumeration<String> aliases = store.aliases();
            //            while (aliases.hasMoreElements()) {
            //                String a = aliases.nextElement();
            //                System.out.println(a);
            //            }

            // wyciagnij klucz prywatny
            Key privKey = store.getKey(alias, haslo);
            final PrivateKey privateKey = (PrivateKey) privKey;

            // wyciagnij certyfikat
            final X509Certificate cert = (X509Certificate) store.getCertificate(alias);

            // provider dla xades
            KeyingDataProvider keyingDataProv = new KeyingDataProvider() {

                public List<X509Certificate> getSigningCertificateChain()
                        throws SigningCertChainException, UnexpectedJCAException {
                    return (List<X509Certificate>) (Object) Arrays.asList(cert);
                }

                public PrivateKey getSigningKey(X509Certificate signingCert)
                        throws SigningKeyException, UnexpectedJCAException {
                    return privateKey;
                }
            };

            // plik xml do podpisania
            String inputFile = XML_FILE_FROM_MF_IN_RESOURCES_VERSION_1;
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setNamespaceAware(true);
            DocumentBuilder db = dbf.newDocumentBuilder();
            org.w3c.dom.Document inDoc = db.parse(new FileInputStream(inputFile));

            // profil podpisu xades
            XadesSigningProfile profile = new XadesBesSigningProfile(keyingDataProv)
                    .withAlgorithmsProvider(MyAlgsProvider.class);

            // zmienna podpisujaca
            XadesSigner signer = profile.newSigner();

            // dodatkowe dane do podpisu
            SignedDataObjects dataObjs = new SignedDataObjects();
            DataObjectReference obj = new DataObjectReference("");
            obj.withTransform(new DataObjectTransform(
                    "http://www.w3.org/2000/09/xmldsig#enveloped-signature"));
            dataObjs.withSignedDataObject(obj);

            // podpisanie
            signer.sign(dataObjs, inDoc.getDocumentElement());

            // Zapis podpisanego pliku
            String outputFile = "G:\\eee.xml";
            OutputStream os = new FileOutputStream(outputFile);
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer trans = tf.newTransformer();
            trans.transform(new DOMSource(inDoc), new StreamResult(os));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    class MyAlgsProvider extends DefaultAlgorithmsProvider {

        @Override
        public String getSignatureAlgorithm(String keyAlgorithmName) {
            return SignatureMethod.RSA_SHA1;
        }
    }


}
