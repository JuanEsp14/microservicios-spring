package com.springboot.app.usuarios.models.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import com.springboot.app.usuarios.models.entity.Usuario;

//Paging And Sorting hereda todo de Repository pero le agrega la paginación y 
//funciones de Sort

//Todos los métodos del CRUD son exportados por esta anotación sin necesidad 
//de escribirlo a mano de manera HATEOAS -> restricción de la arquitectura API Rest
//devuelve información en forma de hipervínculo para informarle al usuario cómo debe
//navegar o interactuar con la app
@RepositoryRestResource(path="usuarios")
public interface UsuarioDAO extends PagingAndSortingRepository<Usuario, Long> {

	//Ambos métodos hacen lo mismo, la primera utiliza las propiedades de 
	//Spring Data JPA y el segundo tmbién utiliza JPA pero la anotación Query
	//con lo cual deben escribir la consulta a mano
	//Las anotaciones @Restresource y @Param me permiten configurar la ruta y 
	//el nombre del parámetro a utilizar
	@RestResource(path="buscar-username")
	public Usuario findByUsername(@Param("username") String username);
	
	@Query("select u from Usuario u where u.username = ?1")
	public Usuario obtenerPorUsername(String username);
	
}
