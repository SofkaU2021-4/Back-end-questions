package co.com.sofka.questions.usecases;

import co.com.sofka.questions.collections.Question;
import co.com.sofka.questions.collections.User;
import co.com.sofka.questions.model.UserDTO;
import co.com.sofka.questions.reposioties.UserRepository;
import co.com.sofka.questions.usecases.MapperUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@Service
@Validated
public class CreateUserUseCase implements SaveUser{

    private final UserRepository repository;
    private final MapperUtils mapperUtils;

    public CreateUserUseCase(UserRepository repository, MapperUtils mapperUtils) {
        this.repository = repository;
        this.mapperUtils = mapperUtils;
    }

    @Override
    public Mono<String> apply(UserDTO userDTO) {
        return repository.findByUid(userDTO.getUid())
                .flatMap(user->{
                    return Mono.error(new ResponseStatusException(HttpStatus.CONFLICT));
                }).switchIfEmpty(repository.save(mapperUtils.mapperToUser(null).apply(userDTO))).thenReturn("guardado");




    }
}