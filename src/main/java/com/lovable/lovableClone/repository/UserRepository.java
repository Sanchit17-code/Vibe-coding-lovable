package com.lovable.lovableClone.repository;

import com.lovable.lovableClone.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository // just added for the sake of consistence, not needed as it is an interface
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
