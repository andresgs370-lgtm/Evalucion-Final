package co.edu.unbosque.evaluacionfinal.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import co.edu.unbosque.evaluacionfinal.dto.UserDTO;
import co.edu.unbosque.evaluacionfinal.model.User;
import co.edu.unbosque.evaluacionfinal.repository.UserRepository;

@Service
public class UserService implements CRUDOperation<UserDTO> {

	private final UserRepository userRepo;
	private final ModelMapper modelMapper;
	private final PasswordEncoder passwordEncoder;

	public UserService(UserRepository userRepo, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
		this.userRepo = userRepo;
		this.modelMapper = modelMapper;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public long count() {
		return userRepo.count();
	}

	@Override
	public boolean exist(Long id) {
		return userRepo.existsById(id);
	}

	@Override
	public int create(UserDTO data) {
		User entity = modelMapper.map(data, User.class);
		if (findUsernameAlreadyTaken(entity)) {
			return 1;
		}
		entity.setPassword(passwordEncoder.encode(entity.getPassword()));
		userRepo.save(entity);
		return 0;
	}

	@Override
	public List<UserDTO> getAll() {
		return userRepo.findAll().stream().map(this::toDtoSinPassword).toList();
	}

	@Override
	public int deleteById(Long id) {
		Optional<User> found = userRepo.findById(id);
		if (found.isPresent()) {
			userRepo.delete(found.get());
			return 0;
		}
		return 1;
	}

	public int deleteByUsername(String username) {
		Optional<User> found = userRepo.findByUsername(username);
		if (found.isPresent()) {
			userRepo.delete(found.get());
			return 0;
		}
		return 1;
	}

	@Override
	public int updateById(Long id, UserDTO newData) {
		Optional<User> found = userRepo.findById(id);
		if (found.isEmpty()) {
			return 2; // el usuario a actualizar no existe
		}
		if (userRepo.findByUsername(newData.getUsername()).isPresent()) {
			return 1; // el nuevo username ya está en uso
		}
		User temp = found.get();
		temp.setUsername(newData.getUsername());
		temp.setPassword(passwordEncoder.encode(newData.getPassword()));
		userRepo.save(temp);
		return 0;
	}

	public UserDTO getById(Long id) {
		return userRepo.findById(id).map(this::toDtoSinPassword).orElse(null);
	}

	public boolean findUsernameAlreadyTaken(User newUser) {
		return userRepo.findByUsername(newUser.getUsername()).isPresent();
	}

	public boolean findUsernameAlreadyTaken(String username) {
		return userRepo.findByUsername(username).isPresent();
	}

	public int validateCredentials(String username, String password) {
		Optional<User> userOpt = userRepo.findByUsername(username);
		if (userOpt.isPresent() && passwordEncoder.matches(password, userOpt.get().getPassword())) {
			return 0; // credenciales correctas
		}
		return 1; // credenciales inválidas
	}

	// Convierte a DTO y borra la contraseña (el hash nunca debe salir en una respuesta)
	private UserDTO toDtoSinPassword(User entity) {
		UserDTO dto = modelMapper.map(entity, UserDTO.class);
		dto.setPassword(null);
		return dto;
	}
}