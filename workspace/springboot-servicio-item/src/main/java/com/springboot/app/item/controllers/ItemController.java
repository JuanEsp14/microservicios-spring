package com.springboot.app.item.controllers;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.springboot.app.item.models.Item;
import com.springboot.app.item.models.Producto;
import com.springboot.app.item.models.service.ItemService;

@RestController
public class ItemController {
	
	@Autowired
	@Qualifier("serviceFeign")
	private ItemService itemService;
	
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
}
