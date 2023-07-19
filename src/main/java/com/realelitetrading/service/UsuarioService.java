package com.realelitetrading.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.realelitetrading.entity.Rol;
import com.realelitetrading.entity.Usuario;
import com.realelitetrading.model.UsuarioDao;
import com.realelitetrading.repository.RolRepository;
import com.realelitetrading.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository repository;

	@Autowired
	private RolRepository rolRepository;

	public List<Usuario> findAll(){
		return repository.findAll();
	}

	public Optional<Usuario> getUsuario(Long id) {
		return repository.findById(id);
	}

	public Usuario save(UsuarioDao dao) {
		Usuario user = repository.findByCorreo(dao.getCorreo());
		if(user == null) {
			Optional<Rol> rol = rolRepository.findById(dao.getIdRol());
			Usuario instance = new Usuario();
			instance.setNivel(dao.getIdNivel());
			instance.setNombre(dao.getNombre());
			instance.setAp(dao.getAp());
			instance.setAm(dao.getAm());
			instance.setFechaNac(dao.getFechaNac());
			instance.setEstatus(0);
			instance.setCorreo(dao.getCorreo());
			instance.setPassword(dao.getPassword());
			instance.setRol(rol.get());	
			return repository.save(instance);
		}else{
			return null;
		}
	}

	public void update(UsuarioDao dao) {
		Usuario instance = new Usuario();
		instance.setNivel(dao.getIdNivel());
		instance.setNombre(dao.getNombre());
		instance.setAp(dao.getAp());
		instance.setAm(dao.getAm());
		instance.setFechaNac(dao.getFechaNac());
		instance.setEstatus(dao.getEstatus());
		instance.setCorreo(dao.getCorreo());
		instance.setPassword(dao.getPassword());
		repository.save(instance);
	}

	public Usuario login(String user, String pass) {
		Usuario instance = repository.findByCorreoAndPassword(user, pass);
		if(instance != null) {
			System.err.println("Entra if");
			return instance;
		}

		return new Usuario();
	}
}