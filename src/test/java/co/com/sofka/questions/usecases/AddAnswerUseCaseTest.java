package co.com.sofka.questions.usecases;

import co.com.sofka.questions.Service.SendMailService;
import co.com.sofka.questions.collections.Answer;
import co.com.sofka.questions.collections.User;
import co.com.sofka.questions.enums.Category;
import co.com.sofka.questions.enums.Type;
import co.com.sofka.questions.model.AnswerDTO;
import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.reposioties.AnswerRepository;
import co.com.sofka.questions.reposioties.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.internal.matchers.Any;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
class AddAnswerUseCaseTest {
    @SpyBean
    AddAnswerUseCase addAnswerUseCase;

    @MockBean
    GetUseCase getUseCase;

    @MockBean
    AnswerRepository answerRepository;
    @MockBean
    UserRepository userRepository;

    @MockBean
    SendMailService mailUseCase;

    @Test
    void setAddAnswerUseCase(){

        var user = new User("789456123","1234","carlos","cabrera","jscrojas25@yahoo.es","oe");
        var question = new QuestionDTO("1asd2153453we", "1234", "What id DDD in software?", Type.OPEN, Category.SCIENCES, "Mensaje");
        var answerDTO = new AnswerDTO("123","1asd2153453we", "1234", "Domain Driven Design");
        var answer = new Answer();
        answer.setId("123456789");
        answer.setQuestionId("1asd2153453we");
        answer.setUserId("1234");
        answer.setAnswer("Domain Driven Design");

        Mockito.when(userRepository.findByUid(any())).thenReturn(Mono.just(user));
        Mockito.when(answerRepository.save(any())).thenReturn(Mono.just(answer));
        Mockito.when(getUseCase.apply(Mockito.anyString())).thenReturn(Mono.just(question));

        var questionDTO = addAnswerUseCase.apply(answerDTO);
        var resultQuestionDTO = questionDTO.block();

        assert resultQuestionDTO != null;
        Assertions.assertEquals(resultQuestionDTO.getId(),question.getId());
        Assertions.assertEquals(resultQuestionDTO.getQuestion(),question.getQuestion());


        Mockito.verify(answerRepository,Mockito.times(1)).save(Mockito.any());


    }


}