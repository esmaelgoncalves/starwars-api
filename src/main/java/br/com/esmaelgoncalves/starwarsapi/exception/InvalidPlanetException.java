package br.com.esmaelgoncalves.starwarsapi.exception;

public class InvalidPlanetException extends RuntimeException{

    public InvalidPlanetException(){}

    public InvalidPlanetException(String message){super(message);}
}
