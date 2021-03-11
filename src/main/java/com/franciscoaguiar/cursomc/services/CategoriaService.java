package com.franciscoaguiar.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.franciscoaguiar.cursomc.domain.Categoria;
import com.franciscoaguiar.cursomc.repositories.CategoriaRepository;
import com.franciscoaguiar.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository repository;
	
	public Categoria find(Integer id) {
		Optional<Categoria> obj = repository.findById(id);
		return  obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
				
	}
	
	public Categoria insert(Categoria obj) {
		obj.setId(null);
		return repository.save(obj);
	}
	
	public Categoria update(Categoria obj) {
		return repository.save(obj);
	}

}
