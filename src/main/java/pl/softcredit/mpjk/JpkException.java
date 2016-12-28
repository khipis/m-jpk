package pl.softcredit.mpjk;

public class JpkException extends Exception {

    private String message = null;

    public JpkException() {
        super();
    }

    public JpkException(String message) {
        super(message);
        this.message = message;
    }

    public JpkException(Throwable cause) {
        super(cause);
    }

    @Override
    public String toString() {
        return message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
