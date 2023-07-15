package com.proyecto.ecommerce.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.proyecto.ecommerce.model.Producto;
import com.proyecto.ecommerce.repository.ProductoRepository;

@Service
public class ProductoServiceImpl implements ProductoService{

	@Autowired
	private ProductoRepository productoRepository;
	
	@Override
	public Producto guardar(Producto producto) {
		return productoRepository.save(producto);
	}

	@Override
	public Optional<Producto> obtener(Integer id) {
		return productoRepository.findById(id);
	}

	@Override
	public void actualizar(Producto producto) {
		productoRepository.save(producto);
	}

	@Override
	public void borrar(Integer id) {
		productoRepository.deleteById(id);
	}

	@Override
	public List<Producto> mostrar() {
		return productoRepository.findAll();
	}

}
