package com.blog.reactive.service;

import com.blog.reactive.controller.CreateUserRequest;
import com.blog.reactive.controller.userRest;
import com.blog.reactive.models.UserEntity;
import com.blog.reactive.models.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class IUserServiceImpl implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Mono<userRest> createUser(Mono<CreateUserRequest> createUserRequest) {
        // .map is used to transform an element emitted by mono, into another form
        // flat map returns a mono of userEntity
        return createUserRequest.map(createUserRequests -> convertToEntity(createUserRequests)).flatMap(UserEntity -> userRepository.save(UserEntity)).map(UserEntity -> convertToRest(UserEntity));
    }


    private UserEntity convertToEntity(CreateUserRequest createUserRequest) {
        // for beanUtils to work, both objects must have same properties with the same names
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(createUserRequest, userEntity);
        return userEntity;
    }

    private userRest convertToRest(UserEntity userEntity) {
        userRest userRest = new userRest();
        BeanUtils.copyProperties(userEntity, userRest);
        return userRest;
    }
}
