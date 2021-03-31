package com.franciscoaguiar.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import com.franciscoaguiar.cursomc.domain.Cliente;
import com.franciscoaguiar.cursomc.domain.enums.TipoCliente;
import com.franciscoaguiar.cursomc.dto.ClienteDTO;
import com.franciscoaguiar.cursomc.dto.ClienteNewDTO;
import com.franciscoaguiar.cursomc.repositories.ClienteRepository;
import com.franciscoaguiar.cursomc.resources.exception.FieldMessage;
import com.franciscoaguiar.cursomc.services.validation.utils.BR;

public class ClienteUpdateValidator implements ConstraintValidator<ClienteUpdate, ClienteDTO>{
	
	@Autowired
	ClienteRepository repository;
	
	@Autowired
	HttpServletRequest request;
	
	@Override
	public void initialize(ClienteUpdate ann) {
	}
	
	@Override
	public boolean isValid(ClienteDTO objDTO, ConstraintValidatorContext context) {
		
		Map<String, String> map = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		Integer uriId = Integer.parseInt(map.get("id"));
		
		
		List<FieldMessage> list = new ArrayList<>();
		//Início dos testes de validação
		
		Cliente aux = repository.findByEmail(objDTO.getEmail());
		
		if(aux != null && !aux.getId().equals(uriId)) {
			list.add(new FieldMessage("email", "Email já existente"));
		}
		
		
		//Fim dos testes de validação
		for(FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName()).addConstraintViolation();
		}
		return list.isEmpty();
	}

}
