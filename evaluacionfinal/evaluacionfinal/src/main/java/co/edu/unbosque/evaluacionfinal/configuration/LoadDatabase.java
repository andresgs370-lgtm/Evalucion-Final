package co.edu.unbosque.evaluacionfinal.configuration;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import co.edu.unbosque.evaluacionfinal.model.Fruta;
import co.edu.unbosque.evaluacionfinal.model.User;
import co.edu.unbosque.evaluacionfinal.model.Verdura;
import co.edu.unbosque.evaluacionfinal.repository.FrutaRepository;
import co.edu.unbosque.evaluacionfinal.repository.UserRepository;
import co.edu.unbosque.evaluacionfinal.repository.VerduraRepository;
import co.edu.unbosque.evaluacionfinal.service.SpoonacularService;

@Configuration
public class LoadDatabase {
	private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

	@Bean
	CommandLineRunner initDatabase(UserRepository userRepo, PasswordEncoder passwordEncoder,
			FrutaRepository frutaRepo, VerduraRepository verduraRepo, SpoonacularService spoon) {

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
				normalUser.setRole(User.Role.USER);
				userRepo.save(normalUser);
				log.info("Preloading normal user");
			}

			// Productos iniciales de la tienda (la imagen se trae de Spoonacular)
			if (frutaRepo.count() == 0) {
				frutaRepo.save(new Fruta("Manzana", 50, 1200, spoon.buscarImagen("manzana")));
				frutaRepo.save(new Fruta("Banano", 80, 500, spoon.buscarImagen("banano")));
				frutaRepo.save(new Fruta("Fresa", 40, 300, spoon.buscarImagen("fresa")));
				frutaRepo.save(new Fruta("Mango", 30, 2500, spoon.buscarImagen("mango")));
				log.info("Preloading frutas");
			}
			if (verduraRepo.count() == 0) {
				verduraRepo.save(new Verdura("Zanahoria", 60, 400, spoon.buscarImagen("zanahoria")));
				verduraRepo.save(new Verdura("Tomate", 70, 600, spoon.buscarImagen("tomate")));
				verduraRepo.save(new Verdura("Lechuga", 25, 1800, spoon.buscarImagen("lechuga")));
				verduraRepo.save(new Verdura("Brocoli", 20, 3000, spoon.buscarImagen("brocoli")));
				log.info("Preloading verduras");
			}
		};
	}
}