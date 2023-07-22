package com.proyecto.ecommerce.controler;

import java.util.Optional;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.proyecto.ecommerce.model.Usuario;
import com.proyecto.ecommerce.service.IUsuarioService;

import ch.qos.logback.classic.Logger;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {
	private final org.slf4j.Logger log =LoggerFactory.getLogger(UsuarioController.class);
	
	@Autowired
	private IUsuarioService usuarioService;
	
	@GetMapping("/registro")
	public String registrar() {
		return"usuario/registro";
	}
	
	@PostMapping("/save")
	public String guardarRegistro(Usuario usuario) {
		log.info("Usuario registro: {}",usuario);
		usuario.setTipo("USER");
		usuarioService.guardar(usuario);
		
		return"redirect:/";
	}
	
	@GetMapping("/login")
	public String login() {
		return"usuario/login";
	}
	
	@PostMapping("/acceder")
	public String acceder(Usuario usuario, HttpSession session) {
		log.info("Acceso de: {}",usuario);
		
		Optional<Usuario> user =usuarioService.findByEmail(usuario.getEmail());
		//log.info("email usuario: {}",user.get());
		
		if (user.isPresent()) {
			session.setAttribute("idusuario", user.get().getId());
			if (user.get().getTipo().equals("ADMIN")) {
				return"redirect:/administrador";
			}else {
				return"redirect:/";
			}
		}else {
			log.info("Usuario no registrado");
		}
		return"redirect:/";
	}

}
