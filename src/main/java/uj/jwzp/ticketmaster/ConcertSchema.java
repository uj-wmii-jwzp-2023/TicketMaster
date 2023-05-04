package uj.jwzp.ticketmaster;

import java.math.BigDecimal;
import java.util.List;

public record ConcertSchema(String name, List<BigDecimal> pricesList) {
}
