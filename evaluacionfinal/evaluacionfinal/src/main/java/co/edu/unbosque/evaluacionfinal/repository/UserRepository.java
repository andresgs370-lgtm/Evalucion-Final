package co.edu.unbosque.evaluacionfinal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.unbosque.evaluacionfinal.model.User;

import java.util.Optional;

public interface UserRepository  extends JpaRepository<User, Long> {
	public Optional<User> findByUsername(String username);
	public void deleteByUsername(String username); 
}
