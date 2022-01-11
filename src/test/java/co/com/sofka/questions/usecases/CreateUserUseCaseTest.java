package co.com.sofka.questions.usecases;

import co.com.sofka.questions.collections.User;
import co.com.sofka.questions.model.UserDTO;
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

import java.util.Objects;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;


class CreateUserUseCaseTest {

    UserRepository userRepository;
    MapperUtils mapperUtils;
    CreateUserUseCase createUserUseCase;

    @BeforeEach
    public void setup(){
        MapperUtils mapperUtils = new MapperUtils();
        userRepository = mock(UserRepository.class);
        createUserUseCase = new CreateUserUseCase(userRepository,mapperUtils);

    }


    @Test
    void createUserCase(){

        var user = new User();
        user.setEmail("jscrojas25@yahoo.es");
        user.setId("123456789");
        user.setUid("xxxx-xxxx");
        user.setPictureUrl("url/picture");
        user.setName("sebastian cabrera");

        var userDto= new UserDTO();
        userDto.setEmail("jscrojas25@yahoo.es");
        userDto.setId("123456789");
        userDto.setUid("xxxx-xxxx");
        userDto.setPictureUrl("url/picture");
        userDto.setName("sebastian cabrera");

        when(userRepository.findByUid(any())).thenReturn(Mono.empty());
        when(userRepository.save(any())).thenReturn(Mono.just(user));

        StepVerifier.create(createUserUseCase.apply(userDto))
                .expectNextMatches(id->{
                    assert id.equals("guardado");
                    return true;
                }).verifyComplete();

        verify(userRepository).findByUid( any());
        verify(userRepository).save(any());

    }



}

