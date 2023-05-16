package com.example.RestService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class ConflictEx extends RuntimeException {

    public ConflictEx(String status) {
        super("You CAN'T rent a car with status " + status + "!");
    }
}
