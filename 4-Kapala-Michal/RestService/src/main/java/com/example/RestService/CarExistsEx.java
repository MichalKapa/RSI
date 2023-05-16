package com.example.RestService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FOUND)
public class CarExistsEx extends RuntimeException {

    public CarExistsEx(int id) {
        super("The car of id="+id+" already exists!");
    }
}