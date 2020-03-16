package com.springboot.app.item;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
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

//Se encarga mediante un hilo separado de la comunicación de los mircroservicios
//ayuda a controlar las interacciones entre estos servicios distribuidos al agregar 
//tolerancia a la latencia y lógica de tolerancia a fallas. Hystrix hace esto al aislar 
//los puntos de acceso entre los servicios, detener las fallas en cascada a través de 
//ellos y proporcionar opciones de respaldo, todo lo cual mejora la resistencia general 
//de su sistema.
@EnableCircuitBreaker

//Configuramos el Entity Scan para que tome las clases que
//se encuentran en otros paquetes o en otros microservicios
@EntityScan({"com.springboot.app.commons.models.entity"})

//Deshabilitamos la autoconfiguración, para que no nos pida una base de datos obligatoria
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
