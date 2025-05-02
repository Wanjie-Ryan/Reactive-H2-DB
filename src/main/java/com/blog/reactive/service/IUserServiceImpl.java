package com.blog.reactive.service;

import com.blog.reactive.controller.CreateUserRequest;
import com.blog.reactive.controller.userRest;
import com.blog.reactive.models.UserEntity;
import com.blog.reactive.models.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.UUID;

@Service
public class IUserServiceImpl implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Mono<userRest> createUser(Mono<CreateUserRequest> createUserRequest) {
        // .map is used to transform an element emitted by mono, into another form
        // flat map returns a mono of userEntity
        return createUserRequest.flatMap(createUserRequests -> convertToEntity(createUserRequests)).flatMap(UserEntity -> userRepository.save(UserEntity)).map(UserEntity -> convertToRest(UserEntity)).onErrorMap(throwable -> {
            if (throwable instanceof DuplicateKeyException) {
                return new ResponseStatusException(HttpStatus.CONFLICT, throwable.getMessage());
            } else if (throwable instanceof DataIntegrityViolationException) {
                return new ResponseStatusException(HttpStatus.BAD_REQUEST, throwable.getMessage());
            } else {
                return new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, throwable.getMessage());
            }
        });

        // to catch a more general exception, one can use the throwable keyword
    }

    @Override
    public Mono<userRest> getUserById(UUID id) {
        // at this point alone, without the .map method,, an entity is returned, now you need to convert this returned entity to a userRest which is return type needed in this function
        // if the record was not found, it will return an empty mono and flow completes
        return userRepository.findById(id).map(userEntity -> convertToRest(userEntity));
    }

    @Override
    public Flux<userRest> findAll(int page, int limit) {
        // create first a pageable request object
//        This line creates a Pageable object using PageRequest.of(page, limit). PageRequest is a utility class provided by Spring Data to create a Pageable instance. It essentially converts the page number (page) and the number of records per page (limit) into a Pageable object that can be used in the query method.
        if (page > 0) page = page - 1;
        // the reason for the if statement above is cause,,, by default sql db starts fetching the pagination records as from 0 index,,, the user may pass page = 1 and expect it to return him records for page 1, but n this case cause db expects records from page 0 hence when 1 is passed it becomes 1-1 hence db fetches records from page 0 which are records from page 1 from users POV
        Pageable pageable = PageRequest.of(page, limit);
        return userRepository.findAllBy(pageable).map(userEntity -> convertToRest(userEntity));
    }


    private Mono<UserEntity> convertToEntity(CreateUserRequest createUserRequest) {
        // form callable is used to wrap potentially blocking operations in a way that they don't execute immediately. it waits to execute until its needed.

        return Mono.fromCallable(()->{

        // for beanUtils to work, both objects must have same properties with the same names
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(createUserRequest, userEntity);
        userEntity.setPassword(passwordEncoder.encode(createUserRequest.getPassword()));
        return userEntity;
        }).subscribeOn(Schedulers.boundedElastic());
    }

    private userRest convertToRest(UserEntity userEntity) {
        userRest userRest = new userRest();
        BeanUtils.copyProperties(userEntity, userRest);
        return userRest;
    }
}
