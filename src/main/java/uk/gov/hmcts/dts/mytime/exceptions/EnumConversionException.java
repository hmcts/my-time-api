package uk.gov.hmcts.dts.mytime.exceptions;

public class EnumConversionException extends RuntimeException {
    private static final long serialVersionUID = -4058030206218307741L;

    public EnumConversionException(String message) {
        super(message);
    }
}
