package com.majorMedia.BackOfficeDashboard.Exception;

import com.majorMedia.BackOfficeDashboard.Exception.EmailServiceException;
import com.majorMedia.BackOfficeDashboard.Exception.InvalidEmailException;
import com.majorMedia.BackOfficeDashboard.Exception.InvalidPasswordException;
import com.majorMedia.BackOfficeDashboard.Exception.InvalidTokenException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class SystemExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({InvalidPasswordException.class, InvalidEmailException.class})
    public ResponseEntity<String> handleInvalidCredentialsException (Exception e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
    @ExceptionHandler(EmailServiceException.class)
    public ResponseEntity<String> handleEmailServiceException(EmailServiceException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
    @ExceptionHandler({InvalidTokenException.class})
        public ResponseEntity<String> handleInvalidTokenException(InvalidTokenException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handliGenericException (Exception e){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An internal server error occurred");
    }

}
