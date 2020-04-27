package com.helder.SistemaOSmo.api.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import com.helder.SistemaOSmo.domain.model.StatusOrdemServico;

public class OrdemServicoModel { // CLASSE representation model 
	
/* A classe REPRESENTATION MODEL é como se fosse uma classe falsa, uma cópia da original,
  onde a falsa que conecta com o front end e depois ambas envia os dados uma para a outra,
   por que se caso for preciso fazer alguma alteração na model, faz em uma, mas se não der certo, 
   a outra ainda esta sem meche de forma original, evitando afeta diretamente o funcionamento da API 
    */
	
	private Long id;
	private ClienteResumoModel cliente;
	private String descricao;
	private BigDecimal preco;
	private StatusOrdemServico status;
	private OffsetDateTime dataAbertura;
	private OffsetDateTime dataFec;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public BigDecimal getPreco() {
		return preco;
	}
	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}
	public StatusOrdemServico getStatus() {
		return status;
	}
	public void setStatus(StatusOrdemServico status) {
		this.status = status;
	}
	public OffsetDateTime getDataAbertura() {
		return dataAbertura;
	}
	public void setDataAbertura(OffsetDateTime dataAbertura) {
		this.dataAbertura = dataAbertura;
	}
	public OffsetDateTime getDataFec() {
		return dataFec;
	}
	public void setDataFec(OffsetDateTime dataFec) {
		this.dataFec = dataFec;
	}
	public ClienteResumoModel getCliente() {
		return cliente;
	}
	public void setCliente(ClienteResumoModel cliente) {
		this.cliente = cliente;
	}
	
	
}
