package com.blog.reactive.service;

import com.blog.reactive.controller.CreateUserRequest;
import com.blog.reactive.controller.userRest;
import reactor.core.publisher.Mono;

public interface IUserService {
    Mono<userRest> createUser(Mono<CreateUserRequest> createUserRequest);
}
