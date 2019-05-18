package br.com.esmaelgoncalves.starwarsapi.service;

import br.com.esmaelgoncalves.starwarsapi.dto.PlanetSwapiResponse;
import br.com.esmaelgoncalves.starwarsapi.repository.filter.PlanetFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class SwapiPlanetService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${swapi.url.planets}")
    private String planetUrl;

    public PlanetSwapiResponse getPlanets(PlanetFilter filter){
        log.info("Executing a searching at swapi api...");

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

        String url = planetUrl + "?page={page}";
        log.info("Preparing to call: {}", url);
        PlanetSwapiResponse planetSwapiResponse = restTemplate.exchange(url,
                HttpMethod.GET, request, PlanetSwapiResponse.class, params).getBody();

        return planetSwapiResponse;
    }

    public PlanetSwapiResponse.PlanetSwapi getPlanet(String name){
        log.info("Executing a searching at swapi api...");

        Map<String, String> params = new HashMap();
        params.put("search", name);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("User-Agent", "IdentificadorUSerAgent");
        httpHeaders.add("content-type", "application/json");
        HttpEntity request = new HttpEntity(null, httpHeaders);

        String url = planetUrl + "?search={search}";
        log.info("Preparing to call: {}", url);
        PlanetSwapiResponse planetSwapiResponse = restTemplate.exchange(url,
                HttpMethod.GET, request, PlanetSwapiResponse.class, params).getBody();

        if(planetSwapiResponse.getResults() != null && !planetSwapiResponse.getResults().isEmpty()) {
            return planetSwapiResponse.getResults().get(0);
        } else {
            return null;
        }
    }

}
