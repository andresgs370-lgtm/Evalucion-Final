package co.edu.unbosque.evaluacionfinal.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import co.edu.unbosque.evaluacionfinal.dto.FrutaDTO;
import co.edu.unbosque.evaluacionfinal.model.Fruta;
import co.edu.unbosque.evaluacionfinal.repository.FrutaRepository;

@Service
public class FrutaService implements CRUDOperation<FrutaDTO> {

	private final FrutaRepository frutaRepo;
	private final ModelMapper modelMapper;
	private final SpoonacularService spoonacularService;

	public FrutaService(FrutaRepository frutaRepo, ModelMapper modelMapper, SpoonacularService spoonacularService) {
		this.frutaRepo = frutaRepo;
		this.modelMapper = modelMapper;
		this.spoonacularService = spoonacularService;
	}

	@Override
	public int create(FrutaDTO data) {
		if (datosInvalidos(data)) {
			return 2; // datos inválidos
		}
		if (frutaRepo.findByNombre(data.getNombre()).isPresent()) {
			return 1; // ya existe
		}
		Fruta entity = modelMapper.map(data, Fruta.class);
		entity.setId(null);
		if (entity.getUrlImagen() == null || entity.getUrlImagen().isBlank()) {
			entity.setUrlImagen(spoonacularService.buscarImagen(entity.getNombre()));
		}
		frutaRepo.save(entity);
		return 0;
	}

	@Override
	public List<FrutaDTO> getAll() {
		return frutaRepo.findAll().stream().map(f -> modelMapper.map(f, FrutaDTO.class)).toList();
	}

	public FrutaDTO getById(Long id) {
		return frutaRepo.findById(id).map(f -> modelMapper.map(f, FrutaDTO.class)).orElse(null);
	}

	@Override
	public int deleteById(Long id) {
		Optional<Fruta> found = frutaRepo.findById(id);
		if (found.isPresent()) {
			frutaRepo.delete(found.get());
			return 0;
		}
		return 1;
	}

	@Override
	public int updateById(Long id, FrutaDTO newData) {
		Optional<Fruta> found = frutaRepo.findById(id);
		if (found.isEmpty()) {
			return 1; // no existe
		}
		if (datosInvalidos(newData)) {
			return 2; // datos inválidos
		}
		Fruta temp = found.get();
		temp.setNombre(newData.getNombre());
		temp.setCantidad(newData.getCantidad());
		temp.setPrecio(newData.getPrecio());
		if (newData.getUrlImagen() == null || newData.getUrlImagen().isBlank()) {
			temp.setUrlImagen(spoonacularService.buscarImagen(newData.getNombre()));
		} else {
			temp.setUrlImagen(newData.getUrlImagen());
		}
		frutaRepo.save(temp);
		return 0;
	}

	// Resta stock. 0 = ok, 1 = no existe, 2 = stock insuficiente
	public int restarCantidad(Long id, int cantidad) {
		Optional<Fruta> found = frutaRepo.findById(id);
		if (found.isEmpty()) {
			return 1;
		}
		Fruta temp = found.get();
		if (cantidad <= 0 || temp.getCantidad() < cantidad) {
			return 2;
		}
		temp.setCantidad(temp.getCantidad() - cantidad);
		frutaRepo.save(temp);
		return 0;
	}

	@Override
	public long count() {
		return frutaRepo.count();
	}

	@Override
	public boolean exist(Long id) {
		return frutaRepo.existsById(id);
	}

	private boolean datosInvalidos(FrutaDTO d) {
		return d == null || d.getNombre() == null || d.getNombre().isBlank() || d.getCantidad() < 0
				|| d.getPrecio() < 0;
	}
}