package com.franciscoaguiar.cursomc.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.franciscoaguiar.cursomc.domain.Categoria;
import com.franciscoaguiar.cursomc.dto.CategoriaDTO;
import com.franciscoaguiar.cursomc.services.CategoriaService;


@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResource {
	
	@Autowired
	private CategoriaService service;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Categoria> findById(@PathVariable Integer id){
		Categoria obj = service.find(id);
		return new ResponseEntity<Categoria>(obj, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<Void> insert(@Valid @RequestBody CategoriaDTO objDTO){
		Categoria obj = service.insert(DTOParseObject(objDTO));
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<Void> update(@PathVariable Integer id, @Valid @RequestBody CategoriaDTO objDTO){
		Categoria obj = DTOParseObject(objDTO);
		obj.setId(id);
		service.find(id);
		service.update(obj);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id){
		service.find(id);
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping
	public ResponseEntity<List<CategoriaDTO>> findAll(){
		List<Categoria> list = service.findAll();
		List<CategoriaDTO> listDTO = list.stream().map(cat -> new CategoriaDTO(cat)).collect(Collectors.toList());		
		
		return new ResponseEntity<List<CategoriaDTO>>(listDTO, HttpStatus.OK);
	}
	
	@GetMapping(value = "/page")
	public ResponseEntity<Page<CategoriaDTO>> findPage(
			@RequestParam(value = "page", defaultValue = "0") Integer page, 
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage, 
			@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy, 
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {
		Page<Categoria> list = service.findPage(page, linesPerPage, orderBy, direction);
		Page<CategoriaDTO> listDTO = list.map(cat -> new CategoriaDTO(cat));		
		
		return ResponseEntity.ok().body(listDTO);
	}
	
	private Categoria DTOParseObject(CategoriaDTO catDTO) {
		return new Categoria(catDTO.getId(), catDTO.getNome());
	}

}
