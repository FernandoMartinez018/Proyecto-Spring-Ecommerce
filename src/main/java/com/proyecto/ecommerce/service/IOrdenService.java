package com.proyecto.ecommerce.service;

import java.util.List;
import java.util.Optional;

import com.proyecto.ecommerce.model.Orden;
import com.proyecto.ecommerce.model.Usuario;

public interface IOrdenService {
	
	List<Orden> findAll();
	
	Orden guardar (Orden orden);
	
	String generarNumeroOrden();
	
	List<Orden> findByUsuario(Usuario usuario);
	
	Optional<Orden> findById(Integer id);
	
	
}
