package app_tup.mds.api_spa.exception.domain;

public class InvalidTokenException extends RuntimeException {

    private static final String DESCRIPTION = "Token expired";

    public InvalidTokenException(String detail){
        super(DESCRIPTION + ". " + detail);
    }

}
