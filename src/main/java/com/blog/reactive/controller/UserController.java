package com.blog.reactive.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/users")
public class UserController {

    @PostMapping
    // request body annotation deserializes a json into java object
    // the valid annotation tells spring to validate the incoming JSON object
    // because we are using mono, it will not emit data until there is an active subscriber
    // mono is like a container that will hold value, whilst String is the type of value being returned.
    public Mono<userRest>  createUser(@RequestBody @Valid Mono<CreateUserRequest> createUserRequest ){
//        return Mono.just("User created");
        // the above code of line means that it emits only one string which is user created.
    }


}
