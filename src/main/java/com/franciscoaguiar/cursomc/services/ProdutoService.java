package com.franciscoaguiar.cursomc.services;

import com.franciscoaguiar.cursomc.domain.Categoria;
import com.franciscoaguiar.cursomc.domain.Produto;
import com.franciscoaguiar.cursomc.repositories.CategoriaRepository;
import com.franciscoaguiar.cursomc.repositories.ProdutoRepository;
import com.franciscoaguiar.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {
	
	@Autowired
	private ProdutoRepository repository;

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	public Produto find(Integer id) {
		Optional<Produto> obj = repository.findById(id);
		return  obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Produto.class.getName()));
	}

	public Page<Produto> search(String nome, List<Integer> ids, Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
		List<Categoria> categorias = categoriaRepository.findAllById(ids);
		return repository.findDistinctByNomeContainingAndCategoriasIn(nome, categorias, pageRequest);
	}

}
