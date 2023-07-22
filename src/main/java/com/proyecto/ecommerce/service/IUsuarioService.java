package com.proyecto.ecommerce.service;

import java.util.Optional;

import com.proyecto.ecommerce.model.Usuario;

public interface IUsuarioService {

	Optional<Usuario> findById (Integer id);
	
	Usuario guardar(Usuario usuario);
	
	Optional<Usuario> findByEmail(String email);
}
