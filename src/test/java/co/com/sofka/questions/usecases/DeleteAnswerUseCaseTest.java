package co.com.sofka.questions.usecases;

import co.com.sofka.questions.collections.Answer;
import co.com.sofka.questions.model.AnswerDTO;
import co.com.sofka.questions.reposioties.AnswerRepository;
import co.com.sofka.questions.reposioties.QuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


class DeleteAnswerUseCaseTest {

    AnswerRepository answerRepository;
    DeleteAnswerUseCase deleteAnswerUseCase;

    @BeforeEach
    public void setup(){
        answerRepository = mock(AnswerRepository.class);
        deleteAnswerUseCase = new DeleteAnswerUseCase(answerRepository);
    }


    @Test
    void setDeleteAnswerTest(){
        var answer = new Answer();
        answer.setId("123456789");
        answer.setQuestionId("1asd2153453we");
        answer.setUserId("1234");
        answer.setAnswer("Domain Driven Design");



        when(answerRepository.findById((String) any())).thenReturn(Mono.just(answer));
        when(answerRepository.deleteById("123456789")).thenReturn(Mono.empty());

        StepVerifier.create(deleteAnswerUseCase.apply("123456789"))
                .expectNextMatches(id->{
                    assert id.equals("1asd2153453we");
                    return true;
                }).verifyComplete();

        verify(answerRepository).findById((String) any());
        verify(answerRepository).deleteById("123456789");
    }

}