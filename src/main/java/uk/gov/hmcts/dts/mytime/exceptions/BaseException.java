package uk.gov.hmcts.dts.mytime.exceptions;

public class BaseException extends Exception {

    private final int httpResponseCode;
    public static final long serialVersionUID = 42L;

    public int getHttpResponseCode() {
        return httpResponseCode;
    }

    public BaseException(int httpResponseCode, String errorMessage) {
        super(errorMessage);
        this.httpResponseCode = httpResponseCode;
    }
}
