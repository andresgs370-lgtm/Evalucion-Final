package co.edu.unbosque.evaluacionfinal.configuration;

import java.util.Optional; 

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import co.edu.unbosque.evaluacionfinal.model.User;
import co.edu.unbosque.evaluacionfinal.repository.UserRepository;

@Configuration
public class LoadDatabase {
	private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

	@Bean
	CommandLineRunner initDatabase(UserRepository userRepo, PasswordEncoder passwordEncoder) {

		return args -> {
			Optional<User> found = userRepo.findByUsername("admin");
			if (found.isPresent()) {
				log.info("Admin already exists, skipping admin creating ...");
			} else {
			
				User adminUser = new User("admin", passwordEncoder.encode("1234567890"));
				adminUser.setRole(User.Role.ADMIN); 
				
				userRepo.save(adminUser);
				log.info("Preloading admin user");
			}

						Optional<User> foundUser = userRepo.findByUsername("user");
			if (foundUser.isEmpty()) {
				User normalUser = new User("user", passwordEncoder.encode("user123"));
				normalUser.setRole(User.Role.USER); // Rol normal
				userRepo.save(normalUser);
				log.info("Preloading normal user");
			}
		};
	}
}