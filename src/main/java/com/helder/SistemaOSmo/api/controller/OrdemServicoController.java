package com.helder.SistemaOSmo.api.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.helder.SistemaOSmo.api.model.OrdemServicoInput;
import com.helder.SistemaOSmo.api.model.OrdemServicoModel;
import com.helder.SistemaOSmo.domain.model.OrdemServico;
import com.helder.SistemaOSmo.domain.repository.OrdemServicoRepository;
import com.helder.SistemaOSmo.domain.service.GestaoOrdemServicoService;

@RestController
@RequestMapping("/ordens-servico")
public class OrdemServicoController {
	
	@Autowired
	private GestaoOrdemServicoService gestaoOrdemServicoService;
	
	@Autowired
	private OrdemServicoRepository ordemServicoRepository;
	
	@Autowired
	private ModelMapper modelMepper;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public OrdemServicoModel criar(@Valid @RequestBody OrdemServicoInput ordemServicoInput) {
		OrdemServico ordemServico = toEntity(ordemServicoInput);
		return toModel(gestaoOrdemServicoService.criar(ordemServico));
	}
	
	@GetMapping
	public List<OrdemServicoModel> listar(){
		return toCollectionModel(ordemServicoRepository.findAll()); //PEGTA TODAS AS ORDENS DE SERVIÇO
	}
	
	@GetMapping("/{ordemServicoId}")
	public ResponseEntity<OrdemServicoModel> buscar(@PathVariable Long ordemServicoId){
		Optional<OrdemServico> ordemServico = ordemServicoRepository.findById(ordemServicoId);
		
		if(ordemServico.isPresent()) { //SE O OBJETO ESTIVER PREENCHIDO
			OrdemServicoModel ordemServicoModel = toModel(ordemServico.get());
			return ResponseEntity.ok(ordemServicoModel); // STATUS 200 OK COM O CORPO DA REQUISIÇÃO
		}
		
		return ResponseEntity.notFound().build(); // RETORNA ERRO 404
	}
	
	@PutMapping("/{ordemServicoId}/finalizacao")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void finalizar(@PathVariable Long ordemServicoId) {
		gestaoOrdemServicoService.finalizar(ordemServicoId);
	}
	
	private OrdemServicoModel toModel(OrdemServico ordemServico) {
		return modelMepper.map(ordemServico, OrdemServicoModel.class); //TRANSFERE OS DADOS ORIGINAIS PARA A CLASSE DE REPRESENTAÇÃO
	}
	
	private List<OrdemServicoModel>toCollectionModel(List<OrdemServico> ordensServico){
		return ordensServico.stream()
				.map(ordemServico -> toModel(ordemServico)).collect(Collectors.toList());				
	}
	
	private OrdemServico toEntity(OrdemServicoInput ordemServicoInput) {
		return modelMepper.map(ordemServicoInput, OrdemServico.class);
	}
}
