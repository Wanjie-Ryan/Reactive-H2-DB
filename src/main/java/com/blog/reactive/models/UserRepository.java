package com.blog.reactive.models;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

import java.util.UUID;

// ReactiveCrudRepository comes from the Spring Data R2DBC
public interface UserRepository extends ReactiveCrudRepository<UserEntity, UUID> {
    // this custom repository method is used to return all records but paginated in this case so as to reduce stress on the server, by help of the argument Pageable, it returns a Paginated result
    Flux<UserEntity> findAllBy(Pageable pageable);
}
