package com.springboot.app.item;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
//Habilita la inyección de dependencias de Feign
@EnableFeignClients
//Se anota la singular porque tenemos solamente un cliente en caso de tener
//más se debe anotar con el annotation @RibbonClients
@RibbonClient(name = "servicio-productos")
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
