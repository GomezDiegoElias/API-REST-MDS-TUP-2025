package app_tup.mds.api_spa.exception.domain;

public class MalformedHeaderException extends RuntimeException {

    private static final String DESCRIPTION = "Token with wrong format";

    public MalformedHeaderException(String detail){
        super(DESCRIPTION + ". " + detail);
    }

}
