package co.com.sofka.questions.usecases;


import co.com.sofka.questions.collections.User;
import co.com.sofka.questions.model.UserDTO;
import co.com.sofka.questions.reposioties.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
@Validated
public class UpdateUserUseCase implements SaveUser{

    private final UserRepository repository;
    private final MapperUtils mapperUtils;


    public UpdateUserUseCase(UserRepository repository, MapperUtils mapperUtils) {
        this.repository = repository;
        this.mapperUtils = mapperUtils;
    }


    @Override
    public Mono<String> apply(UserDTO userDTO) {
        Objects.requireNonNull(userDTO.getId(), "Id of the question is required");
        return repository
                .save(mapperUtils.mapperToUser(userDTO.getId()).apply(userDTO))
                .map(User::getId);
    }
}