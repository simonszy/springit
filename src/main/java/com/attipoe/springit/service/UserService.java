package com.attipoe.springit.service;

import com.attipoe.springit.domain.Person;
import com.attipoe.springit.repository.RoleRepository;
import com.attipoe.springit.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    private final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository  userRepository;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public Person register(Person user) {
        return user;
    }

    public Person save(Person user) {
        userRepository.save(user);
        return user;
    }

    @Transactional
    public void sveUsers(Person... users) {
        for (Person user : users) {
            logger.info("Saving user: " + user.getEmail());
            userRepository.save(user);
        }
    }
}
