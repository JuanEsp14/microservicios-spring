package com.springboot.app.oauth.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

//La configuración del proceso de seguridad se encarga de los procesos de login y
//todo lo que tenga que ver con el TOKEN, utilizando el AuthenticationManager

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	//AuthorizationServerConfigurerAdapter necesita la inyección de dos atributos
	//el BCryptPasswordEncoder y el AuthenticationManager, donde este último tiene
	//inyectado el UsuarioService
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private InfoAdcionalToken infoAdicional;

	//Configura el permiso y la seguridad que van a tener nuestros endpoints de OAuth2
	//tokenKeyAccess se encarga de validar las credenciales que se envían al OAuth2 la idea
	//es que cualquier cliente pueda acceder
	//checkTokenAccess validad el token
	//Estos dos endpoints están protegidos por HTTP Basic utilizando las credenciales del cliente
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security.tokenKeyAccess("permitAll()")
		.checkTokenAccess("isAuthenticated()");
	}
	
	//Configuura los clientes que utilizaran a nuestro Backend, cada cliente debe registrarse con el 
	//Id y su contraseña, generando mayor seguridad ya que se establece quién ingresará a la información
	//dnado doble seguridad, una de la app y otra del cliente que va a ingresar
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		//Se registran los clientes en memoria, con el nombre y contraseña de la app
		//en scopes se indican los procesos que puede llevar a cabo (leer/escribir)
		//en authorizedGrantTypes se establece cómo vamos a obtener el Token pueden ser
		// -> Password indica el logeo de la app y del usuario
		// -> AuthorizationCode sirve para autenticar las applicaciones, solicitando
		//		BackEnd el TOKEN para el registro de la APP (TIPO AFIP)
		// -> Implicit autenticación del cliente mucho más debil es como AuthorizationCode 
		//		pero solo se envía el clienteID y el password y se recibe el TOKEN sin
		//		verificación. Se usa para aplicaciones públicas
		clients.inMemory().withClient("nombreAppFrontend")
				.secret(passwordEncoder.encode("contraseñaApp"))
				.scopes("read", "write")
				.authorizedGrantTypes("password", "refresh_token")//refresh renueva el token cuando se vence
				.accessTokenValiditySeconds(3600) //cada una hora se vence el token
				.refreshTokenValiditySeconds(3600); //se renueva por una hora más
			/**
			 poniendo un .and() se pueden anidar más clientes
			 .and()
			 .withClient("nombreAppFrontend2")
				.secret(passwordEncoder.encode("contraseñaApp"))
				.scopes("read", "write")
				.authorizedGrantTypes("password", "refresh_token")
				.accessTokenValiditySeconds(3600) 
				.refreshTokenValiditySeconds(3600);
			 * */
	}

	//Los endpoints están relacionados a los endpoints ("/oauth/token") de la librería OAuth2 que se encargan 
	//de generar el TOKEN JWT
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		
		//Para unir al AccessToken con la información adicional tenemos que utilizar TokenEnhancerChain
		TokenEnhancerChain tokenEnhancer = new TokenEnhancerChain();
		tokenEnhancer.setTokenEnhancers(Arrays.asList(infoAdicional, accessTokenConverter()));
		
		//Configuramos el AuthenticationManager en AuthorizationServerConfigurerAdapter 
		//también el token de tipo JWT y el AccesTokenCorverter que se encarga de guardar
		//los datos del usuario en el token. Encargándose de tomar estos datos y convertirlo
		//en el token, codificados en base64
		endpoints.authenticationManager(authenticationManager)
			.tokenStore(tokenStore())
			.accessTokenConverter(accessTokenConverter())
			.tokenEnhancer(tokenEnhancer);
	}
	
	//Se encarga de crear y  guardar el Token a partir de la configuración de JwtAccessTokenConverter
	@Bean
	public JwtTokenStore tokenStore() {
		return new JwtTokenStore(accessTokenConverter());
	}

	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter tokenConverter = new JwtAccessTokenConverter();
		
		//Firmamos el token con un código secreto
		tokenConverter.setSigningKey("algun_codigo_secreto_aeiou");
		return tokenConverter;
	}
	
	
}
