package br.com.esmaelgoncalves.starwarsapi.controller;

import br.com.esmaelgoncalves.starwarsapi.model.Planet;
import br.com.esmaelgoncalves.starwarsapi.repository.filter.PlanetFilter;
import br.com.esmaelgoncalves.starwarsapi.service.PlanetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("planets")
public class PlanetController {

    @Autowired
    private PlanetService planetService;

    @PostMapping
    public ResponseEntity addPlanet(@Valid @RequestBody Planet planet) {
        Planet savedPlanet = planetService.save(planet);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(savedPlanet.getId())
                .toUri();
        return ResponseEntity.created(uri).body(savedPlanet);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deletePlanet(@PathVariable Long id) {
        planetService.delete(id);
    }


    @GetMapping
    public ResponseEntity find(PlanetFilter filter){
        return planetService.filter(filter);
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable Long id) {
       Planet planet = planetService.findById(id);
       return ResponseEntity.ok(planet);
    }
}
