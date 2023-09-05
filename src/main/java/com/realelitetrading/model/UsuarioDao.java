package com.realelitetrading.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioDao {
	
	private Long id;
	
	private Long idRol;
	
	private Long idNivel;

	private String nombre;

	private String ap;

	private String am;

	private Date fechaNac;
	
	private Date registro;

	private String correo;
	
	private String telefono;
	
	private String cod_pais;

	private String password;

	private int estatus;
}