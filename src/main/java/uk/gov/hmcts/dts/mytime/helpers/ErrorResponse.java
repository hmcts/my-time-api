package uk.gov.hmcts.dts.mytime.helpers;

public class ErrorResponse {
    private final int errorCode;
    private final String message;

    public int getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }

    public ErrorResponse(int code, String message) {
        this.errorCode = code;
        this.message = message;
    }
}
