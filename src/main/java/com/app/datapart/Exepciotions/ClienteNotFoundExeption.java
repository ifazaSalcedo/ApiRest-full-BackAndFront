package com.app.datapart.Exepciotions;

public class ClienteNotFoundExeption extends RuntimeException{

    public ClienteNotFoundExeption(String mensage) {
        super(mensage);
    }
}
