package uj.jwzp.ticketmaster.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import uj.jwzp.ticketmaster.entities.Ticket;
import uj.jwzp.ticketmaster.repositories.TicketRepository;


import java.time.Clock;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Configuration
@EnableScheduling
public class Config {
    @Autowired
    private TicketRepository ticketRepository;
    private final Clock clock = Clock.systemUTC();
    @Bean
    public Clock clock() {
        return Clock.systemUTC();
    }

    @Scheduled(fixedRate = 100000)
    public void scheduleFixedRateTask() {
        List<Ticket> ticketList = ticketRepository.findAll();
        LocalDateTime localTime = LocalDateTime.now(clock);

        for (Ticket ticket : ticketList) {
            if (ticket.getPurchasedAt() == null && Duration.between(ticket.getReservedAt(), localTime).toMinutes() > 5) {
                ticketRepository.delete(ticket);
            }
        }
    }
}
