package uj.jwzp.ticketmaster.config;

import java.time.Clock;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import uj.jwzp.ticketmaster.ampq.LogAmqpSender;
import uj.jwzp.ticketmaster.entities.Ticket;
import uj.jwzp.ticketmaster.repositories.TicketRepository;

@Configuration
@EnableScheduling
public class ScheduleConfig {
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private Clock clock;

    // private final Logger logger = LoggerFactory.getLogger(ScheduleConfig.class);

    @Autowired
    LogAmqpSender logAmqpSender;

    AtomicInteger count = new AtomicInteger(0);
    @Scheduled(fixedRate = 2000)
    public void scheduleTestHeartbeat() {
        logAmqpSender.debug(ScheduleConfig.class, "Heartbeat " + count.incrementAndGet());
    }

    @Scheduled(fixedRate = 10000)
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

        // logger.info("Deleted " + deletedTickets + " tickets");
        logAmqpSender.info(ScheduleConfig.class, "Deleted " + deletedTickets + " tickets");
    }

    
}
