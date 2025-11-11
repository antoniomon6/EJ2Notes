package com.amalagonj.ej2notes.controllers;

import java.util.List;

import com.amalagonj.ej2notes.exception.ConcurrencyConflictException;
import com.amalagonj.ej2notes.exception.NoteNotFoundException;
import com.amalagonj.ej2notes.model.Note;
import com.amalagonj.ej2notes.repository.NoteRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired; // Asegúrate de importar el repositorio
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


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
    public String createNote(Note note) {
        noteRepository.save(note);
        return "redirect:/list-notes";
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

    @PostMapping("/notes-form")
    public String createNote(@Valid Note note, BindingResult br, Model model) {
        if (br.hasErrors()) {
            // Si hay errores de validación, volvemos al formulario para mostrarlos
            model.addAttribute("note", note); // Mantenemos los datos introducidos
            // La plantilla new_note.html debe mostrar el error con th:errors
            return "new_note";
        }
        noteRepository.save(note);
        return "redirect:/list-notes";
        // Si la validación en el Controller REST fallara, Spring lanza una
        // MethodArgumentNotValidException que se mapea por defecto a un 400.
    }

    @GetMapping("/test-500")
    public String triggerInternalError() {
        String s = null;
        s.length(); // Esto fuerza un NullPointerException (HTTP 500)
        return "menu";
    }

    @GetMapping("/edit-note/{id}")
    public String showEditNoteForm(@PathVariable Long id, Model model) {
        Note note = noteRepository.findById(id).orElseThrow(() -> new NoteNotFoundException(id)); // Reutiliza el 404
        model.addAttribute("note", note);
        return "edit_note"; // Carga el formulario edit_note.html
    }

    //@PostMapping("/edit-note/{id}")
    @PutMapping("/edit-note/{id}")
    public String updateNoteMvc(@PathVariable Long id, @Valid Note note, BindingResult br, Model model) {
        // Reutiliza la lógica de validación
        if (br.hasErrors()) {
            model.addAttribute("note", note);
            return "edit_note"; // Vuelve a mostrar la plantilla con errores
        }

        // Lógica para guardar (similar al PUT REST, pero sin DTO)
        Note existingNote = noteRepository.findById(id).orElseThrow(() -> new NoteNotFoundException(id));

        // ** Lógica de Simulación de Conflicto **: lanzamos la excepción para hacerlo fácil si la nota
        // contiene en detalle la cadena “CONFLICTO”
        // Si el contenido es "CONFLICTO", lanzamos el error 409
        if ("CONFLICTO".equalsIgnoreCase(existingNote.getContent())) {
            throw new ConcurrencyConflictException("Nota ID " + id);
        }
        // ** Fin de la Simulación **


        existingNote.setTitle(note.getTitle());
        existingNote.setContent(note.getContent());
        noteRepository.save(existingNote);
        return "redirect:/list-notes"; // PRG - Redirección 302
        //return "redirect:/list-notes";

    }
}

