package br.com.esmaelgoncalves.starwarsapi.repository;

import br.com.esmaelgoncalves.starwarsapi.model.Planet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PlanetRespositoryTests {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PlanetRepository repository;

    @Test
    public void whenFindById_thenReturnPlanet() {
        Planet planet = Planet.builder()
                .name("Alderaan")
                .climate("temperate")
                .terrain("grasslands, mountains")
                .films(2).build();

        Planet saved = entityManager.persist(planet);
        entityManager.flush();

        Optional<Planet> found = repository.findById(saved.getId());

        assertThat(found.isPresent()).isTrue();
        assertThat(found.get().getName()).isEqualTo(planet.getName());
    }

    @Test
    public void whenFindByName_thenReturnPlanet() {
        Planet planet = Planet.builder()
                .name("Alderaan")
                .climate("temperate")
                .terrain("grasslands, mountains")
                .films(2).build();

        entityManager.persist(planet);
        entityManager.flush();

        Optional<Planet> found = repository.findByName(planet.getName());

        assertThat(found.isPresent()).isTrue();
        assertThat(found.get().getName()).isEqualTo(planet.getName());
    }

    @Test
    public void whenFindAll_thenReturnAllPlanetAtDataBase() {
        Planet planet = Planet.builder()
                .name("Alderaan")
                .climate("temperate")
                .terrain("grasslands, mountains")
                .films(2).build();

        Planet saved = entityManager.persist(planet);
        entityManager.flush();

        List<Planet> planets = (List<Planet>) repository.findAll();

        assertThat(planets).isNotEmpty();
    }

    @Test
    public void whenDelete_thenNotReturnPlanet() {
        Planet planet = Planet.builder()
                .name("Alderaan")
                .climate("temperate")
                .terrain("grasslands, mountains")
                .films(2).build();

        Planet saved = entityManager.persist(planet);
        entityManager.flush();

        Optional<Planet> found = repository.findById(saved.getId());

        assertThat(found.isPresent()).isTrue();
        assertThat(found.get().getName()).isEqualTo(planet.getName());

        repository.deleteById(saved.getId());

        found = repository.findById(saved.getId());

        assertThat(found.isPresent()).isFalse();
    }

}
