package co.com.sofka.questions.model;


import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

    public class AnswerDTO {
        private String id;
        @NotBlank(message = "Debe existir el userId para este objeto")
        private String userId;
        @NotBlank
        private String questionId;
        @NotBlank
        private String answer;
        private Integer position;
        private UserDTO userDTO;
        private LocalDate fechaCreacio = LocalDate.now();




        public AnswerDTO() {

        }


        public AnswerDTO(String id, String questionId, @NotBlank String userId, @NotBlank String answer ) {
            this.id = id;
            this.userId = userId;
            this.questionId = questionId;
            this.answer = answer;
            this.fechaCreacio = LocalDate.now();
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public UserDTO getUserDTO() {
            return userDTO;
        }

        public void setUserDTO(UserDTO userDTO) {
            this.userDTO = userDTO;
        }

        public LocalDate getFechaCreacio() {
            return fechaCreacio;
        }

        public void setFechaCreacio(LocalDate fechaCreacio) {
            this.fechaCreacio = fechaCreacio;
        }

        public Integer getPosition() {
            return Optional.ofNullable(position).orElse(1);
        }

        public void setPosition(Integer position) {
            this.position = position;
        }


        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getQuestionId() {
            return questionId;
        }

        public void setQuestionId(String questionId) {
            this.questionId = questionId;
        }

        public String getAnswer() {
            return answer;
        }

        public void setAnswer(String answer) {
            this.answer = answer;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            AnswerDTO answerDTO = (AnswerDTO) o;
            return Objects.equals(id, answerDTO.id) && Objects.equals(userId, answerDTO.userId) && Objects.equals(questionId, answerDTO.questionId) && Objects.equals(answer, answerDTO.answer) && Objects.equals(position, answerDTO.position) && Objects.equals(userDTO, answerDTO.userDTO) && Objects.equals(fechaCreacio, answerDTO.fechaCreacio);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, userId, questionId, answer, position, userDTO, fechaCreacio);
        }
    }
