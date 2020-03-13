package com.springboot.app.item.controllers;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.springboot.app.item.models.Item;
import com.springboot.app.item.models.Producto;
import com.springboot.app.item.models.service.ItemService;

@RestController

//Permite actualizar los controladores, componentes, services, etc. donde se 
//ingresan datos a través de @Valuo o Enviroment
@RefreshScope
public class ItemController {
	
	private static Logger log = LoggerFactory.getLogger(ItemController.class);
	
	@Autowired
	private Environment env;
	
	@Autowired
	@Qualifier("serviceFeign")
	private ItemService itemService;
	
	//Las properties especificas es aconsajable ingresarlas a través del @Value ya
	//se les puede setear un valor por defecto y no vamos a tener error de que la propertie
	//no fue encontrada
	@Value("${configuracion.texto}")
	private String texto;
	
	@GetMapping("/listar")
	public List<Item> listar(){
		return itemService.findAll();
	}

	@GetMapping("/ver/{id}/cantidad/{cantidad}")
	//Se configura un camino alternativo si llega a existir un error en los
	//microservicios que utiliza este método
	@HystrixCommand(fallbackMethod = "metodoAlternativo")
	public Item detalle(@PathVariable Long id, @PathVariable Integer cantidad){
		return itemService.findById(id, cantidad);
	}
	
	public Item metodoAlternativo(@PathVariable Long id, @PathVariable Integer cantidad){
		Item item = new Item();
		Producto producto = new Producto();
		
		producto.setId(id);
		producto.setNombre("Nombre por defecto");
		producto.setCreadoEl(new Date());
		producto.setPrecio(0.0);
		item.setProducto(producto);
		item.setCantidad(cantidad);
		
		return item;
	}
	
	@GetMapping("/obtener-config")
	public ResponseEntity<?> getConfig(){
		
		log.info(texto);
		
		Map<String, String> json = new HashMap<>();
		json.put("texto", texto);
		json.put("port", env.getProperty("server.port"));
		
		if(env.getActiveProfiles().length > 0 && env.getActiveProfiles()[0].equals("dev")) {
			json.put("autor.nombre", env.getProperty("configuracion.autor.nombre"));
			json.put("autor.email", env.getProperty("configuracion.autor.email"));
		}
		return new ResponseEntity<Map<String, String>>(json, HttpStatus.OK);
	}
}
