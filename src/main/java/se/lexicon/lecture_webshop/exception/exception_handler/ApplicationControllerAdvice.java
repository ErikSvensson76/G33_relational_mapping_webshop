package se.lexicon.lecture_webshop.exception.exception_handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import se.lexicon.lecture_webshop.dto.GeneralExceptionResponse;
import se.lexicon.lecture_webshop.exception.AppResourceNotFoundException;

import java.time.LocalDateTime;

@ControllerAdvice
public class ApplicationControllerAdvice {


    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<GeneralExceptionResponse> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request){
        GeneralExceptionResponse response = new GeneralExceptionResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.name(),
                ex.getMessage(),
                request.getDescription(false)
        );

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler({AppResourceNotFoundException.class})
    public ResponseEntity<GeneralExceptionResponse> handleAppResourceNotFoundException(AppResourceNotFoundException ex, WebRequest request){
        GeneralExceptionResponse response = new GeneralExceptionResponse(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.name(),
                ex.getMessage(),
                request.getDescription(false)
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }





}
