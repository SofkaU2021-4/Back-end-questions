package co.com.sofka.questions.usecases;

import co.com.sofka.questions.collections.Question;
import co.com.sofka.questions.collections.User;
import co.com.sofka.questions.enums.Category;
import co.com.sofka.questions.enums.Type;
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

class ListUseCaseTest {

    QuestionRepository questionRepository;
    ListUseCase listUseCase;
    UserRepository userRepository;

    @BeforeEach
    public void setup(){
        MapperUtils mapperUtils = new MapperUtils();
        questionRepository = mock(QuestionRepository.class);
        userRepository= mock(UserRepository.class);
        listUseCase = new ListUseCase(mapperUtils,questionRepository,userRepository);
    }

    @Test
    void getValidationTest(){
        var question= new Question();
        question.setUserId("xxxx-xxxx");
        question.setType(Type.OPEN);
        question.setCategory(Category.SCIENCES);
        question.setQuestion("¿Que es java?");
        question.setDescripcion("alguien sabe que es");

        var user = new User();
        user.setEmail("jscrojas25@yahoo.es");
        user.setId("123456789");
        user.setUid("xxxx-xxxx");
        user.setPictureUrl("url/picture");
        user.setName("sebastian cabrera");

        when(userRepository.findByUid(any())).thenReturn(Mono.just(user));
        when(questionRepository.findAll()).thenReturn(Flux.just(question));


        StepVerifier.create(listUseCase.get())
                .expectNextMatches(questionDTO -> {
                    assert questionDTO.getUserId().equals("xxxx-xxxx");
                    assert questionDTO.getCategory().equals(Category.SCIENCES);
                    assert questionDTO.getQuestion().equals("¿Que es java?");
                    assert questionDTO.getType().equals(Type.OPEN);
                    return true;
                })
                .verifyComplete();
        verify(questionRepository).findAll();
        verify(userRepository).findByUid(any());

    }

}