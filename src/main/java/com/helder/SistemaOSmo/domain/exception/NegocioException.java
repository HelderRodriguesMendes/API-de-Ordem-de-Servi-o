package com.helder.SistemaOSmo.domain.exception;

public class NegocioException extends RuntimeException{ // REPRESENTA UM ERRO DE DOMINIO, ERRO DE NOGOCIO
	
	private static final long serialVersionUID = 1L;
	
	public NegocioException(String message) {
		super(message);
	}
}
