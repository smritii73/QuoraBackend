package com.example.QuoraApp.services;

import com.example.QuoraApp.adapter.AnswerAdapter;
import com.example.QuoraApp.dto.AnswerRequestDto;
import com.example.QuoraApp.dto.AnswerResponseDto;
import com.example.QuoraApp.models.Answer;
import com.example.QuoraApp.repositories.AnswerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AnswerService implements IAnswerService {

    private final AnswerRepository answerRepository;
    private final IUserService userService;

    @Override
    public Mono<AnswerResponseDto> createAnswer(AnswerRequestDto answerRequestDto) {
        return userService.getUserById(answerRequestDto.getCreatedById())
                .switchIfEmpty(Mono.error(new RuntimeException("User doesnt exist")))
                .flatMap(userResponseDto->{
                    return answerRepository.save(AnswerAdapter.toEntity(answerRequestDto))
                            .map(answer-> AnswerAdapter.toDto(answer, userResponseDto));
                })
                .doOnSuccess(response -> System.out.println("Answer created successfully: " + response))
                .doOnError(error-> System.out.println("Error faced : "+ error));
    }

    @Override
    public Mono<AnswerResponseDto> getAnswersById(String id) {
        return answerRepository.findById(id)
                // can also be written as .flatMap(answer-> enrichAnswerWithUserResponseDto(answer)) this is the lambda reference
                .flatMap(this::enrichAnswerWithUserResponseDto)
                // this is method reference
                // sinc ehte enrichAsnwerWithUserResponseDto is present in this class itself, we use this
                .switchIfEmpty(Mono.error(new RuntimeException("Answer with Id : " + id + " doesn't exists")))
                .doOnSuccess(response -> System.out.println("Answer retrieved successfully: " + response))
                .doOnError(error -> System.out.println("Error faced : "+ error));
    }

    @Override
    public Flux<AnswerResponseDto> getAllAnswersByQuestionId(String questionId){
        return answerRepository.findByQuestionId(questionId)
                .flatMap(this::enrichAnswerWithUserResponseDto)
                // toDto is the static method reference of the AnswerAdapter class
                .switchIfEmpty(Flux.error(new RuntimeException("Answer with questionId : " + questionId + " doesn't exists")))
                .doOnNext(response -> System.out.println("Answer retrieved successfully: " + response))
                .doOnComplete(() -> System.out.println("Answer retrieved successfully"))
                .doOnError(error -> System.out.println("Error faced : "+ error));
    }

    public Mono<AnswerResponseDto> enrichAnswerWithUserResponseDto(Answer answer){
        /* Lets focus on what we have and then try to get what we need
           We have answer and we want Mono<AnswerResponseDto>
           Is there a way to convert Answer to AnswerResponseDto
           Yes there is a way to convert answer to AnswerResponseDto using AnswerAdapter.toDto() but for that we also require
           UserResponseDto
           ok. so we need to get UserResponseDto. now the question is how to get UserResponseDto?
           now we are talking about userReponseDto viz related to User. so we focus on user related files.
           now the question arises which user? do we have any info about the user which we need?
           We have id of user present in answer variable. We can get it using getter() ie. answer.getCreatdById()
           Now the question arises how to get UserResponseDto from createdById.
           Now lets think it simple. Instead of getting userResponseDto, lets get user from createdById aka userId
           Now the question arises how to get user from userId?
           Think like this User is a model and we need a user variable and we have an id so we can use the database to get it
           as its stored there. But repo talks with Db and we cannt directly talk w db, so we use Repo.
           specifically UserRepo and not the AnswerRepo.
           we can use userRepository.findById(answer.getCreatedById()), so we will get Mono<User> from this call
           but we want Mono<UserResponseDto>
           so the question arises how to convert the Mono<User> to Mono<UserResponseDto> ?
           The solution is : As we talk about User, we use the UserAdapter and can convert the Mono<User> to Mono<UserResponseDto>
           using the toDto()
           so using answerAdapter.toDto() we got userResponseDto.
           and now coming back to answerResponseDto, now we can use answerAdapter.toDto as we have the userResponseDto in it
           but calling the userRepository, then getting the User and then using UserAdapter to get UserResponseDto,
           so if we carefully see, we have already done this in userService.getUserById() so we dont have to duplicate the code
           and introduce the code redundancy and we have to follow the DRY principle
           we can use userService.getUserById(answer.getCreatedById()) and directly get userResponseDto and avoid step repetition

           userRepository.getUserById(answer.getCreatedById()) -> we get Mono<UserResponseDto>
           now we have a value in Mono and we have to take it out and convert it so we use map
           return userRepository.getUserById(answer.getCreatedById())
           .map(userReponseDto->{
            answerAdapter.toDto(answer,userResponseDto) -> we get AnswerResponseDto
           })
       */
        return userService.getUserById(answer.getCreatedById())
                .map(userResponseDto-> AnswerAdapter.toDto(answer,userResponseDto));


    }
}