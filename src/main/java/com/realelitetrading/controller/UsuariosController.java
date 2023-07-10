package com.realelitetrading.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.realelitetrading.entity.Usuario;
import com.realelitetrading.model.UsuarioDao;
import com.realelitetrading.service.EmailService;
import com.realelitetrading.service.UsuarioService;
import com.realelitetrading.utils.Utils;

@RestController
@RequestMapping(value = "/usuarios")
public class UsuariosController {

	@Autowired
	private UsuarioService service;

	@Autowired
	private EmailService emailService;


	@ResponseBody
	@GetMapping
	public List<Usuario> getUsuario(){
		List<Usuario> list = null;
		try {
			list = service.findAll();
			new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return list;
	}

	@ResponseBody
	@PostMapping
	public ResponseEntity<Object> guardar(@Valid @RequestBody UsuarioDao dao){
		Usuario obj = new Usuario();
		try {
			String pass = Utils.generateRandomPassword(8);
			StringBuilder name = new StringBuilder(dao.getNombre())
					.append(" ").append(dao.getAp())
					.append(" ").append(dao.getAm()); 
			dao.setPassword(Utils.encrypt(pass));
			obj = service.save(dao);

			//Enviamos correo con la contraseña
//			Utils.sendEmail(dao.getCorreo(), name.toString(), pass);
//			SimpleMailMessage message = new SimpleMailMessage();
//			message.setFrom("realelitetreadin@gmail.com");
//			message.setTo(dao.getCorreo());
//			message.setSubject("Acceso Portal");
//			message.setText("Hola, "+name.toString()+"\n\nRecibimos la solicitud creacion de tu cuenta con exito!\n\nLa contraseña para acceder a tu cuenta es: "+pass);
			emailService.send("realelitetreadin@gmail.com", dao.getCorreo(), "Acceso Portal", "Hola, "+name.toString()+"\n\nRecibimos la solicitud creacion de tu cuenta con exito!\n\nLa contraseña para acceder a tu cuenta es: "+pass);
			
			return new ResponseEntity<>(obj, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@ResponseBody
	@PutMapping
	public ResponseEntity<Object> actualizar(@Valid @RequestBody UsuarioDao dao) {
		try {
			service.update(dao);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
		}
	}
}
