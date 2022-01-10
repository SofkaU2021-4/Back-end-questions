package co.com.sofka.questions.model;


import co.com.sofka.questions.enums.Category;
import co.com.sofka.questions.enums.Type;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class QuestionDTO {
    private String id;
    @NotBlank
    private String userId;
    @NotBlank
    private String question;
    @NotNull
    private Type type;
    @NotNull
    private Category category;
    private List<AnswerDTO> answers;
    private UserDTO userDTO;
    private LocalDate fechaCreacion = LocalDate.now();
    @NotBlank
    private String descripcion;

    public QuestionDTO() {

    }

    public QuestionDTO(String userId, String question, Type type, Category category) {
        this.userId = userId;
        this.question = question;
        this.type=type;
        this.category=category;

    }

    public QuestionDTO(String id, String userId, String question, Type type, Category category ,String descripcion) {
        this.id = id;
        this.userId = userId;
        this.question = question;
        this.type = type;
        this.category = category;
        this.descripcion = descripcion;
        this.fechaCreacion=LocalDate.now();
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public List<AnswerDTO> getAnswers() {
        this.answers = Optional.ofNullable(answers).orElse(new ArrayList<>());
        return answers;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public void setAnswers(List<AnswerDTO> answers) {
        this.answers = answers;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuestionDTO that = (QuestionDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(userId, that.userId) && Objects.equals(question, that.question) && type == that.type && category == that.category && Objects.equals(answers, that.answers) && Objects.equals(userDTO, that.userDTO) && Objects.equals(fechaCreacion, that.fechaCreacion) && Objects.equals(descripcion, that.descripcion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, question, type, category, answers, userDTO, fechaCreacion, descripcion);
    }
}
