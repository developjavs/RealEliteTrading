package com.realelitetrading.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.realelitetrading.entity.Nivel;
import com.realelitetrading.repository.NivelRepository;

@Service
public class NivelService {

	@Autowired
	private NivelRepository repository;

	public List<Nivel> findAll(){
		return repository.findAll();
	}

	public Optional<Nivel> getNivel(Long id) {
		return repository.findById(id);
	}

	public Nivel save(String clave, String descripcion) {
		Nivel instance = new Nivel();
		instance.setClave(clave);
		instance.setDescripcion(descripcion);
		return repository.save(instance);
	}

	public void update(Nivel dao) {
		Nivel instance = new Nivel();
		instance.setId(dao.getId());
		instance.setClave(dao.getClave());
		instance.setDescripcion(dao.getDescripcion());
		repository.save(instance);
	}
}
