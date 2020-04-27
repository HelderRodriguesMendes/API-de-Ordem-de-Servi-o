package com.helder.SistemaOSmo.api.exceptionhandler;

import java.time.OffsetDateTime;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.helder.SistemaOSmo.domain.exception.EntidadeNaoEncontradaException;
import com.helder.SistemaOSmo.domain.exception.NegocioException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler { // PEGA TODOS OS ERRO QUE ACONTECE NA API
	
	@	Autowired
	private MessageSource messageSource; // RESOUVE MENSSAGEM QUE TA NO ARQUIVO (MESSAGES) 
	
	@ExceptionHandler(EntidadeNaoEncontradaException.class) // VAI RECEBE TODO ERRO DA CLASSE NegocioException
	public ResponseEntity<Object> handleEntidadeNaoEncontrada(NegocioException ex, WebRequest request) { 			//QUE FOR LANÇADA POR ALGUM CONTROLADOR OU CLASSE COMUM, VAI CAI NESSE METODO PARA SER TRATADO
		var status = HttpStatus.NOT_FOUND;
		var problema = new Problema();
		problema.setTitulo(ex.getMessage());
		problema.setDataHora(OffsetDateTime.now());
		
		return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
	}
	
	@ExceptionHandler(NegocioException.class) // VAI RECEBE TODO ERRO DA CLASSE NegocioException
	public ResponseEntity<Object> handleNegocio(NegocioException ex, WebRequest request) { 			//QUE FOR LANÇADA POR ALGUM CONTROLADOR OU CLASSE COMUM, VAI CAI NESSE METODO PARA SER TRATADO
		var status = HttpStatus.BAD_REQUEST;
		var problema = new Problema();
		problema.setTitulo(ex.getMessage());
		problema.setDataHora(OffsetDateTime.now());
		
		return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		var campos = new ArrayList<Problema.Campo>();
		
		for(ObjectError error : ex.getBindingResult().getAllErrors()) { // DE TODOS OS ERROS
			String nome = ((FieldError)error).getField(); //NOME DO CAMPO QUE TA COM PROBLEMA
			String mensagem =  messageSource.getMessage(error, LocaleContextHolder.getLocale()); //MENSAGEM PARA MOSTRA PRO USUARIO
			campos.add(new Problema.Campo(nome, mensagem)); 						//O (LocaleContextHolder) TRADUZ A MENSSAGEM PARA A LINGUA QUE ESTAMOS (BRASILEIRA)
		}
		
		var problema = new Problema();
		problema.setStatus(status.value());
		problema.setTitulo("Um ou mais campos estão inválidos. "
				+ "Faça o preenchimento correto e tente novamente");
		problema.setDataHora(OffsetDateTime.now());
		problema.setCampos(campos);
		
		
		return super.handleExceptionInternal(ex, problema, headers, status, request);
	}
	
}
