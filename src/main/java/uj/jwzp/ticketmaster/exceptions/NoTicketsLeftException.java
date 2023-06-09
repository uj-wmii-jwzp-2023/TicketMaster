package uj.jwzp.ticketmaster.exceptions;

public class NoTicketsLeftException extends RuntimeException {
    public NoTicketsLeftException(String exception) {
        super(exception);
    }

    public NoTicketsLeftException(long locationZoneId) {
        super("No tickets left in zone " + locationZoneId);
    }
}
