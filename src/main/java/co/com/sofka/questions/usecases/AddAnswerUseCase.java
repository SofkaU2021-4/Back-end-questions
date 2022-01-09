package co.com.sofka.questions.usecases;

import co.com.sofka.questions.Service.SendMailService;
import co.com.sofka.questions.model.AnswerDTO;
import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.reposioties.AnswerRepository;
import co.com.sofka.questions.reposioties.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
@Validated
public class AddAnswerUseCase implements SaveAnswer {
    private final AnswerRepository answerRepository;
    private final MapperUtils mapperUtils;
    private final GetUseCase getUseCase;
    private final SendMailService sendMailService;
    private final UserRepository userRepository;


    public AddAnswerUseCase(MapperUtils mapperUtils, GetUseCase getUseCase, AnswerRepository answerRepository, SendMailService sendMailService, UserRepository userRepository) {
        this.answerRepository = answerRepository;
        this.getUseCase = getUseCase;
        this.mapperUtils = mapperUtils;
        this.sendMailService = sendMailService;
        this.userRepository = userRepository;
    }

    public Mono<QuestionDTO> apply(AnswerDTO answerDTO) {
        Objects.requireNonNull(answerDTO.getQuestionId(), "Id of the answer is required");
        return  userRepository.findByUid(answerDTO.getUserId())
                .flatMap(user -> {
                    return answerRepository.save(mapperUtils.mapperToAnswer().apply(answerDTO))
                            .flatMap(answer -> {
                                return getUseCase.apply(answer.getQuestionId())
                                        .flatMap(questionDTO -> {
                                            sendMailService.sendMail(
                                                    user.getEmail(),
                                                    "Han respondido a tu pregunta: " + questionDTO.getQuestion(),
                                                    "Respuesta: \n" + answerDTO.getAnswer());
                                            return Mono.just(questionDTO);
                                        });
                            });

                });
    }


}


