package com.amalagonj.ej2notes.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Manejo específico de NoteNotFoundException (404)
    @ExceptionHandler(NoteNotFoundException.class)
    public ModelAndView handleNoteNotFoundException(NoteNotFoundException ex) {
        ModelAndView mav = new ModelAndView("error");
        mav.setStatus(HttpStatus.NOT_FOUND);
        mav.addObject("errorMessage", ex.getMessage());
        return mav;
    }
    // Manejo específico de NoteNotFoundException (404)
    @ExceptionHandler(ConcurrencyConflictException .class)
    public ModelAndView handleNoteNotFoundException(ConcurrencyConflictException  ex) {
        ModelAndView mav = new ModelAndView("error");
        mav.setStatus(HttpStatus.CONFLICT);
        mav.addObject("errorMessage", ex.getMessage());
        return mav;
    }

    // Manejo general de cualquier otra excepción (500)
    @ExceptionHandler(Exception.class)
    public ModelAndView handleAllExceptions(Exception ex) {
        ModelAndView mav = new ModelAndView("error");
        mav.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        mav.addObject("errorMessage", "Ocurrió un error inesperado en el servidor. Inténtalo más tarde.");
        // Opcional: mav.addObject("details", ex.getMessage());
        return mav;
    }
}