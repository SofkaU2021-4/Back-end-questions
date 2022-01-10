package co.com.sofka.questions.usecases;

import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.reposioties.QuestionRepository;
import co.com.sofka.questions.reposioties.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;

import java.util.function.Function;

@Service
@Validated
public class OwnerListUseCase implements Function<String, Flux<QuestionDTO>> {
    private final QuestionRepository questionRepository;
    private final MapperUtils mapperUtils;
    private final UserRepository userRepository;

    public OwnerListUseCase(MapperUtils mapperUtils, QuestionRepository questionRepository, UserRepository userRepository) {
        this.questionRepository = questionRepository;
        this.mapperUtils = mapperUtils;
        this.userRepository = userRepository;
    }


    @Override
    public Flux<QuestionDTO> apply(String userId) {
        return questionRepository.findByUserId(userId)
                .map(mapperUtils.mapEntityToQuestion())
                .flatMap(questionDTO -> {
                    return userRepository.findByUid(questionDTO.getUserId())
                            .map(user -> {
                                questionDTO.setUserDTO(mapperUtils.mapEntityToUser().apply(user));
                                return questionDTO;
                            });
                });
    }
}
