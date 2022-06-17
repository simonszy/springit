package com.attipoe.springit;

import com.attipoe.springit.domain.Comment;
import com.attipoe.springit.domain.Link;
import com.attipoe.springit.repository.CommentRepository;
import com.attipoe.springit.repository.LinkRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SpringitApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringitApplication.class, args);
		System.out.println("Welcome to Springit!");
	}

	@Bean
	CommandLineRunner runner(LinkRepository linkRepository, CommentRepository commentRepository) {
		return args -> {
			Link link = new Link("Getting Started With Spring Boot 2", "http://setriakor.amegaxoxo.com");
			linkRepository.save(link);

			Comment comment = new Comment("This link is awesome.", link);
			commentRepository.save(comment);

			link.addComment(comment);
			System.out.println("We just inserted a link and a comment.");
		};
	}

}
