package uj.jwzp.ticketmaster.exceptions;

import java.math.BigDecimal;

public class NotEnoughCashException extends RuntimeException {
    public NotEnoughCashException(String exception) {
        super(exception);
    }

    public NotEnoughCashException(BigDecimal currentCash, BigDecimal ticketPrice) {
        super("Your current balance (" + currentCash + ") is insufficient. Ticket price is " + ticketPrice);
    }
}
