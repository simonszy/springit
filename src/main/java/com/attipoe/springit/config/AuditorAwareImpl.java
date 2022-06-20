package com.attipoe.springit.config;

import com.attipoe.springit.domain.Person;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        if(SecurityContextHolder.getContext().getAuthentication() == null) {
            return Optional.of("master@gmail.com");
        }
        return Optional.of(((Person) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal()).getEmail());
    }
}
