package com.franciscoaguiar.cursomc.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.franciscoaguiar.cursomc.domain.Cliente;
import com.franciscoaguiar.cursomc.repositories.ClienteRepository;
import com.franciscoaguiar.cursomc.services.exceptions.DataIntegrityException;
import com.franciscoaguiar.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repository;

	public Cliente find(Integer id) {
		Optional<Cliente> Cliente = repository.findById(id);
		return Cliente.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}
	
	@Transactional
	public Cliente insert(Cliente obj) {
		obj.setId(null);
		obj = repository.save(obj);
		return obj;
	}

	public Cliente update(Cliente obj) {
		Cliente cli = repository.findById(obj.getId()).get();
		updateData(obj, cli);
		return repository.save(obj);
	}

	public void delete(Integer id) {
		Cliente cli = repository.findById(id).get();
		if (cli.getPedidos().isEmpty()) {
			repository.deleteById(id);
		} else {
			throw new DataIntegrityException("Não é possível excluir um cliente, pois há pedidos relacionadas");
		}

	}

	public List<Cliente> findAll() {
		return repository.findAll();
	}

	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repository.findAll(pageRequest);
	}

	private void updateData(Cliente obj, Cliente cli) {
		obj.setCpfOuCnpj(cli.getCpfOuCnpj());
		obj.setTipo(cli.getTipo());
	}
}
