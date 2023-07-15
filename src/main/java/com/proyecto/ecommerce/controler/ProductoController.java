package com.proyecto.ecommerce.controler;

import java.util.Optional;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.proyecto.ecommerce.model.Producto;
import com.proyecto.ecommerce.model.Usuario;
import com.proyecto.ecommerce.service.ProductoService;


@Controller
@RequestMapping("/productos")
public class ProductoController {
	
	private final Logger LOGGER = LoggerFactory.getLogger(ProductoController.class);
	
	@Autowired
	private ProductoService productoService;
	

	@GetMapping("")
	public String show(Model model) {	
		model.addAttribute("productos", productoService.mostrar());
		return "productos/show";
	}
	
	@GetMapping("/create")
	public String create() {
		return "productos/create";
	}
	
	@PostMapping("/save")
	public String guardar(Producto producto) {
		LOGGER.info("Objeto de la vista producto {}",producto);
		Usuario u = new Usuario(1, "", "", "", "", "", "", "");
		producto.setUsuario(u);
		
		productoService.guardar(producto);
		return "redirect:/productos";
	}
	
	@GetMapping("/edit/{id}")
	public String editar(@PathVariable Integer id,Model model) {
		Producto producto =new Producto();
		Optional<Producto> optionalProducto = productoService.obtener(id);
		producto = optionalProducto.get();
		
		LOGGER.info("producto encontrado {}",producto);
		model.addAttribute("producto", producto);
		return "productos/edit";
	}
	
	@PostMapping("/update")
	public String subir(Producto producto) {
		productoService.actualizar(producto);
		return "redirect:/productos";
	}
	
	@GetMapping("/delete/{id}")
	public String borrar(@PathVariable Integer id) {
		productoService.borrar(id);
		return "redirect:/productos";
	}
}
