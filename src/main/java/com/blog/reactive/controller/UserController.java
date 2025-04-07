package com.blog.reactive.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    @PostMapping
    // request body annotation deserializes a json into java object
    // the valid annotation tells spring to validate the incoming JSON object
    // because we are using mono, it will not emit data until there is an active subscriber
    // mono is like a container that will hold value, whilst String is the type of value being returned.

    // making the function return a specific HTTP status
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<userRest> createUser(@RequestBody @Valid Mono<CreateUserRequest> createUserRequest) {
//        return Mono.just("User created");
        // the above code of line means that it emits only one string which is user created.
        // take createUserRequest object and transform it to UserRest
        // the map request takes content from the userRequest object, changes it UserRest object
        return createUserRequest.map(request -> new userRest(UUID.randomUUID(), request.getFirstName(), request.getLastName(), request.getEmail()));
    }


}
