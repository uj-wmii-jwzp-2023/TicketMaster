package uj.jwzp.ticketmaster.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserAlreadyExistsException extends RuntimeException{
    public UserAlreadyExistsException(String exception) {
        super(exception);
    }
}
