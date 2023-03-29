package uj.jwzp.ticketmaster;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import uj.jwzp.ticketmaster.controllers.LocationController;
import uj.jwzp.ticketmaster.controllers.UserController;

@SpringBootTest
public class SmokeTest {
    @Autowired
    private UserController userController;
    @Autowired
    private LocationController locationController;

    @Test
    public void contextLoads() throws Exception{
        assertThat(userController).isNotNull();
        assertThat(locationController).isNotNull();
    }
}
