package com.helder.SistemaOSmo.core;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig { // para configurar o ModelMapper no spring
	
	/*Para fazer a CLASSE representation model deve adicionar a dependenci no pom.xml:
	 * 
	  <dependency>
  			<groupId>org.modelmapper</groupId>
 			 <artifactId>modelmapper</artifactId>
  			<version>2.3.7</version>
		</dependency>
		
		porque o modelmapper transfere os dados de um objeto para outro sem precisar 
		digita um por um, ou seja, ele vai transferia os dados entre as classes representation model
		e a classe original
		 */

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
}
