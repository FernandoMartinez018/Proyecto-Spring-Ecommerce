package com.proyecto.ecommerce.controler;

import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.proyecto.ecommerce.model.Orden;
import com.proyecto.ecommerce.model.Producto;
import com.proyecto.ecommerce.service.IOrdenService;
import com.proyecto.ecommerce.service.IProductoService;
import com.proyecto.ecommerce.service.IUsuarioService;

import ch.qos.logback.classic.Logger;

@Controller
@RequestMapping("/administrador")
public class AdministradorController {
	
	@Autowired
	private IProductoService productoService;
	
	@Autowired
	private IUsuarioService usuarioService;
	
	@Autowired
	private IOrdenService ordenService;
	
	private Logger log =(Logger) LoggerFactory.getLogger(AdministradorController.class);
	
	@GetMapping("")
	public String home(Model model) {
		List<Producto> productos = productoService.mostrar();
		model.addAttribute("productos",productos);
		
		return "administrador/home";
	}

	@GetMapping("/usuarios")
	public String usuarios(Model model) {
		model.addAttribute("usuarios", usuarioService.findAll());
		return"administrador/usuarios";
	}
	
	@GetMapping("/ordenes")
	public String ordenes(Model model) {
		model.addAttribute("ordenes", ordenService.findAll());
		return"administrador/ordenes";
	}
	
	@GetMapping("/detalle/{id}")
	public String detalle(Model model,@PathVariable Integer id) {
		log.info("Id de la orden {}",id);
		Orden orden =ordenService.findById(id).get();
		
		model.addAttribute("detalles", orden.getDetalle());
		return "administrador/detalleorden";
	}
}
