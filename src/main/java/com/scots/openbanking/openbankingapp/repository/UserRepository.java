package com.scots.openbanking.openbankingapp.repository;

import com.scots.openbanking.openbankingapp.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByEmail(String email);
}
