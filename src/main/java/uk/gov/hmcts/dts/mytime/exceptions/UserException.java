package uk.gov.hmcts.dts.mytime.exceptions;

public class UserException extends BaseException {
    public UserException(int httpResponseCode, String errorMessage) {
        super(httpResponseCode, errorMessage);
    }
}
