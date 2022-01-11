package co.com.sofka.questions.usecases;

import co.com.sofka.questions.collections.Answer;
import co.com.sofka.questions.collections.Question;
import co.com.sofka.questions.collections.User;
import co.com.sofka.questions.enums.Category;
import co.com.sofka.questions.enums.Type;
import co.com.sofka.questions.reposioties.AnswerRepository;
import co.com.sofka.questions.reposioties.QuestionRepository;
import co.com.sofka.questions.reposioties.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

class GetUseCaseTest {

    QuestionRepository questionRepository;
    AnswerRepository answerRepository;
    MapperUtils mapperUtils;
    UserRepository userRepository;
    GetUseCase getUseCase;

    @BeforeEach
    public void setup(){
        MapperUtils mapperUtils = new MapperUtils();
        userRepository = mock(UserRepository.class);
        questionRepository = mock(QuestionRepository.class);
        answerRepository = mock(AnswerRepository.class);
        getUseCase = new GetUseCase(mapperUtils,questionRepository,answerRepository,userRepository);

    }

    @Test
    void getUseCase(){
        var question= new Question();
        question.setId("xxxxx-xxx");
        question.setUserId("xxxx-xxxx");
        question.setType(Type.OPEN);
        question.setCategory(Category.SCIENCES);
        question.setQuestion("¿Que es java?");
        question.setDescripcion("alguien sabe que es");

        var answer = new Answer();
        answer.setId("123456789");
        answer.setQuestionId("1asd2153453we");
        answer.setUserId("1234");
        answer.setAnswer("Domain Driven Design");

        var user = new User();
        user.setEmail("jscrojas25@yahoo.es");
        user.setId("123456789");
        user.setUid("564897");
        user.setPictureUrl("url/picture");
        user.setName("sebastian cabrera");



        when(questionRepository.findById((String) any())).thenReturn(Mono.just(question));
        when(answerRepository.findAllByQuestionId(any())).thenReturn(Flux.just(answer));
        when(userRepository.findByUid(any())).thenReturn(Mono.just(user));

        StepVerifier.create(getUseCase.apply("xxxxx-xxx"))
                .expectNextMatches(questionDTO->{
                    assert questionDTO.getId().equals("xxxxx-xxx");
                    assert questionDTO.getUserDTO().getUid().equals("564897");
                    return true;
                }).verifyComplete();

        verify(questionRepository).findById((String) any());
        verify(answerRepository).findAllByQuestionId(any());
        verify(userRepository,times(2)).findByUid(any());

    }

}