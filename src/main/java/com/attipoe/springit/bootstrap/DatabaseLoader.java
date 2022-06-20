package com.attipoe.springit.bootstrap;

import com.attipoe.springit.domain.Link;
import com.attipoe.springit.domain.Person;
import com.attipoe.springit.domain.Role;
import com.attipoe.springit.repository.CommentRepository;
import com.attipoe.springit.repository.LinkRepository;
import com.attipoe.springit.repository.RoleRepository;
import com.attipoe.springit.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class DatabaseLoader implements CommandLineRunner {

    private LinkRepository linkRepository;
    private CommentRepository commentRepository;
    private UserRepository userRepository;
    private RoleRepository roleRepository;

    public DatabaseLoader(LinkRepository linkRepository, CommentRepository commentRepository, UserRepository userRepository, RoleRepository roleRepository) {
        this.linkRepository = linkRepository;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) {

        // Add users and roles
        addUsersAndRoles();
        Map<String, String> links = new HashMap<>();
        links.put("Securing Spring Boot APIs and SPAs with OAuth 2.0", "https://auth0.com/blog/securing-spring-boot-apis-and-spas-with-oauth2/?utm_source=reddit&utm_medium=sc&utm_campaign=springboot_spa_securing");
        links.put("Easy way to detect Device in Java Web Application using Spring Mobile - Source code to download from GitHub", "https://www.opencodez.com/java/device-detection-using-spring-mobile.htm");
        links.put("Tutorial series about building microservices with SpringBoot (with Netflix OSS)", "https://medium.com/@marcus.eisele/implementing-a-microservice-architecture-with-spring-boot-intro-cdb6ad16806c");
        links.put("Detailed steps to send encrypted email using Java / Spring Boot - Source code to download from GitHub", "https://www.opencodez.com/java/send-encrypted-email-using-java.htm");
        links.put("Build a Secure Progressive Web App With Spring Boot and React", "https://dzone.com/articles/build-a-secure-progressive-web-app-with-spring-boo");
        links.put("Building Your First Spring Boot Web Application - DZone Java", "https://dzone.com/articles/building-your-first-spring-boot-web-application-ex");
        links.put("Building Microservices with Spring Boot Fat (Uber) Jar", "https://jelastic.com/blog/building-microservices-with-spring-boot-fat-uber-jar/");
        links.put("Spring Cloud GCP 1.0 Released", "https://cloud.google.com/blog/products/gcp/calling-java-developers-spring-cloud-gcp-1-0-is-now-generally-available");
        links.put("Simplest way to Upload and Download Files in Java with Spring Boot - Code to download from Github", "https://www.opencodez.com/uncategorized/file-upload-and-download-in-java-spring-boot.htm");
        links.put("Add Social Login to Your Spring Boot 2.0 app", "https://developer.okta.com/blog/2018/07/24/social-spring-boot");
        links.put("File download example using Spring REST Controller", "https://www.jeejava.com/file-download-example-using-spring-rest-controller/");

        links.forEach((k, v) -> {
            linkRepository.save(new Link(k, v));
            // we will do something with comments later
        });

        long linkCount = linkRepository.count();
        System.out.println("Number of links in the database: " + linkCount);
    }

    private void addUsersAndRoles() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String secret = "{bcrypt}" + encoder.encode("password");

        Role userRole = new Role("ROLE_USER");
        roleRepository.save(userRole);
        Role adminRole = new Role("ROLE_ADMIN");
        roleRepository.save(adminRole);

        Person user = new Person("user@gmail.com", secret, true);
        user.addRole(userRole);
        userRepository.save(user);

        Person admin = new Person("admin@gmail.com", secret, true);
        admin.addRole(adminRole);
        userRepository.save(admin);

        Person master = new Person("master@gmail.com", secret, true);
        master.addRole(adminRole);
        master.addRoles(new HashSet<>(Arrays.asList(userRole,adminRole)));
    }
}