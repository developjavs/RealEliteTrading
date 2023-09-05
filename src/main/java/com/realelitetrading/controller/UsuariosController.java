package com.realelitetrading.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.realelitetrading.entity.Usuario;
import com.realelitetrading.model.UsuarioDao;
import com.realelitetrading.service.EmailService;
import com.realelitetrading.service.UsuarioService;
import com.realelitetrading.utils.Utils;

//@CrossOrigin
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

			if (obj != null)//realtradingimparableoficial@gmail.com
				emailService.sendWithHTML("realtradingimparableoficial@gmail.com", dao.getCorreo(),
						"Confirme su dirección de correo electrónico",
						"<div style='font-family: sans-serif; font-size: medium;background: #f8f8f8;padding: 1rem;border-radius: 5px;font-size: 1rem;'>"
						+ "¡Gracias por Registrarte!<br /><br />"
						+ "Querido " + name.toString()
								+ "<br /><br />"
								+ "¡Bienvenido(a) a nuestra comunidad de élite! <br /> Estamos emocionados de tenerte a bordo al programa de trading MAS COMPLETO y profesional sin ningun costo. "
								+ "<br/>"
								+ "Tu decisión de unirte es el primer paso hacia el desarrollo de habilidades financieras sólidas y la exploración del emocionante mundo de los mercados financieros."
								+ "<br/><br/>"
								+ "Nos complace informarte que hemos creado un usuario exclusivo para ti. A continuación, encontrarás tu información de acceso:"
								+ "<br/>"
								+ "Usuario: " + dao.getCorreo()
								+ "<br/>"
								+ "Contraseña:" + pass
								+ "<br /><br />"
								+ "<p>Con esta información, podrás acceder a nuestro portal exclusivo de miembros y sumergirte en el contenido de alta calidad que hemos preparado para ti."
								+ "<br/>Estamos comprometidos en brindarte la mejor experiencia educativa posible y te alentamos a aprovechar al máximo todos los recursos y conocimientos que te ofreceremos."
								+ "<br/>Si tienes alguna pregunta o necesitas ayuda en cualquier momento, no dudes en ponerte en contacto con nuestro equipo de soporte. Estamos aquí para ayudarte en cada paso del camino.<p/>"
								+ "<br/>"
								+ "¡Estamos ansiosos por ver tu crecimiento y éxito en el mundo del trading!"
								+ "<br /><br />"
								+ "Para usar tu cuenta, primero tendrás que confirmar la dirección de correo electrónico haciendo clic en el siguiente botón."
								+ "<br />"
								+ "<a "
								+ "style='height: auto;background: #405189;border: none;color: #fff;padding: 8px 20px;border-radius: 4px;display: inline-block;margin: 20px 0px 20px 5px;text-decoration: none;' "
								+ "href='http://www.realelitetrading.com/verify.html?email="+dao.getCorreo()+"'>VERIFICAR CUENTA</a>"
								+ "<br />"
								+ "Saludos cordiales,"
								+"<br/>"
								+"ATTE: RAFAEL VILLANUEVA - CEO FUNDADOR <br/> REAL ELITE TRADING <br/> WWW.REALELITETRADING.COM <br/> ------------------------------------"
								+ "</div>");
			
			if(obj != null)
				return new ResponseEntity<>(obj, HttpStatus.CREATED);//201
			else
				return new ResponseEntity<>(HttpStatus.CONFLICT);//409
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);//400
		}
	}
	
	@ResponseBody
	@PostMapping("/contactar")
	public ResponseEntity<Object> contacto(@RequestParam String nombre, @RequestParam String correoRemitente,
			@RequestParam String asunto, @RequestParam String mensaje) {
		try {
				emailService.sendWithHTML(correoRemitente,"realtradingimparableoficial@gmail.com",
						asunto,
						"<div style='font-family: sans-serif; font-size: medium;background: #f8f8f8;padding: 1rem;border-radius: 5px;font-size: 1rem;'>"
						+"Hola, mi nombre es "+ nombre + "<br/>"+  mensaje + "</div>");
				return new ResponseEntity<>(HttpStatus.ACCEPTED);//202
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);//400
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

	@ResponseBody
	@GetMapping("/login")
	public ResponseEntity<Object> login(@RequestParam String user, @RequestParam String pass) {
		try {
			Usuario instance = service.login(user, Utils.encrypt(pass));
			if(instance.getEstatus()==0 && instance.getNombre().length()>0) {
				return new ResponseEntity<>(HttpStatus.NON_AUTHORITATIVE_INFORMATION);//203
			}else if(instance.getNombre().length()==0){
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);//404
			} else {
				return new ResponseEntity<>(instance, HttpStatus.OK);//200
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);//404
		}
	}

	@ResponseBody
	@GetMapping("/valida-cuenta")
	public ResponseEntity<Object> validaCuenta(@RequestParam String email) {
		try {
			boolean estatus = service.validaCuenta(email);
			if(estatus)
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);//404
			else
				return new ResponseEntity<>(HttpStatus.ACCEPTED);//202
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);//400
		}
	}

	
}
