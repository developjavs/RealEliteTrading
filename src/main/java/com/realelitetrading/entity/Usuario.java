package com.realelitetrading.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@ToString
public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_usuario")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "id_rol")
	private Rol rol;

	@Column(name = "id_nivel")
	private Long nivel;

	private String nombre;

	@Column(name = "a_paterno")
	private String ap;

	@Column(name = "a_materno")
	private String am;

	@Column(name = "fecha_nacimiento")
	private Date fechaNac;
	
	private Date registro;

	private int estatus;

	private String correo;
	
	private String telefono;
	
	private String cod_pais;

	private String password;

	@Column(name = "reset_password")
	private int reset;
}