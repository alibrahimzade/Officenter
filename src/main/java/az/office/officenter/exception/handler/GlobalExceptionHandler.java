package az.office.officenter.exception.handler;

import az.office.officenter.dto.ExceptionDto;
import az.office.officenter.exception.UserAlreadyExistException;
import az.office.officenter.exception.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(UserNotFoundException.class)
    public ExceptionDto handle(UserNotFoundException exception) {
        log.error("not found: ", exception);
        return new ExceptionDto(NOT_FOUND.value(), exception.getMessage());
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    public ExceptionDto handle(UserAlreadyExistException exception) {
        log.error("ClientException: ", exception);
        return new ExceptionDto(CONFLICT.value(), exception.getMessage());
    }
}
