package com.helder.SistemaOSmo.api.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.helder.SistemaOSmo.domain.model.Cliente;
import com.helder.SistemaOSmo.domain.repository.ClienteRepository;
import com.helder.SistemaOSmo.domain.service.CadastroClienteServise;

@RestController
@RequestMapping("/clientes") //TODOS OS METODOS DESSA CLASSE RESPONDEM A REQUISIÇÃO COM /CLIENTES
public class ClienteController {

	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	CadastroClienteServise cadastroCliente;
	
	@GetMapping
	public List<Cliente> listar() {
		return clienteRepository.findAll();
	}
	
	@GetMapping("/{clienteId}")
	public ResponseEntity<Cliente> buscar(@PathVariable Long clienteId) {	//A ANOTAÇÃO (@PathVariable) PEGA A VARIAVEL QUE VEM NA URL E PASSA PARA O PARAMETRO DO METODO
		Optional<Cliente> cliente = clienteRepository.findById(clienteId); // O TIPO (OPTIONAL) PODE SER NULL OU NÃO
		
		if(cliente.isPresent()) { //SE O OBJETO ESTIVER PREENCHIDO
			return ResponseEntity.ok(cliente.get()); // STATUS 200 OK COM O CORPO DA REQUISIÇÃO
		}
		
		return ResponseEntity.notFound().build(); // RETORNA ERRO 404
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cliente adicionar(@Valid @RequestBody Cliente cliente) { //A ANOTAÇÃO @RequestBody TRANSFORMA O JSON QUE VEIO NA REQUISIÇÃO PARA OBJETO
		return cadastroCliente.salvar(cliente);						// A ANOTAÇÃO @Valid ATIVA AS ANOTAÇÕES DE VALIDAÇÃO DA CLASSE MODEL
	}
	
	@PutMapping("/{clienteId}")
	public ResponseEntity<Cliente> atualizar(@Valid @PathVariable Long clienteId, @RequestBody Cliente cliente){
		if(!clienteRepository.existsById(clienteId)) {
			return ResponseEntity.notFound().build();
		}
		
		cliente.setId(clienteId);
		cliente = cadastroCliente.salvar(cliente);
		
		return ResponseEntity.ok(cliente);
	}
	
	@DeleteMapping("/{clienteId}")
	public ResponseEntity<Void> excluir(@PathVariable Long clienteId){
		if(!clienteRepository.existsById(clienteId)) {
			return ResponseEntity.notFound().build();
		}
		
		cadastroCliente.excluir(clienteId);
		
		return ResponseEntity.noContent().build();
	}
	
}
