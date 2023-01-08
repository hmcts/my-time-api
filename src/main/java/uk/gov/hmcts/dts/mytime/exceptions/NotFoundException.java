package uk.gov.hmcts.dts.mytime.exceptions;

public class NotFoundException extends RuntimeException {
    private static final long serialVersionUID = 8794731308148202263L;

    public NotFoundException(String message) {
        super(message);
    }
}
