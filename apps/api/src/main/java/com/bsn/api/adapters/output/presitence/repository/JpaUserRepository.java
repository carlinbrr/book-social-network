package com.bsn.api.adapters.output.presitence.repository;

import com.bsn.api.adapters.output.presitence.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaUserRepository extends JpaRepository<User, String> {

    Optional<User> findByKeycloakId(String keycloakId);

}
