package br.com.esmaelgoncalves.starwarsapi.service;

import br.com.esmaelgoncalves.starwarsapi.dto.PlanetSwapiResponse;
import br.com.esmaelgoncalves.starwarsapi.exception.InvalidPlanetException;
import br.com.esmaelgoncalves.starwarsapi.exception.PlanetNotFoundException;
import br.com.esmaelgoncalves.starwarsapi.model.Planet;
import br.com.esmaelgoncalves.starwarsapi.repository.PlanetRepository;
import br.com.esmaelgoncalves.starwarsapi.repository.filter.PlanetFilter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
public class PlanetServiceTests {

    @TestConfiguration
    static class PlanetServiceTestsContextConfiguration {

        @Bean
        public PlanetService planetService() {
            return new PlanetService();
        }
    }

    @Autowired
    private PlanetService planetService;
    @MockBean
    private SwapiPlanetService swapiPlanetService;
    @MockBean
    private PlanetRepository repository;

    @Before
    public void setup(){
        PlanetSwapiResponse.PlanetSwapi planetSwapi = new PlanetSwapiResponse.PlanetSwapi();
        planetSwapi.setName("Alderaan");
        planetSwapi.setFilms(new ArrayList<>());

        PlanetSwapiResponse swapiResponse = new PlanetSwapiResponse();
        List<PlanetSwapiResponse.PlanetSwapi> planetSwapiList = new ArrayList<>();
        planetSwapiList.add(planetSwapi);
        swapiResponse.setResults(planetSwapiList);

        Planet planet = Planet.builder()
                .id(1L)
                .name("Alderaan")
                .climate("temperate")
                .terrain("grasslands, mountains")
                .films(2).build();

        given(swapiPlanetService.getPlanet(planetSwapi.getName())).willReturn(planetSwapi);
        given(swapiPlanetService.getPlanets(any())).willReturn(swapiResponse);
        given(repository.save(any(Planet.class))).willReturn(planet);
        given(repository.findById(1L)).willReturn(Optional.ofNullable(planet));
        given(repository.findByName(planet.getName())).willReturn(Optional.of(planet));

    }

    @Test
    public void whenValidPlanet_thenSave() {
        Planet planet = Planet.builder()
                .name("Alderaan")
                .climate("temperate")
                .terrain("grasslands, mountains")
                .films(2).build();

        Planet saved = planetService.save(planet);

        assertThat(saved).isNotNull();
        assertThat(saved.getId()).isNotZero();
        assertThat(saved.getName()).isEqualTo(planet.getName());
    }

    @Test(expected = InvalidPlanetException.class)
    public void whenInvalidPlanet_thenThrowException() {
        Planet planet = Planet.builder()
                .name("OtherPlanet")
                .climate("temperate")
                .terrain("grasslands, mountains")
                .films(2).build();
        planetService.save(planet);
    }


    @Test
    public void whenFindByValidId_thenReturn() {
        Planet planet = planetService.findById(1L);

        assertThat(planet).isNotNull();
    }

    @Test(expected = PlanetNotFoundException.class)
    public void whenFindByInvalidId_thenThrowException() {
       planetService.findById(100L);
    }

    @Test
    public void whenFilterSwapi_thenReturnOK() {
        PlanetFilter filter = new PlanetFilter();
        filter.setSwapi(true);

        ResponseEntity responseEntity = planetService.filter(filter);

        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void whenFilterName_thenReturnOK() {
        PlanetFilter filter = new PlanetFilter();
        filter.setSwapi(false);
        filter.setName("Alderaan");

        ResponseEntity responseEntity = planetService.filter(filter);

        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test(expected = PlanetNotFoundException.class)
    public void whenFilterInvalidName_thenReturnThrowException() {
        PlanetFilter filter = new PlanetFilter();
        filter.setSwapi(false);
        filter.setName("Earth");

        planetService.filter(filter);
    }

}
