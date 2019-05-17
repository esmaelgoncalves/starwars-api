package br.com.esmaelgoncalves.starwarsapi.service;

import br.com.esmaelgoncalves.starwarsapi.dto.PlanetSwapiResponse;
import br.com.esmaelgoncalves.starwarsapi.repository.filter.PlanetFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SwapiPlanetService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${swapi.url.planets}")
    private String planetUrl;

    public PlanetSwapiResponse getPlanets(PlanetFilter filter){

        Map<String, String> params = new HashMap();
        if(filter.getPage() > 0){
            params.put("page", String.valueOf(filter.getPage()));
        } else {
            params.put("page", "1");
        }

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("content-type", "application/json");
        httpHeaders.add("User-Agent", "IdentificadorUSerAgent");
        HttpEntity request = new HttpEntity(null, httpHeaders);

        PlanetSwapiResponse planetSwapiResponse = restTemplate.exchange(planetUrl + "?page={page}",
                HttpMethod.GET, request, PlanetSwapiResponse.class, params).getBody();

        return planetSwapiResponse;
    }

    public PlanetSwapiResponse.PlanetSwapi getPlanet(String name){

        Map<String, String> params = new HashMap();
        params.put("search", name);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("User-Agent", "IdentificadorUSerAgent");
        httpHeaders.add("content-type", "application/json");
        HttpEntity request = new HttpEntity(null, httpHeaders);

        PlanetSwapiResponse planetSwapiResponse = restTemplate.exchange(planetUrl + "?search={search}",
                HttpMethod.GET, request, PlanetSwapiResponse.class, params).getBody();

        return planetSwapiResponse.getResults().get(0);
    }

}
