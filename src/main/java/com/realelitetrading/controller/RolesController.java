package com.realelitetrading.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.realelitetrading.entity.Rol;
import com.realelitetrading.model.RolDao;
import com.realelitetrading.service.RolService;

@RestController
@RequestMapping("/roles")
public class RolesController {

	@Autowired
	private RolService service;

	@ResponseBody
	@GetMapping
	public List<Rol> getRoles(){
		List<Rol> list = null;
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
	public ResponseEntity<Object> guardar(@RequestParam String clave, @RequestParam String descripcion){
		Rol obj = new Rol();
		try {
			obj = service.save(clave, descripcion);
			return new ResponseEntity<>(obj, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@ResponseBody
	@PutMapping
	public ResponseEntity<Object> actualizar(@Valid @RequestBody RolDao dao) {
		try {
			service.update(dao);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
		}
	}
}