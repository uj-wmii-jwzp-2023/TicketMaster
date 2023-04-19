package uj.jwzp.ticketmaster.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class LocationAlreadyExistsException extends  RuntimeException {
    public LocationAlreadyExistsException(String exception) {
        super(exception);
    }
}
