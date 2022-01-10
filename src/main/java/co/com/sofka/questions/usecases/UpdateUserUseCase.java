package co.com.sofka.questions.usecases;


import co.com.sofka.questions.collections.User;
import co.com.sofka.questions.model.UserDTO;
import co.com.sofka.questions.reposioties.UserRepository;
import com.mongodb.Function;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
@Validated
public class UpdateUserUseCase implements Function<UserDTO,Mono<UserDTO>> {

    private final UserRepository repository;
    private final MapperUtils mapperUtils;


    public UpdateUserUseCase(UserRepository repository, MapperUtils mapperUtils) {
        this.repository = repository;
        this.mapperUtils = mapperUtils;
    }


    @Override
    public Mono<UserDTO> apply(UserDTO userDTO) {
        Objects.requireNonNull(userDTO.getUid(), "Id of the question is required");
       return repository.findByUid(userDTO.getUid())
                .flatMap(user -> {
                    user.setName(userDTO.getName());
                    return repository.save(user);
                }).map(mapperUtils.mapEntityToUser());
    }
}