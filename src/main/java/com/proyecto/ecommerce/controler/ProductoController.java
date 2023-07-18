package com.proyecto.ecommerce.controler;

import java.io.IOException;
import java.util.Optional;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.proyecto.ecommerce.model.Producto;
import com.proyecto.ecommerce.model.Usuario;
import com.proyecto.ecommerce.service.ProductoService;
import com.proyecto.ecommerce.service.UploadFileService;


@Controller
@RequestMapping("/productos")
public class ProductoController {
	
	private final Logger LOGGER = LoggerFactory.getLogger(ProductoController.class);
	
	@Autowired
	private UploadFileService upload;
	
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
	public String guardar(Producto producto,@RequestParam("img") MultipartFile file) throws IOException {
		LOGGER.info("Objeto de la vista producto {}",producto);
		Usuario u = new Usuario(1, "", "", "", "", "", "", "");
		producto.setUsuario(u);
		//subida de imagenes
		if (producto.getId()==null) { //si se crea el producto
			String nombreImagen = upload.guardarImagen(file);
			producto.setImagen(nombreImagen);
		}
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
	public String subir(Producto producto,@RequestParam("img") MultipartFile file) throws IOException {
		Producto p = new Producto();
		p = productoService.obtener(producto.getId()).get();
		
		
		if (file.isEmpty()) { //edicion del producto pero no se cambia la imagen
			producto.setImagen(p.getImagen());
		}else {//edicion del producto pero si se cambia la imagen
			if (!p.getImagen().equals("default.jpg")) {//se eliminina solo si no es la imagen por defecto
				upload.borrarimagen(p.getImagen());
			}
			
			String nombreImagen = upload.guardarImagen(file);
			producto.setImagen(nombreImagen);
		}
		
		producto.setUsuario(p.getUsuario());
		productoService.actualizar(producto);
		return "redirect:/productos";
	}
	
	@GetMapping("/delete/{id}")
	public String borrar(@PathVariable Integer id) {
		Producto p =new Producto();
		p=productoService.obtener(id).get();
		
		if (!p.getImagen().equals("default.jpg")) {//se eliminina solo si no es la imagen por defecto
			upload.borrarimagen(p.getImagen());
		}
		
		productoService.borrar(id);
		return "redirect:/productos";
	}
	
	
}
