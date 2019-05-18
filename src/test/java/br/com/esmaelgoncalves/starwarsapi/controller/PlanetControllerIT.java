package br.com.esmaelgoncalves.starwarsapi.controller;

import br.com.esmaelgoncalves.starwarsapi.service.PlanetService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PlanetControllerIT {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private PlanetService planetService;

    @Test
    public void whenSaveValidPlanet_thenOk() throws Exception {
        String json = "{\r\n  \"name\": \"Alderaan\",\r\n  \"climate\": \"temperate\",\r\n  \"terrain\": \"grasslands, mountains\"\r\n}";

        mvc.perform(post("/planets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    public void whenSaveInvalidPlanet_thenBadRequest() throws Exception {
        String json = "{\r\n  \"name\": \"Earth\",\r\n  \"climate\": \"temperate\",\r\n  \"terrain\": \"grasslands, mountains\"\r\n}";

        mvc.perform(post("/planets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest());
    }

}
