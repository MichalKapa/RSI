package com.example.RestService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class CarNotFoundEx extends RuntimeException {
    public CarNotFoundEx(int id) {
        super("The car of id="+id+" does NOT exist");
    }
}