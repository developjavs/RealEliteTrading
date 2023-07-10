package com.realelitetrading.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.realelitetrading.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	public Usuario findByCorreoAndPassword(String correo, String password);

	public Usuario findByCorreo(String correo);
}
