package br.com.esmaelgoncalves.starwarsapi.repository.filter;

import lombok.Data;

@Data
public class PlanetFilter {
    private boolean swapi;
    private int page;
    private String name;
}
