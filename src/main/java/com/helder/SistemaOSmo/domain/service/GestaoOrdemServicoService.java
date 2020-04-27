package com.helder.SistemaOSmo.domain.service;

import java.time.OffsetDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.helder.SistemaOSmo.api.model.Comentario;
import com.helder.SistemaOSmo.domain.exception.EntidadeNaoEncontradaException;
import com.helder.SistemaOSmo.domain.exception.NegocioException;
import com.helder.SistemaOSmo.domain.model.Cliente;
import com.helder.SistemaOSmo.domain.model.OrdemServico;
import com.helder.SistemaOSmo.domain.model.StatusOrdemServico;
import com.helder.SistemaOSmo.domain.repository.ClienteRepository;
import com.helder.SistemaOSmo.domain.repository.ComentarioRepository;
import com.helder.SistemaOSmo.domain.repository.OrdemServicoRepository;

@Service
public class GestaoOrdemServicoService {
	
	@Autowired
	private OrdemServicoRepository ordemServicoRepository;
	
	@Autowired
	ClienteRepository clienteRepository;
	
	@Autowired
	ComentarioRepository comentarioRepository;
	
	public OrdemServico criar(OrdemServico ordemServico) {
		
		Cliente cliente = clienteRepository.findById(ordemServico.getCliente().getId()) // BUSCANDO TODOS OS DADOS CLIENTE DA ORDEM DE SERVIÇO, PELO ID DELE
				.orElseThrow(() -> new NegocioException("Cliente não encontrado")); //LANÇA EXCEÇÃO CASO O CLIENTE N FOR ENCONTRADO
		
		ordemServico.setCliente(cliente);
		ordemServico.setStatus(StatusOrdemServico.ABERTA);
		ordemServico.setDataAbertura(OffsetDateTime.now());
		
		return ordemServicoRepository.save(ordemServico);
	}
	
	public Comentario adicionarComentaro(Long ordemServicoId, String descricao) {
		
		OrdemServico ordemServico = buscar(ordemServicoId);

		Comentario comentario = new Comentario();
		
		comentario.setDataEnvio(OffsetDateTime.now());
		comentario.setDescricao(descricao);
		comentario.setOrdemServico(ordemServico);
		
		return comentarioRepository.save(comentario);
	}
	
	public void finalizar(Long ordemServicoId) {
		OrdemServico ordemServico = buscar(ordemServicoId);
		
		ordemServico.finalizar();
		
		ordemServicoRepository.save(ordemServico);
	}

	private OrdemServico buscar(Long ordemServicoId) {
		return ordemServicoRepository.findById(ordemServicoId)
				.orElseThrow(() -> new EntidadeNaoEncontradaException("Ordem de serviço não encontrada"));
	}
}
