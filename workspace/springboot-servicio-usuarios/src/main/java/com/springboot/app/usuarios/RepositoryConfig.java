package com.springboot.app.usuarios;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;

import com.springboot.app.usuarios.models.entity.Rol;
import com.springboot.app.usuarios.models.entity.Usuario;

//Se va a configurar para recibir los ID de las clases utilizando la anotación
//@RepositoryRestResource ya que por defecto no los muestra
@Configuration
public class RepositoryConfig implements RepositoryRestConfigurer{

	@Override
	public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
		config.exposeIdsFor(Usuario.class, Rol.class);
	}

}
