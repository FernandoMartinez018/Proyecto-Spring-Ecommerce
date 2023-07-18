package com.proyecto.ecommerce.controler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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

import com.proyecto.ecommerce.model.DetalleOrden;
import com.proyecto.ecommerce.model.Orden;
import com.proyecto.ecommerce.model.Producto;
import com.proyecto.ecommerce.service.ProductoService;


@Controller
@RequestMapping("/")
public class HomeControler {
	
	private final org.slf4j.Logger  log= LoggerFactory.getLogger(HomeControler.class);
	//almacenamiento de la orden
	List<DetalleOrden> detalles = new ArrayList<DetalleOrden>();
	//datos de la orden
	Orden orden = new Orden();
	
	@Autowired
	private ProductoService productoService;
	
	@GetMapping("")
	public String Home(Model model) {
		model.addAttribute("productos", productoService.mostrar());
		return "usuario/home";
	}
	
	@GetMapping("productohome/{id}")
	public String productoHome(@PathVariable Integer id, Model model) {
		log.info("Id como parametro correcto {id}",id);
		Producto producto = new Producto();
		Optional<Producto> productoOptional = productoService.obtener(id);
		producto = productoOptional.get();
		model.addAttribute("producto", producto);
		return "usuario/productohome";
	}
	
	@PostMapping("/cart")
	public String añadirCarrito(@RequestParam Integer id, @RequestParam Integer cantidad, Model model) {
		DetalleOrden detalleOrden =new DetalleOrden();
		Producto producto =new Producto();
		double sumaTotal =0;
		
		Optional<Producto> optionalProducto = productoService.obtener(id);
		log.info("Producto {} Añadido",optionalProducto.get());
		log.info("cantidad {}",cantidad);
		producto=optionalProducto.get();
		
		detalleOrden.setCantidad(cantidad);
		detalleOrden.setPrecio(producto.getPrecio());
		detalleOrden.setNombre(producto.getNombre());
		detalleOrden.setTotal(producto.getPrecio()*cantidad);
		detalleOrden.setProducto(producto);
		
		//validacion de que el producto no se añada mas de 2 veces
		Integer idProducto = producto.getId();
		boolean ingresado = detalles.stream().anyMatch(p -> p.getProducto().getId() == idProducto);
		
		if (!ingresado) {
			detalles.add(detalleOrden);
		}
		
		sumaTotal = detalles.stream().mapToDouble(dt ->dt.getTotal()).sum();
		
		orden.setTotal(sumaTotal);
		model.addAttribute("cart", detalles);
		model.addAttribute("orden", orden);
		
		return "usuario/carrito";
	}
	
	@GetMapping("/delete/cart/{id}")
	public String borrarDelCarrito(@PathVariable Integer id,Model model) {
		List<DetalleOrden> ordenesNuevas = new ArrayList<DetalleOrden>();
		
		for(DetalleOrden detalleOrden : detalles) {
			if (detalleOrden.getProducto().getId() !=id) {
				ordenesNuevas.add(detalleOrden);
			}
		}
		
		//nueva lista con id diferentes
		detalles = ordenesNuevas;
		
		double sumaTotal =0;
		sumaTotal = detalles.stream().mapToDouble(dt ->dt.getTotal()).sum();
		
		orden.setTotal(sumaTotal);
		model.addAttribute("cart", detalles);
		model.addAttribute("orden", orden);
		return "usuario/carrito";
	}
	
	@GetMapping("/getcart")
	public String compraCarrito(Model model) {
		model.addAttribute("cart", detalles);
		model.addAttribute("orden", orden);
		return "/usuario/carrito";
	}
}
