package com.proyecto.ecommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.proyecto.ecommerce.model.Orden;
import com.proyecto.ecommerce.repository.IOrdenRepository;

@Service
public class OrdenServiceImpl implements IOrdenService{

	@Autowired
	private IOrdenRepository ordenRepository;
	
	@Override
	public Orden guardar(Orden orden) {
		
		return ordenRepository.save(orden);
	}

}
