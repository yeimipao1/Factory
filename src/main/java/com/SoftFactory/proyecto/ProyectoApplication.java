package com.SoftFactory.proyecto;


import com.SoftFactory.proyecto.entidades.Empresa;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;


@SpringBootApplication(exclude={SecurityAutoConfiguration.class})
public class ProyectoApplication {

		@GetMapping("/test")
	public String test(){
		Empresa fact = new Empresa("SoftFactory", "av.sante", 310876, "111187566");
		fact.setNombreEmpresa("SoftFactory 1");
		System.out.println("probando");
		return fact.getNombreEmpresa();
	}

	public static void main(String[] args) {
		SpringApplication.run(ProyectoApplication.class, args);
	}

}
