package com.app.datapart.apirest.exeptionapi;

import com.app.datapart.Exepciotions.ClienteNotFoundExeption;
import com.app.datapart.Exepciotions.ProductoNotFoundExeption;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiExeptionHanler {
    @ExceptionHandler(ClienteNotFoundExeption.class)
    public ResponseEntity<String> handleClienteNotFoundException(ClienteNotFoundExeption ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProductoNotFoundExeption.class)
    public ResponseEntity<String> handleProductoNotFountException(ProductoNotFoundExeption ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

}
