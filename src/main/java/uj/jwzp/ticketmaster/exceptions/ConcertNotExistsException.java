package uj.jwzp.ticketmaster.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ConcertNotExistsException extends RuntimeException{
    public ConcertNotExistsException(String exception){
        super(exception);
    }
}
