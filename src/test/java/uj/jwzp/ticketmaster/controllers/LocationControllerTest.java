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
import uj.jwzp.ticketmaster.config.SecurityConfig;
import uj.jwzp.ticketmaster.entities.Location;
import uj.jwzp.ticketmaster.services.LocationService;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(LocationController.class)
@Import(SecurityConfig.class)
public class LocationControllerTest {

    @MockBean
    private LocationService locationService;

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
    public void givenLocations_whenGetLocations_thenReturnJsonArray() throws Exception {
        Location location1 = new Location("Tauron Arena");
        Location location2 = new Location("Ergo Arena");
        List<Location> locationList = List.of(location1, location2);

        Mockito.when(locationService.getAllLocations()).thenReturn(locationList);

        mvc.perform(get("/locations")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is(location1.getName())))
                .andExpect(jsonPath("$[1].name", is(location2.getName())));
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void givenADMIN_whenAddLocation_thenReturnOk() throws Exception {
        Location location = new Location("Tauron Arena");

        Mockito.when(locationService.addNewLocation(location)).thenReturn(location);

        mvc.perform(post("/locations")
                .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(location)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "USER")
    public void givenUSER_whenAddLocation_thenReturnForbidden() throws Exception {
        Location location = new Location("Tauron Arena");

        Mockito.when(locationService.addNewLocation(location)).thenReturn(location);

        mvc.perform(post("/locations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(location)))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void givenADMIN_whenDeleteLocation_thenReturnString() throws Exception {
        mvc.perform(delete("/locations/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Location has been deleted")));
    }
}
