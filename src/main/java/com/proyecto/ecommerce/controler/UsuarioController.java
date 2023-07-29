package com.proyecto.ecommerce.controler;

import java.util.List;
import java.util.Optional;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.proyecto.ecommerce.model.Orden;
import com.proyecto.ecommerce.model.Usuario;
import com.proyecto.ecommerce.service.IOrdenService;
import com.proyecto.ecommerce.service.IUsuarioService;

import ch.qos.logback.classic.Logger;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {
	private final org.slf4j.Logger log =LoggerFactory.getLogger(UsuarioController.class);
	
	@Autowired
	private IUsuarioService usuarioService;
	
	@Autowired
	private IOrdenService ordenService;
	
	//BCryptPasswordEncoder passEncode=new BCryptPasswordEncoder();
	
	@GetMapping("/registro")
	public String registrar() {
		return"usuario/registro";
	}
	
	@PostMapping("/save")
	public String guardarRegistro(Usuario usuario) {
		log.info("Usuario registro: {}",usuario);
		usuario.setTipo("USER");
	//	usuario.setPassword(passEncode.encode(usuario.getPassword()));
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
	
	@GetMapping("/compras")
	public String obtenerCompras(Model model,HttpSession session) {
		model.addAttribute("sesion", session.getAttribute("idusuario"));
		Usuario usuario =usuarioService.findById(Integer.parseInt(session.getAttribute("idusuario").toString())).get();
		
		List<Orden> ordenes = ordenService.findByUsuario(usuario);
		model.addAttribute("ordenes", ordenes);
		return"usuario/compras";
	}



	@GetMapping("/detalle/{id}")
	public String detalleCompra(@PathVariable Integer id, HttpSession session, Model model) {
	//	log.info("Id de la orden: {}", id);
		Optional<Orden> orden=ordenService.findById(id);

		model.addAttribute("detalles", orden.get().getDetalle());


		//session
		model.addAttribute("sesion", session.getAttribute("idusuario"));
		return "usuario/detallecompra";
	}
	
	@GetMapping("/cerrar")
	public String cerrarSesion(HttpSession session) {
		session.removeAttribute("idusuario");
		
		return "redirect:/";
	}
}
