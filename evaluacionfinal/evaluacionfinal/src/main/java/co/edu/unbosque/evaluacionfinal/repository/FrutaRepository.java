package co.edu.unbosque.evaluacionfinal.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.unbosque.evaluacionfinal.model.Fruta;

public interface FrutaRepository extends JpaRepository<Fruta, Long> {
	public Optional<Fruta> findByNombre(String nombre);
}