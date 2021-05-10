package com.franciscoaguiar.cursomc.resources;

import com.franciscoaguiar.cursomc.domain.Produto;
import com.franciscoaguiar.cursomc.domain.Produto;
import com.franciscoaguiar.cursomc.dto.ProdutoDTO;
import com.franciscoaguiar.cursomc.resources.utils.URLUtil;
import com.franciscoaguiar.cursomc.services.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/produtos")
public class ProdutoResource {
	
	@Autowired
	private ProdutoService service;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Produto> findById(@PathVariable Integer id){
		Produto obj = service.find(id);
		return new ResponseEntity<Produto>(obj, HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<Page<ProdutoDTO>> findPage(
			@RequestParam(value = "nome", defaultValue = "") String nome,
			@RequestParam(value = "categorias", defaultValue = "") String categorias,
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {
		String nomeDecoded = URLUtil.decodeParam(nome);
		List<Integer> ids = URLUtil.decodeIntList(categorias);
		Page<Produto> list = service.search(nomeDecoded ,ids, page, linesPerPage, orderBy, direction);
		Page<ProdutoDTO> listDTO = list.map(obj -> new ProdutoDTO(obj));

		return ResponseEntity.ok().body(listDTO);
	}
}
