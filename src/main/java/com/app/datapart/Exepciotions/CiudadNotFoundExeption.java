package com.app.datapart.Exepciotions;

public class CiudadNotFoundExeption extends RuntimeException {
    public CiudadNotFoundExeption(Long codigo) {
        super("Ciudad con código " + codigo + " no encotrado");
    }
}
