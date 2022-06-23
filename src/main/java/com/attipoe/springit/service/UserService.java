package com.attipoe.springit.service;

import com.attipoe.springit.domain.Person;
import com.attipoe.springit.domain.Role;
import com.attipoe.springit.repository.RoleRepository;
import com.attipoe.springit.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    private final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository  userRepository;
    private final RoleService roleService;
    private final BCryptPasswordEncoder encoder;

    private final MailService mailService;


    public UserService(UserRepository userRepository, RoleService roleService, MailService mailService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.mailService = mailService;
        this.encoder = new BCryptPasswordEncoder();
    }
    public Person register(Person user) {
        String secret = "{bcrypt}" + encoder.encode(user.getPassword());
        user.setPassword(secret);
        // confirm password
        user.setConfirmPassword(secret);
        // assign a role to this user
        user.addRole(roleService.findByName("ROLE_USER"));
        // set an activation code
        user.setActivationCode(UUID.randomUUID().toString());
        // disable use
        user.setEnabled(false);
        save(user);
        // send the activation email
        sendActivationEmail(user);
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
    private void sendActivationEmail(Person user) {
        mailService.sendActivationEmail(user);
    }
    public void sendWelcomeEmail(Person user) {
        mailService.sendWelcomeEmail(user);
    }

    public Optional<Person> findByEmailAndActivationCode(String email, String activationCode) {
        return userRepository.findByEmailAndActivationCode(email, activationCode);
    }
}
