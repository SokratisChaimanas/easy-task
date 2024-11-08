package gr.myprojects.easytask.core;

import gr.myprojects.easytask.core.exceptions.EntityAlreadyExistsException;
import gr.myprojects.easytask.core.exceptions.EntityInvalidArgumentException;
import gr.myprojects.easytask.core.exceptions.EntityNotFoundException;
import gr.myprojects.easytask.core.exceptions.GenericException;
import gr.myprojects.easytask.dto.ExceptionDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(GenericException.class)
    public ResponseEntity<ExceptionDTO> handleGenericException(GenericException e) {
        ExceptionDTO exceptionDTO = new ExceptionDTO(e.getCODE(), e.getMessage());
        HttpStatus status;

        if (e instanceof EntityInvalidArgumentException) {
            status = HttpStatus.BAD_REQUEST;
        } else if (e instanceof EntityNotFoundException) {
            status = HttpStatus.NOT_FOUND;
        } else if (e instanceof EntityAlreadyExistsException) {
            status = HttpStatus.CONFLICT;
        } else {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return ResponseEntity.status(status).body(exceptionDTO);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionDTO> handleException(Exception e) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        if (e instanceof BadCredentialsException) {
            status = HttpStatus.BAD_REQUEST;
        }
        ExceptionDTO exceptionDTO = new ExceptionDTO(e.getClass().getSimpleName(), e.getMessage());

        return ResponseEntity.status(status).body(exceptionDTO);
    }
}
