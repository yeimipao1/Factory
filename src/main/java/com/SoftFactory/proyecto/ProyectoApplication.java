package com.SoftFactory.proyecto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication(exclude={SecurityAutoConfiguration.class})
public class ProyectoApplication {

	@GetMapping("/hello")
	public String hello(){
		return "Hola Mundo";
	}
	
	@GetMapping("/test")
	public String test(){
		Empresa enterprise = new Empresa("nueva", "av. santafe", "321786954", "");
		
		
		return "Hola Mundo";
	}

	public static void main(String[] args) {
		SpringApplication.run(ProyectoApplication.class, args);
	}

}
