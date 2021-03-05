package com.franciscoaguiar.cursomc.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.franciscoaguiar.cursomc.domain.Endereco;
import com.franciscoaguiar.cursomc.repositories.EnderecoRepository;
import com.franciscoaguiar.cursomc.services.EnderecoService;


@RestController
@RequestMapping(value = "/enderecos")
public class EnderecoResource {
	
	@Autowired
	private EnderecoService service;
	
	@Autowired
	private EnderecoRepository repository;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Endereco> findById(@PathVariable Integer id){
		Endereco obj = service.buscar(id);
		return new ResponseEntity<Endereco>(obj, HttpStatus.OK);
	}
	
	@GetMapping
	public List<Endereco> findAll(){
		return repository.findAll();
	}

}
