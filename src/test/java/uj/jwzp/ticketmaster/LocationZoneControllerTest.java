package uj.jwzp.ticketmaster;

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
import uj.jwzp.ticketmaster.controllers.LocationZoneController;
import uj.jwzp.ticketmaster.entities.Concert;
import uj.jwzp.ticketmaster.entities.LocationZone;
import uj.jwzp.ticketmaster.services.LocationZoneService;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LocationZoneController.class)
@Import(SecurityConfig.class)
public class LocationZoneControllerTest {
    @MockBean
    private LocationZoneService locationZoneService;

    @Autowired
    private MockMvc mvc;

    @Test
    @WithMockUser
    public void givenLocationZonesAndUser_whenGetLocationZones_thenReturnJsonArray() throws Exception {
        LocationZone locationZone1 = new LocationZone(null, "VIP", "A", 100);
        LocationZone locationZone2 = new LocationZone(null, "Normal", "B", 500);
        List<LocationZone> locationZones = List.of(locationZone1, locationZone2);

        Mockito.when(locationZoneService.getLocationZones(1)).thenReturn(locationZones);

        mvc.perform(get("/locations/1/zones")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is(locationZone1.getName())))
                .andExpect(jsonPath("$[1].name", is(locationZone2.getName())));
    }

    @Test
    public void givenLocationZonesAndNoUser_whenGetLocationZones_thenReturnUnauthorized() throws Exception {
        LocationZone locationZone1 = new LocationZone(null, "VIP", "A", 100);
        LocationZone locationZone2 = new LocationZone(null, "Normal", "B", 500);
        List<LocationZone> locationZones = List.of(locationZone1, locationZone2);

        Mockito.when(locationZoneService.getLocationZones(1)).thenReturn(locationZones);

        mvc.perform(get("/locations/1/zones")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }
}
