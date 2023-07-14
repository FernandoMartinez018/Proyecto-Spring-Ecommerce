package com.proyecto.ecommerce.service;

import java.util.Optional;

import com.proyecto.ecommerce.model.Producto;

public interface ProductoService {

	public Producto guardar(Producto producto);
	
	public Optional<Producto> obtener(Integer id);
	
	public void actualizar(Producto producto);
	
	public void borrar(Integer id);
}
