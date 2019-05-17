package br.com.esmaelgoncalves.starwarsapi.service;

import br.com.esmaelgoncalves.starwarsapi.dto.PlanetSwapiResponse;
import br.com.esmaelgoncalves.starwarsapi.exception.InvalidPlanetException;
import br.com.esmaelgoncalves.starwarsapi.exception.PlanetNotFoundException;
import br.com.esmaelgoncalves.starwarsapi.model.Planet;
import br.com.esmaelgoncalves.starwarsapi.repository.PlanetRepository;
import br.com.esmaelgoncalves.starwarsapi.repository.filter.PlanetFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PlanetService {

    @Autowired
    private PlanetRepository repository;
    @Autowired
    private SwapiPlanetService swapiPlanetService;

    public Planet save(Planet planet) {
        PlanetSwapiResponse.PlanetSwapi planetSwapi = swapiPlanetService.getPlanet(planet.getName());

        if (planetSwapi == null) {
            throw new InvalidPlanetException("The Planet name provided is invalid.");
        }

        planet.setFilms(planetSwapi.getFilms().size());

        return repository.save(planet);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Planet findById(Long id) {
        Optional<Planet> optionalPlanet = repository.findById(id);
        Planet planet = optionalPlanet.orElseThrow(() -> new PlanetNotFoundException("Planet not found."));
        return planet;
    }

    public List<Planet> findAll() {
        List<Planet> planets = new ArrayList<>();

        repository.findAll().forEach(p -> {
            planets.add(p);
        });

        if (planets.size() > 0) {
            return planets;
        } else {
            throw new PlanetNotFoundException("No Planet Found at Database.");
        }
    }

    public ResponseEntity filter(PlanetFilter filter) {

        if (filter.isSwapi()) {
            return ResponseEntity.ok(swapiPlanetService.getPlanets(filter));
        } else if (filter.getName() != null) {
            Optional<Planet> planet = repository.findByName(filter.getName());

            if (planet.isPresent()) {
                return ResponseEntity.ok(planet.get());
            } else {
                throw new PlanetNotFoundException("Planet not found");
            }
        } else {
            return ResponseEntity.ok(findAll());
        }
    }
}
