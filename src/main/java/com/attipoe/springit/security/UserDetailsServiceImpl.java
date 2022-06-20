package com.attipoe.springit.security;

import com.attipoe.springit.domain.Person;
import com.attipoe.springit.repository.UserRepository;
import org.springframework.format.number.PercentStyleFormatter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private UserRepository repository;

    public UserDetailsServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Person> person = repository.findByEmail(email);
        if(!person.isPresent()) {
            throw  new UsernameNotFoundException(email);
        }
        return person.get();
    }
}
