package com.amalagonj.ej2notes.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT) // Mapea al código 409
public class ConcurrencyConflictException extends RuntimeException {
    public ConcurrencyConflictException(String resource) {
        super("Conflicto de concurrencia al actualizar: " + resource);
    }
}
