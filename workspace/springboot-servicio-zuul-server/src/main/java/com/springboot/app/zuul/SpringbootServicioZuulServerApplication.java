package com.springboot.app.zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableEurekaClient

//Zuul se encarga del acceso a los demás. Es la puerta de enlace a los micorservicios.
//Su caracteristicas es su enrutamiento dinámico, se incluye con ribbon y provee balanceo
//de carga. Tiene filtros, uno de ellos es el de seguridad que te permite no usar spring-security
@EnableZuulProxy
public class SpringbootServicioZuulServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootServicioZuulServerApplication.class, args);
	}

}
