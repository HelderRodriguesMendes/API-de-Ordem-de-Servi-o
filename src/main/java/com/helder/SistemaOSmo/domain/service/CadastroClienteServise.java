package com.helder.SistemaOSmo.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.helder.SistemaOSmo.domain.exception.NegocioException;
import com.helder.SistemaOSmo.domain.model.Cliente;
import com.helder.SistemaOSmo.domain.repository.ClienteRepository;

@Service
public class CadastroClienteServise {
	
	@Autowired
	ClienteRepository clienteRepository;
	
	public Cliente salvar(Cliente cliente) {
		
		Cliente clienteExistente = clienteRepository.findByEmail(cliente.getEmail());
		
		if(clienteExistente != null && !clienteExistente.equals(cliente)) {
			throw new NegocioException("Já existe um cliente cadastrado com este e-mail.");
		}
		
		return clienteRepository.save(cliente);
	}
	
	public void excluir(Long clienteId) {
		clienteRepository.deleteById(clienteId);
	}
}
