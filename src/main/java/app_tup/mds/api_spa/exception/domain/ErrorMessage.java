package app_tup.mds.api_spa.exception.domain;

import lombok.Data;

import java.util.Map;

@Data
public class ErrorMessage {

    private String exception;
    private String message;
    private String path;
    //private Map<String,String> errors;

    public ErrorMessage(Exception exception, String path){
        this.exception = exception.getClass().getSimpleName();
        this.message = exception.getMessage();
        this.path = path;
    }

    public ErrorMessage(Exception exception, String path, Map<String,String> errors){
        this.exception = exception.getClass().getSimpleName();
        this.message = exception.getMessage();
        //this.errors = errors;
        this.path = path;
    }

}
