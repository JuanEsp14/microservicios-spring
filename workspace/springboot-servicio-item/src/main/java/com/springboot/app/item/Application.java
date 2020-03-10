package com.springboot.app.item;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
//import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
//Habilita la inyección de dependencias de Feign
@EnableFeignClients

//Se anota la singular porque tenemos solamente un cliente en caso de tener
//más se debe anotar con el annotation @RibbonClients
//Se elimina la configuración de Ribbon, ya que se está utilizando Eureka
//@RibbonClient(name = "servicio-productos")

//Habilitamos el cliente de Eureka en la app
@EnableEurekaClient
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
