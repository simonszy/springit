package com.attipoe.springit.repository;

import com.attipoe.springit.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Person, Long> {
   Optional<Person> findByEmail(String email);
   Optional<Person> findByEmailAndActivationCode(String email, String activationCode);
}
