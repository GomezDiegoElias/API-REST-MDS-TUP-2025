package app_tup.mds.api_spa.exception.domain;

public class BadRequestException extends RuntimeException {

    private static final String DESCRIPTION = "Bad Request Exception (400)";

    public BadRequestException(String detail){
        super(DESCRIPTION + ". " + detail);
    }

}
