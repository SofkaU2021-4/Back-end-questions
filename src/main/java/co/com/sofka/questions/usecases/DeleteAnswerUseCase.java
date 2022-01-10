package co.com.sofka.questions.usecases;

import co.com.sofka.questions.reposioties.AnswerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.function.Function;
@Service
@Validated
public class DeleteAnswerUseCase implements Function<String, Mono<String>> {

    private final AnswerRepository answerRepository;

    public DeleteAnswerUseCase(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }


    @Override
    public Mono<String> apply(String id) {
        Objects.requireNonNull(id, "Id is required");

        return answerRepository.findById(id)
                .flatMap(question -> {
                    return answerRepository.deleteById(id).thenReturn(question.getQuestionId());
                })
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND , "id erroneo")));

    }
}
