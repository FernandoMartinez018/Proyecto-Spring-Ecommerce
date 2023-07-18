package com.proyecto.ecommerce.controler;

import java.util.Optional;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.proyecto.ecommerce.model.Producto;
import com.proyecto.ecommerce.service.ProductoService;


@Controller
@RequestMapping("/")
public class HomeControler {
	
	private final org.slf4j.Logger  log= LoggerFactory.getLogger(HomeControler.class);

	@Autowired
	private ProductoService productoService;
	
	@GetMapping("")
	public String Home(Model model) {
		model.addAttribute("productos", productoService.mostrar());
		return "usuario/home";
	}
	
	@GetMapping("productohome/{id}")
	public String productoHome(@PathVariable Integer id, Model model) {
		log.info("Id como parametro correcto {id}"+id);
		Producto producto = new Producto();
		Optional<Producto> productoOptional = productoService.obtener(id);
		producto = productoOptional.get();
		model.addAttribute("producto", producto);
		return "usuario/productohome";
	}
}
