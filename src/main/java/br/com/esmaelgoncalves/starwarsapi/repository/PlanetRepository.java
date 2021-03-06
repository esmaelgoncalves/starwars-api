package br.com.esmaelgoncalves.starwarsapi.repository;

import br.com.esmaelgoncalves.starwarsapi.model.Planet;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PlanetRepository extends CrudRepository<Planet, Long> {
    Optional<Planet> findByName(String name);
}
