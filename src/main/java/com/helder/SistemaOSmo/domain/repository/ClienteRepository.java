package com.helder.SistemaOSmo.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.helder.SistemaOSmo.domain.model.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
	List<Cliente> findByNome(String nome); // FAZER BUSCA POR NOME NO BANCO DE DADOS
	
	List<Cliente> findByNomeContaining(String nome); // (findByNomeContaining) VAI BUSCA USANDO LIKE
	
	List<Cliente> findByTelefone(String telefone);// FAZER BUSCA POR TELEFONE NO BANCO DE DADOS
	Cliente findByEmail(String email);
}
