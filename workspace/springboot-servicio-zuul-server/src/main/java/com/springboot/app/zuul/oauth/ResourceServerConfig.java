package com.springboot.app.zuul.oauth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

//ResourceServer se encargará de proteger todos los recursos 
//todos los accesos a los microservicios, y darle acceso a los mismos
//a través dle token enviado en las cabeceras. Es el que valida que el token
//sea válido y con la misma firma. 
//Se tienen que implementar dos métodos una para asegurar nuestras rutas
//y otro para configurar el token
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	// Configuración del TOKEN
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.tokenStore(tokenStore());
	}

	// Seguridad para rutas
	@Override
	public void configure(HttpSecurity http) throws Exception {
		//Permite que cualquier usuario pueda ingresar a esta ruta
		//Los ** permiten que cualquier ruta que venga después tenga los mismos
		//permisos. Al no poner el método permite que se aplique para cualquier tipo PUT, GET, POST, etc.
		//permitAll() permite que las rutas sean públicas
		//hasAnyRole(ROLES) permite que solo los usuario con esos roles tengan acceso a la ruta
		//hasRole(ROL) permite que solo los usuario con el rol tenga acceso a la ruta
		//					NO es necesario que se ponga el ROLE_
		//.anyRequest().authenticated() establece que cualquier otra ruta requiere autenticación
		http.authorizeRequests().antMatchers("/api/security/oauth/**").permitAll()
				.antMatchers(HttpMethod.GET, "/api/productos/listar", 
											"/api/items/listar", 
											"/api/usuarios/usuarios").permitAll()
				.antMatchers(HttpMethod.GET, "/api/productos/ver/{id}", 
											"/api/items/ver/{id}/cantidad/{cantidad}", 
											"/api/usuarios/usuarios/{id}").hasAnyRole("ADMIN", "USER")
				.antMatchers("/api/productos/**", "/api/items/**", "/api/usuarios/**").hasRole("ADMIN")
				.anyRequest().authenticated();
		/** ES LO MISMO QUE EL ANTERIOR PERO NO DE FORMA GENÉRICA, SE PUEDE DETALLAR MÁS CONTROL
		 * EL CRUD SOLO LO HACE EL ADMIN
		 .antMatchers(HttpMethod.POST, "/api/productos/crear", 
										"/api/items/crear",
										"/api/usuarios/usuarios").hasRole("ADMIN")
				.antMatchers(HttpMethod.PUT, "/api/productos/editar/{id}", 
										"/api/items/editar/{id}",
										"/api/usuarios/usuarios/{id}").hasRole("ADMIN")
				.antMatchers(HttpMethod.DELETE, "/api/productos/eliminar/{id}", 
										"/api/items/eliminar/{id}",
										"/api/usuarios/usuarios/{id}").hasRole("ADMIN");
		 **/
		
		
	}

	// Se encarga de crear y guardar el Token a partir de la configuración de
	// JwtAccessTokenConverter
	@Bean
	public JwtTokenStore tokenStore() {
		return new JwtTokenStore(accessTokenConverter());
	}

	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter tokenConverter = new JwtAccessTokenConverter();

		// Firmamos el token con el mismo código secreto
		tokenConverter.setSigningKey("algun_codigo_secreto_aeiou");
		return tokenConverter;
	}

}
