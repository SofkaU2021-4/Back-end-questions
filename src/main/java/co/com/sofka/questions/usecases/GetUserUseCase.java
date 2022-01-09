package co.com.sofka.questions.usecases;

import co.com.sofka.questions.model.UserDTO;
import co.com.sofka.questions.reposioties.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Service
@Validated
public class GetUserUseCase implements Function<String, Mono<UserDTO>> {

    private final UserRepository repository;
    private final MapperUtils mapperUtils;

    public GetUserUseCase(UserRepository repository, MapperUtils mapperUtils) {
        this.repository = repository;
        this.mapperUtils = mapperUtils;
    }

    @Override
    public Mono<UserDTO> apply(String s) {
        return repository.findByUid(s)
                .map(mapperUtils.mapEntityToUser())
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }
}