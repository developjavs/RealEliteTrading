package com.realelitetrading.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.realelitetrading.entity.Tema;
import com.realelitetrading.repository.TemaRepository;

@Service
public class TemaService {

	@Autowired
	private TemaRepository repository;

	public List<Tema> findAll(){
		return repository.findAll();
	}

	public Optional<Tema> getTema(Long id) {
		return repository.findById(id);
	}

	public Tema save(String clave, String nombre, String descripcion) {
		Tema instance = new Tema();
		instance.setClave(clave);
		instance.setNombre(nombre);
		instance.setDescripcion(descripcion);
		return repository.save(instance);
	}

	public void update(Tema dao) {
		Tema instance = new Tema();
		instance.setId(dao.getId());
		instance.setClave(dao.getClave());
		instance.setNombre(dao.getNombre());
		instance.setDescripcion(dao.getDescripcion());
		repository.save(instance);
	}
}
