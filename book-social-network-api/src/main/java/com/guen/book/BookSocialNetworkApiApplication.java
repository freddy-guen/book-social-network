package com.guen.book;

import com.guen.book.role.Role;
import com.guen.book.role.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableJpaAuditing
@EnableAsync
public class BookSocialNetworkApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookSocialNetworkApiApplication.class, args);
	}

	//TODO : A supprimer avant la prod
	// C'est juste pour initialiser le role au debut. Logiquement en prod je dois passer par
	// un Guide de mise en oeuvre avec un script qui initialise ces roles (et d'autres params si nécessaires)
	@Bean
	public CommandLineRunner runner(RoleRepository roleRepository)
	{
		return args -> {
			if (roleRepository.findByName("USER").isEmpty())
			{
				roleRepository.save(Role.builder().name("USER").build());
			}
		};
	}
}
