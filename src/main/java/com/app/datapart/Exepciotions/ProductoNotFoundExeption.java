package com.app.datapart.Exepciotions;

public class ProductoNotFoundExeption extends RuntimeException{
    public ProductoNotFoundExeption(String msg){
        super(msg);
    }
}
