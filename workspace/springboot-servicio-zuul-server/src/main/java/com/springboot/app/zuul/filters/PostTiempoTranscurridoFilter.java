package com.springboot.app.zuul.filters;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

@Component
public class PostTiempoTranscurridoFilter extends ZuulFilter{

private static Logger log = LoggerFactory.getLogger(PreTiempoTranscurridoFilter.class);
	
	/**
	 Sirve para validar si el filtro se ejecuta o no, se puede evaluar si
	 existe algún parámetro en la ruta o si tienen determinado valor o si
	 el usuario está registrado,	 
	 */
	@Override
	public boolean shouldFilter() {
		return true;
	}

	/**
	 En este método se escribe la lógica del filtro
	 */
	@Override
	public Object run() throws ZuulException {
		
		
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();
		
		log.info("Entrando a PostFilter");
		
		Long tiempoInicio = (Long) request.getAttribute("tiempoInicio");
		Long tiempoFinal = System.currentTimeMillis();
		Long tiempoTranscurrido = tiempoFinal - tiempoInicio;
		
		log.info(String.format("Tiempo transcurrido en segundos %s", tiempoTranscurrido.doubleValue()/1000.0));
		log.info(String.format("Tiempo transcurrido en milisegundos %s", tiempoTranscurrido));
		
		return null;
	}

	/**
	Se indica el tipo de filtro que se va a utilizar existen 3 tipos
	pre -> Antes que el request sea enrutado a un microservicio. Se utiliza 
			para pasar datos
	post -> Se invoca después que el request fue enrutado. Se utiliza para 
			modificar la respuesta. ES la típica cabecera
	route -> Se ejecura durante el enrutado del request, sirve para resolver 
				la ruta. Se usa para la comunicación el microservico
	error -> Se ejecura si existe algun error. Sirve para procesarlos y tomar
				alguna medida sobre ellos
	 * */
	@Override
	public String filterType() {
		return "post";
	}
	
	
	/**
	 El orden que ocupa nuestro filtro dentro de los demás
	 * */
	@Override
	public int filterOrder() {
		return 1;
	}
}
