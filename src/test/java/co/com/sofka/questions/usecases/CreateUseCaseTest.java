package co.com.sofka.questions.usecases;

import co.com.sofka.questions.collections.Question;
import co.com.sofka.questions.enums.Category;
import co.com.sofka.questions.enums.Type;
import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.reposioties.QuestionRepository;
import co.com.sofka.questions.reposioties.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


class CreateUseCaseTest {


     QuestionRepository questionRepository;
     MapperUtils mapperUtils;
     CreateUseCase createUseCase;

    @BeforeEach
    public void setup(){
        MapperUtils mapperUtils = new MapperUtils();
        questionRepository = mock(QuestionRepository.class);
        createUseCase = new CreateUseCase(mapperUtils,questionRepository);
    }

    @Test
    void createUseCase(){

        var questionDto= new QuestionDTO(
                "xxxxx-xxx",
                "123456789",
                "que es java",
                Type.OPINION,Category.SCIENCES,
                "una ayuda plis");

        var question= new Question();
        question.setId("xxxxx-xxx");
        question.setUserId("xxxx-xxxx");
        question.setType(Type.OPEN);
        question.setCategory(Category.SCIENCES);
        question.setQuestion("¿Que es java?");
        question.setDescripcion("alguien sabe que es");


        when(questionRepository.save(any())).thenReturn(Mono.just(question));

        StepVerifier.create(createUseCase.apply(questionDto))
                .expectNextMatches(id->{
                    assert id.equals("xxxxx-xxx");
                    return true;
                }).verifyComplete();

        verify(questionRepository).save(any());
    }





}