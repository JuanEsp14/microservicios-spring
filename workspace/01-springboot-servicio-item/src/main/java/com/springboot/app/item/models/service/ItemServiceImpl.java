package com.springboot.app.item.models.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.springboot.app.item.models.Item;
import com.springboot.app.item.models.Producto;

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private WebClient clientRest;
	
	@Override
	public List<Item> findAll() {
		WebClient.RequestBodySpec request = clientRest.method(HttpMethod.GET).uri("/listar");
		List<Producto> productos = Arrays.asList(request.exchange().block().bodyToMono(Producto[].class).block());
		return productos.stream().map(p -> new Item(p, 1)).collect(Collectors.toList());
	}

	@Override
	public Item findById(Long id, Integer cantidad) {
		Map<String, String> pathVariables = new HashMap<String, String>();
		pathVariables.put("id", id.toString());
		WebClient.RequestBodySpec request = clientRest.method(HttpMethod.GET).uri("/ver/{id}", pathVariables);
		Producto producto = request.exchange().block().bodyToMono(Producto.class).block();
		return new Item(producto, cantidad);
	}

}
