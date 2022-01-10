package co.com.sofka.questions.usecases;

import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.reposioties.AnswerRepository;
import co.com.sofka.questions.reposioties.QuestionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.function.Function;

@Service
@Validated
public class DeleteUseCase implements Function<String, Mono<QuestionDTO>> {
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private  final  MapperUtils mapperUtils;

    public DeleteUseCase(AnswerRepository answerRepository, QuestionRepository questionRepository, MapperUtils mapperUtils) {
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
        this.mapperUtils = mapperUtils;
    }


    @Override
    public Mono<QuestionDTO> apply(String id) {
        Objects.requireNonNull(id, "Id is required");

        return questionRepository.findById(id)
                .flatMap(question -> {
                    return questionRepository.deleteById(id)
                            .and(answerRepository.deleteAllByQuestionId(id)).thenReturn(question).map(mapperUtils.mapEntityToQuestion());
                })
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND , "id erroneo")));
    }
}
