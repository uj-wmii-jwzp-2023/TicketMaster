package uj.jwzp.ticketmaster.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import uj.jwzp.ticketmaster.entities.Location;
import uj.jwzp.ticketmaster.entities.User;
import uj.jwzp.ticketmaster.repositories.LocationRepository;
import uj.jwzp.ticketmaster.repositories.UserRepository;

@Configuration
public class Config {
    private static final Logger log = LoggerFactory.getLogger(Config.class);
    @Autowired
    private PasswordEncoder passwordEncoder;

    /*@Bean
    CommandLineRunner initDatabase(UserRepository userRepository, LocationRepository locationRepository) {

        return args -> {
            log.info("Preloading " + userRepository.save(new User("username1", passwordEncoder.encode("password1"))));
            log.info("Preloading " + userRepository.save(new User("username2", passwordEncoder.encode("password2"))));
            log.info("Preloading " + locationRepository.save(new Location("Tauron Arena")));
        };
    }*/
}
