package pl.softcredit.mpjk.engine.utils;

public enum JpkExtensions {
    AES_EXTENSION(".aes"),
    ZIP_EXTENSION(".zip"),
    XADES_EXTENSION(".xades"),
    KEY_EXTENSION(".key"),
    VEC_EXTENSION(".vec"),
    VALIDATION_EXTENSION(".validation")
    ;

    private final String extension;

    JpkExtensions(String extension){
        this.extension = extension;
    }

    @Override
    public String toString(){
        return extension;
    }
}
