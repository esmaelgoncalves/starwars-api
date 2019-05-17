package br.com.esmaelgoncalves.starwarsapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlanetSwapiResponse {
    private int count;
    private String next;
    private String previous;
    private List<PlanetSwapi> results;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PlanetSwapi {
        private String climate;
        private String diameter;
        private String gravity;
        private String name;
        @JsonProperty("orbital_period")
        private String orbitalPeriod;
        private String population;
        private List<String> residents;
        @JsonProperty("rotation_period")
        private String rotationPeriod;
        @JsonProperty("surface_water")
        private String surfaceWater;
        private String terrain;
        private List<String> films;
    }
}
