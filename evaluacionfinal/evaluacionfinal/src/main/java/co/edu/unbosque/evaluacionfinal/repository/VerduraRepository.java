package co.edu.unbosque.evaluacionfinal.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.unbosque.evaluacionfinal.model.Verdura;

public interface VerduraRepository extends JpaRepository<Verdura, Long> {
	public Optional<Verdura> findByNombre(String nombre);
}