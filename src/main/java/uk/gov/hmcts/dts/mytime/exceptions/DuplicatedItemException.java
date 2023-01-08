package uk.gov.hmcts.dts.mytime.exceptions;

public class DuplicatedItemException extends RuntimeException {
    private static final long serialVersionUID = -2918854284717604L;

    public DuplicatedItemException(String message) {
        super(message);
    }
}
