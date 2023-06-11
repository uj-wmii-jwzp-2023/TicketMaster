package uj.jwzp.ticketmaster.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
public class ScheduleConfig {
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private Clock clock;
    private final Logger logger = LoggerFactory.getLogger(ScheduleConfig.class);

    @Scheduled(fixedRate = 100000)
    public void scheduleFixedRateTask() {
        List<Ticket> ticketList = ticketRepository.findAll();
        LocalDateTime localTime = LocalDateTime.now(clock);

        int deletedTickets = 0;

        for (Ticket ticket : ticketList) {
            if (ticket.getPurchasedAt() == null && Duration.between(ticket.getReservedAt(), localTime).toMinutes() > 5) {
                ticketRepository.delete(ticket);
                deletedTickets++;
            }
        }

        logger.info("Deleted " + deletedTickets + " tickets");
    }
}
