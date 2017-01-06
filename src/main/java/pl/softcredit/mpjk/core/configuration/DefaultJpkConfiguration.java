package pl.softcredit.mpjk.core.configuration;

public final class DefaultJpkConfiguration implements JpkConfiguration {

    @Override
    public String getWorkingDirectoryPath() {
        return "working-dir";
    }

    @Override
    public String getSchemeFilePath() {
        return "schemes/Schemat_JPK_VAT(1)_v1-0.xsd";
    }

    @Override
    public String getInputFilePath() {
        return "input-files/JPK-VAT-TEST-0000.xml";
    }

    @Override
    public String getConfigFilePath() {
        return "";
    }

    @Override
    public String getProcessingFlow() {
        return "CONFIG_PARAMETERS_VALIDATION,SCHEME_VALIDATION,CLEAN_WORKING_DIRECTORY,FORMAL_VALIDATION,KEY_GENERATOR,VECTOR_GENERATOR_STAGE,ZIP_STAGE,AES_ENCRYPT_STAGE,AES_DECRYPT";
    }

}
