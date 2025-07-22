package app_tup.mds.api_spa.exception.infrastructure;

import app_tup.mds.api_spa.exception.domain.*;
import app_tup.mds.api_spa.util.dto.StandardResponse;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import java.security.SignatureException;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({
            NotFoundException.class,
            TypeNotPresentException.class,
            EntityNotFoundException.class,
    })
    @ResponseBody
    public ResponseEntity<StandardResponse<?>> handleNotFound(HttpServletRequest request, Exception exception) {

        ErrorDetails error = ErrorDetails.builder()
                .message(exception.getClass().getSimpleName())
                .details(exception.getMessage())
                .path(request.getRequestURI())
                .build();

        StandardResponse<?> response = StandardResponse.builder()
                .success(false)
                .message("Something went wrong")
                .error(error)
                .status(HttpStatus.NOT_FOUND.value())
                .build();

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
            BadRequestException.class,
            InvalidTokenException.class,
            HttpRequestMethodNotSupportedException.class,
            MethodArgumentNotValidException.class,
            MissingRequestHeaderException.class,
            MissingServletRequestParameterException.class,
            MethodArgumentTypeMismatchException.class,
            HttpMessageNotReadableException.class,
    })
    @ResponseBody
    public ResponseEntity<StandardResponse<?>> handleBadRequest(HttpServletRequest request, Exception exception) {

        ErrorDetails error = ErrorDetails.builder()
                .message(exception.getClass().getSimpleName())
                .details(exception.getMessage())
                .path(request.getRequestURI())
                .build();

        StandardResponse<?> response = StandardResponse.builder()
                .success(false)
                .message("Something went wrong")
                .error(error)
                .status(HttpStatus.BAD_REQUEST.value())
                .build();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler({
            ExpiredJwtException.class,
            MalformedJwtException.class,
            SignatureException.class
    })
    @ResponseBody
    public ResponseEntity<StandardResponse<?>> handleUnauthorized(HttpServletRequest request, Exception exception) {

        ErrorDetails error = ErrorDetails.builder()
                .message(exception.getClass().getSimpleName())
                .details(exception.getMessage())
                .path(request.getRequestURI())
                .build();

        StandardResponse<?> response = StandardResponse.builder()
                .success(false)
                .message("Something went wrong")
                .error(error)
                .status(HttpStatus.UNAUTHORIZED.value())
                .build();

        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);

    }

}
