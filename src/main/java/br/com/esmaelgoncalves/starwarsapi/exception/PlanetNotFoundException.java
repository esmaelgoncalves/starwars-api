package br.com.esmaelgoncalves.starwarsapi.exception;

public class PlanetNotFoundException extends RuntimeException {

    public PlanetNotFoundException() {
    }

    public PlanetNotFoundException(String message) {
        super(message);
    }
}
