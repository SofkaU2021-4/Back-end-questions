package co.com.sofka.questions.collections;

import co.com.sofka.questions.enums.Category;
import co.com.sofka.questions.enums.Type;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;


@Document
public class Question {
    @Id
    private String id;
    private String userId;
    private String question;
    private Type type;
    private Category category;
    private LocalDate fechaCreacio;
    private String descripcion;

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getFechaCreacio() {
        return fechaCreacio;
    }

    public void setFechaCreacio(LocalDate fechaCreacio) {
        this.fechaCreacio = fechaCreacio;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
