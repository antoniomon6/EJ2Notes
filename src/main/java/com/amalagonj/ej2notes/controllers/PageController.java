package com.amalagonj.ej2notes.controllers;
import java.util.List;

import com.amalagonj.ej2notes.model.Note;
import com.amalagonj.ej2notes.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired; // Asegúrate de importar el repositorio
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
public class PageController {

    @Autowired
    private NoteRepository noteRepository; // Necesario para listar notas [cite: 86, 87]

    @GetMapping("/menu")
    public String showMenu() {
        return "menu"; // Devuelve menu.html [cite: 147, 150]
    }

    @GetMapping("/new-note")
    public String showNewNoteForm(Model model) {
        // Añade un objeto Note vacío al modelo para que el formulario lo use [cite: 31, 33]
        model.addAttribute("note", new Note());
        return "new_note"; // Devuelve new_note.html [cite: 34]
    }
    @PostMapping("/create-note")
    public String createNote( Note note) {
        noteRepository.save(note);
        return "redirect:/list-notes" ;
    }
    @GetMapping("/list-notes")
    public String showAllNotes(Model model) {
        // Carga todas las notas de la BBDD [cite: 94]
        List<Note> notes = noteRepository.findAll();
        model.addAttribute("notes", notes); // Añade la lista al modelo [cite: 95]
        return "list_notes"; // Devuelve list_notes.html [cite: 97]
    }

    @GetMapping("/delete-note")
    public String showDeleteNotePage() {
        // Simplemente muestra la página con el formulario JS
        return "delete_note"; // Devuelve delete_note.html [cite: 157, 160]
    }
}

