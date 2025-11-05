package com.amalagonj.ej2notes.exception;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
// Cuando esta excepción se lance, Spring enviará un código 404 Not Found
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoteNotFoundException extends RuntimeException {

    public NoteNotFoundException(Long id) {
        super("Nota no encontrada con ID: " + id);
    }
}
