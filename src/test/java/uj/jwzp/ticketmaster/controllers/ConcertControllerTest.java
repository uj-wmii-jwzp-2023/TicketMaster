package uj.jwzp.ticketmaster.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import uj.jwzp.ticketmaster.ConcertSchema;
import uj.jwzp.ticketmaster.config.SecurityConfig;
import uj.jwzp.ticketmaster.entities.Concert;
import uj.jwzp.ticketmaster.services.ConcertService;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ConcertController.class)
@Import(SecurityConfig.class)
public class ConcertControllerTest {
    @MockBean
    private ConcertService concertService;

    @Autowired
    private MockMvc mvc;

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @WithMockUser
    public void givenConcertsAndUser_whenGetConcerts_thenReturnJsonArray() throws Exception {
        Concert concert1 = new Concert("Eric Clapton");
        Concert concert2 = new Concert("Pink Floyd");
        List<Concert> concerts = List.of(concert1, concert2);

        Mockito.when(concertService.getConcerts(1)).thenReturn(concerts);

        mvc.perform(get("/locations/1/concerts")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is(concert1.getName())))
                .andExpect(jsonPath("$[1].name", is(concert2.getName())));
    }

    @Test
    public void givenConcertsAndNoUser_whenGetConcerts_thenReturnUnauthorized() throws Exception {
        Concert concert1 = new Concert("Eric Clapton");
        Concert concert2 = new Concert("Pink Floyd");
        List<Concert> concerts = List.of(concert1, concert2);

        Mockito.when(concertService.getConcerts(1)).thenReturn(concerts);

        mvc.perform(get("/locations/1/concerts")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    public void givenConcertAndUser_whenGetConcertDetails_thenReturnConcert() throws Exception {
        Concert concert = new Concert("Eric Clapton");

        Mockito.when(concertService.getConcertDetails(1, 2)).thenReturn(concert);

        mvc.perform(get("/locations/1/concerts/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(concert.getName())));
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void givenConcertAndAdmin_whenAddConcert_thenReturnConcert() throws Exception {
        Concert concert = new Concert("Eric Clapton");
        ConcertSchema concertSchema = new ConcertSchema("name", null);

        Mockito.when(concertService.addConcert(1, concertSchema)).thenReturn(concert);

        mvc.perform(post("/locations/1/concerts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(concertSchema)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(concert.getName())));
    }

    @Test
    @WithMockUser(authorities = "USER")
    public void givenConcertAndUser_whenAddConcert_thenReturnForbidden() throws Exception {
        Concert concert = new Concert("Eric Clapton");
        ConcertSchema concertSchema = new ConcertSchema("name", null);

        Mockito.when(concertService.addConcert(1, concertSchema)).thenReturn(concert);

        mvc.perform(post("/locations/1/concerts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(concertSchema)))
                .andExpect(status().isForbidden());
    }
}
