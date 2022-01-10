package co.com.sofka.questions.usecases;

import co.com.sofka.questions.model.AnswerDTO;
import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.reposioties.AnswerRepository;
import co.com.sofka.questions.reposioties.QuestionRepository;
import co.com.sofka.questions.reposioties.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.function.Function;

@Service
@Validated
public class GetUseCase implements Function<String, Mono<QuestionDTO>> {
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final MapperUtils mapperUtils;
    private final UserRepository userRepository;

    public GetUseCase(MapperUtils mapperUtils, QuestionRepository questionRepository, AnswerRepository answerRepository, UserRepository userRepository) {
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
        this.mapperUtils = mapperUtils;
        this.userRepository = userRepository;
    }

    @Override
    public Mono<QuestionDTO> apply(String id) {
        Objects.requireNonNull(id, "Id is required");
        return questionRepository.findById(id)
                .map(mapperUtils.mapEntityToQuestion())
                .flatMap(mapQuestionAggregate())
                .flatMap(mapUserQuestion());
    }

    private Function<QuestionDTO, Mono<QuestionDTO>> mapUserQuestion() {
        return questionDTO ->
                Mono.just(questionDTO).zipWith(
                        userRepository.findByUid(questionDTO.getUserId())
                                .map(mapperUtils.mapEntityToUser())
                            , (question, user) -> {
                            question.setUserDTO(user);
                            return question;
                        }
                );
    }

    private Function<QuestionDTO, Mono<QuestionDTO>> mapQuestionAggregate() {
        return questionDTO ->
                Mono.just(questionDTO).zipWith(
                        answerRepository.findAllByQuestionId(questionDTO.getId())
                                .map(mapperUtils.mapEntityToAnswer())
                                .flatMap(mapUser())
                                .collectList(),
                        (question, answers) -> {
                            question.setAnswers(answers);
                            return question;
                        }
                );
    }

    private Function<AnswerDTO, Mono<AnswerDTO>> mapUser() {
        return answerDTO ->
                Mono.just(answerDTO).zipWith(
                        userRepository.findByUid(answerDTO.getUserId())
                                .map(mapperUtils.mapEntityToUser())
                                , (answer, user) -> {
                            answer.setUserDTO(user);
                            return answer;
                        }
                );
    }



}
