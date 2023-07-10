package com.realelitetrading.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.realelitetrading.entity.Rol;
import com.realelitetrading.model.RolDao;
import com.realelitetrading.repository.RolRepository;

@Service
public class RolService {

	@Autowired
	private RolRepository repository;

	public List<Rol> findAll(){
		return repository.findAll();
	}

	public Optional<Rol> getRol(Long id) {
		return repository.findById(id);
	}

	public Rol save(String clave, String descripcion) {
		Rol instance = new Rol();
		instance.setClave(clave);
		instance.setDescripcion(descripcion);
		return repository.save(instance);
	}

	public void update(RolDao dao) {
		Rol instance = new Rol();
		instance.setId(dao.getId());
		instance.setClave(dao.getClave());
		instance.setDescripcion(dao.getDescripcion());
		repository.save(instance);
	}
}
