package com.amalagonj.ej2notes.controllers;
import com.amalagonj.ej2notes.exception.NoteNotFoundException;
import com.amalagonj.ej2notes.model.Note;
import com.amalagonj.ej2notes.repository.NoteRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notes")
public class NoteController {

    @Autowired
    private NoteRepository noteRepository;

    @GetMapping
    public List<Note> getAllNotes() {
        return noteRepository.findAll();
    }
    @GetMapping("/imp")
    public List<Note> getAllNotesImp() {
        return noteRepository.findByTitleContainingIgnoreCase("importante");
    }

    @PostMapping
    public Note createNote(@RequestBody Note note) {
        return noteRepository.save(note);
    }

    @GetMapping("/{id}")
    public Note getNoteById(@PathVariable Long id) {
        return noteRepository.findById(id)
                .orElseThrow(() -> new NoteNotFoundException(id));
    }

    @DeleteMapping("/{id}")
    public void deleteNoteById(@PathVariable Long id) {
        noteRepository.deleteById(id);
    }
}
