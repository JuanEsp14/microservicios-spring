package com.springboot.app.productos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication

//Habilitamos el cliente de Eureka en la app
@EnableEurekaClient

//Configuramos el Entity Scan para que tome las clases que
//se encuentran en otros paquetes o en otros microservicios
@EntityScan({"com.springboot.app.commons.models.entity"})
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
