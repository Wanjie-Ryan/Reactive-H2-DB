package com.blog.reactive.models;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.util.UUID;

// ReactiveCrudRepository comes from the Spring Data R2DBC
public interface UserRepository extends ReactiveCrudRepository<UserEntity, UUID> {
}
