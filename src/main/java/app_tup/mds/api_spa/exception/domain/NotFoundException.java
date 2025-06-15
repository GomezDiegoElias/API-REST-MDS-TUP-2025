package app_tup.mds.api_spa.exception.domain;

public class NotFoundException extends RuntimeException {

    private static final String DESCRIPTION = "Not found Exception ";

    public NotFoundException(String detail){
        super(DESCRIPTION + ". " + detail);
    }

}
