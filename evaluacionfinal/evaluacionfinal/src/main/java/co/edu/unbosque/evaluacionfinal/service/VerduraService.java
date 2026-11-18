package co.edu.unbosque.evaluacionfinal.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import co.edu.unbosque.evaluacionfinal.dto.VerduraDTO;
import co.edu.unbosque.evaluacionfinal.model.Verdura;
import co.edu.unbosque.evaluacionfinal.repository.VerduraRepository;

@Service
public class VerduraService implements CRUDOperation<VerduraDTO> {

	private final VerduraRepository verduraRepo;
	private final ModelMapper modelMapper;
	private final SpoonacularService spoonacularService;

	public VerduraService(VerduraRepository verduraRepo, ModelMapper modelMapper, SpoonacularService spoonacularService) {
		this.verduraRepo = verduraRepo;
		this.modelMapper = modelMapper;
		this.spoonacularService = spoonacularService;
	}

	@Override
	public int create(VerduraDTO data) {
		if (datosInvalidos(data)) {
			return 2;
		}
		if (verduraRepo.findByNombre(data.getNombre()).isPresent()) {
			return 1;
		}
		Verdura entity = modelMapper.map(data, Verdura.class);
		entity.setId(null);
		if (entity.getUrlImagen() == null || entity.getUrlImagen().isBlank()) {
			entity.setUrlImagen(spoonacularService.buscarImagen(entity.getNombre()));
		}
		verduraRepo.save(entity);
		return 0;
	}

	@Override
	public List<VerduraDTO> getAll() {
		return verduraRepo.findAll().stream().map(v -> modelMapper.map(v, VerduraDTO.class)).toList();
	}

	public VerduraDTO getById(Long id) {
		return verduraRepo.findById(id).map(v -> modelMapper.map(v, VerduraDTO.class)).orElse(null);
	}

	@Override
	public int deleteById(Long id) {
		Optional<Verdura> found = verduraRepo.findById(id);
		if (found.isPresent()) {
			verduraRepo.delete(found.get());
			return 0;
		}
		return 1;
	}

	@Override
	public int updateById(Long id, VerduraDTO newData) {
		Optional<Verdura> found = verduraRepo.findById(id);
		if (found.isEmpty()) {
			return 1;
		}
		if (datosInvalidos(newData)) {
			return 2;
		}
		Verdura temp = found.get();
		temp.setNombre(newData.getNombre());
		temp.setCantidad(newData.getCantidad());
		temp.setPrecio(newData.getPrecio());
		if (newData.getUrlImagen() == null || newData.getUrlImagen().isBlank()) {
			temp.setUrlImagen(spoonacularService.buscarImagen(newData.getNombre()));
		} else {
			temp.setUrlImagen(newData.getUrlImagen());
		}
		verduraRepo.save(temp);
		return 0;
	}

	public int restarCantidad(Long id, int cantidad) {
		Optional<Verdura> found = verduraRepo.findById(id);
		if (found.isEmpty()) {
			return 1;
		}
		Verdura temp = found.get();
		if (cantidad <= 0 || temp.getCantidad() < cantidad) {
			return 2;
		}
		temp.setCantidad(temp.getCantidad() - cantidad);
		verduraRepo.save(temp);
		return 0;
	}

	@Override
	public long count() {
		return verduraRepo.count();
	}

	@Override
	public boolean exist(Long id) {
		return verduraRepo.existsById(id);
	}

	private boolean datosInvalidos(VerduraDTO d) {
		return d == null || d.getNombre() == null || d.getNombre().isBlank() || d.getCantidad() < 0
				|| d.getPrecio() < 0;
	}
}