package com.springboot.app.oauth.security.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.springboot.app.commons.usuarios.models.entity.Usuario;
import com.springboot.app.oauth.services.IUsuarioService;

import feign.FeignException;

//Se manejaran los eventos de éxito y de error al loguearse y se lanzará un evento
@Component
public class AuthenticationSuccessErrorHandler implements AuthenticationEventPublisher {

	private Logger log = LoggerFactory.getLogger(AuthenticationSuccessErrorHandler.class);
	
	//Inyectamos el servicio de usuario
	@Autowired
	private IUsuarioService usuarioService;	
	
	@Override
	public void publishAuthenticationSuccess(Authentication authentication) {
		UserDetails user = (UserDetails) authentication.getPrincipal();
		String mensaje = "Success Login: "+ user.getUsername();
		System.out.println(mensaje);
		log.info(mensaje);		
		
		Usuario usuario = this.usuarioService.findByUsername(authentication.getName());	
		
		if(usuario.getIntentos() != null && usuario.getIntentos() > 0) {
			usuario.setIntentos(0);
			this.usuarioService.update(usuario, usuario.getId());
		}
	}

	@Override
	public void publishAuthenticationFailure(AuthenticationException exception, Authentication authentication) {
		String mensaje = "Error en el Login: "+ exception.getMessage();
		System.out.println(mensaje);
		log.error(mensaje);		
		
		try {
			Usuario usuario = this.usuarioService.findByUsername(authentication.getName());	
			if(usuario.getIntentos() == null){
				usuario.setIntentos(0);
			}
			if (usuario.getIntentos() >= 3) {
				log.error(String.format("El usuario %s deshabilitado por máximos intentos", usuario.getUsername()));
				usuario.setEnabled(false);
			}	
			
			log.info("Intento actual es de "+ usuario.getIntentos());
			
			usuario.setIntentos(usuario.getIntentos()+1);			
			
			this.usuarioService.update(usuario, usuario.getId());
			
			log.info("Intento después es de "+ usuario.getIntentos());
		}catch (FeignException e){
			log.error(String.format("El usuario %s no existe en el sistema", authentication.getName()));	
		}
	}

}
