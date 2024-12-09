package com.majorMedia.BackOfficeDashboard.exception;

import com.majorMedia.BackOfficeDashboard.model.responses.ErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class SystemExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({InvalidPasswordException.class,
                        NotFoundEmailException.class,
                        })
    public ResponseEntity<String> handleInvalidCredentialsException (Exception e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
    @ExceptionHandler({
            EntityNotFoundException.class})
    public ResponseEntity<String> handleEntityNotFoundException (Exception e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
    @ExceptionHandler({InvalidTokenException.class,
                        AlreadyExistEmailException.class , AlreadyExistRoleException.class , AlreadyExistsEntityException.class})
        public ResponseEntity<String> handleInvalidTokenException(Exception e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
    @ExceptionHandler({InvalidRoleException.class})
    public ResponseEntity<String> handleInvalidRoleException(InvalidRoleException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
    @ExceptionHandler({InvalidCurrentPasswordException.class})
    public ResponseEntity<String> handleInvalidCurrentPasswordException(InvalidCurrentPasswordException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<String> handleAccessDeniedException(AccessDeniedException e){
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
    }
    @ExceptionHandler(EmailServiceException.class)
    public ResponseEntity<String> handleEmailServiceException(EmailServiceException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRoleAssignment(RuntimeException e){
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(AccountDeactivatedException.class)
    public ResponseEntity<String> AccountDeactivated(AccountDeactivatedException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> IllegalArgumentException(IllegalArgumentException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {

        List<String> details = new ArrayList<>();
        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            details.add(error.getDefaultMessage());
        }
        ErrorResponse error = new ErrorResponse("Validation Failed",HttpStatus.BAD_REQUEST, details);
        return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
    }
}
