package com.amalagonj.ej2notes.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity
public class Note implements Comparable<Note>{
    @Override
    public int compareTo(Note o) {
        return o.createdAt.compareTo(createdAt);
    }
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @Size(min = 3, message = "El título debe tener al menos 3 caracteres")
    @NotBlank(message = "El título no puede estar vacío")
    private String title;

    @NotBlank(message = "El contenido no puede estar vacío")
    @Size(min = 10, message = "El contenido debe tener al menos 10 caracteres")
    private String content;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
