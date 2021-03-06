package co.com.sofka.questions.usecases;

import co.com.sofka.questions.collections.Answer;
import co.com.sofka.questions.collections.Question;
import co.com.sofka.questions.enums.Category;
import co.com.sofka.questions.enums.Type;
import co.com.sofka.questions.reposioties.AnswerRepository;
import co.com.sofka.questions.reposioties.QuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

class DeleteUseCaseTest {

    QuestionRepository questionRepository;
    AnswerRepository answerRepository;
    MapperUtils mapperUtils;
    DeleteUseCase deleteUseCase;

    @BeforeEach
    public void setup(){
        MapperUtils mapperUtils = new MapperUtils();
        answerRepository = mock(AnswerRepository.class);
        questionRepository = mock(QuestionRepository.class);
        deleteUseCase = new DeleteUseCase(answerRepository,questionRepository,mapperUtils);
    }

    @Test
    void deleteUseCase(){

        var question= new Question();
        question.setId("xxxxx-xxx");
        question.setUserId("xxxx-xxxxyyy");
        question.setType(Type.OPEN);
        question.setCategory(Category.SCIENCES);
        question.setQuestion("¿Que es java?");
        question.setDescripcion("alguien sabe que es");

        var answer = new Answer();
        answer.setId("123456789");
        answer.setQuestionId("1asd2153453we");
        answer.setUserId("1234");
        answer.setAnswer("Domain Driven Design");

        when(questionRepository.findById((String) any())).thenReturn(Mono.just(question));
        when(questionRepository.deleteById((String) any())).thenReturn(Mono.empty());
        when(answerRepository.deleteAllByQuestionId(any())).thenReturn(Mono.empty());

        StepVerifier.create(deleteUseCase.apply("xxxxx-xxx"))
                .expectNextMatches(questionDTO->{
                    assert questionDTO.getId().equals("xxxxx-xxx");
                    assert questionDTO.getUserId().equals("xxxx-xxxxyyy");
                    assert questionDTO.getType().equals(Type.OPEN);
                    return true;
                }).verifyComplete();

        verify(questionRepository).findById((String) any());
        verify(questionRepository).deleteById((String) any());
        verify(answerRepository).deleteAllByQuestionId( any());


    }



}