package uj.jwzp.ticketmaster.exceptions;

public class TicketPurchaseException extends RuntimeException {
    public TicketPurchaseException(String exception) {
        super(exception);
    }

    public TicketPurchaseException() {
        super("Your reservation expired or you did not make one");
    }
}
