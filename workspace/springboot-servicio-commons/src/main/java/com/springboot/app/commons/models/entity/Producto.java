package com.springboot.app.commons.models.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name = "productos")
public class Producto implements Serializable{

	private static final long serialVersionUID = 524008437855650582L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; 
	
	private String nombre;
	private Double precio;
	
	//Solo se agrega el column a este campo porque cambia su nombre en la base
	@Column(name = "creado_el")
	@Temporal(TemporalType.TIMESTAMP)
	private Date creadoEl;
	
	//Se agrega este annotation para indicar que el valor este no va a la base
	@Transient
	private Integer port;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Double getPrecio() {
		return precio;
	}
	public void setPrecio(Double precio) {
		this.precio = precio;
	}
	public Date getCreadoEl() {
		return creadoEl;
	}
	public void setCreadoEl(Date creadoEl) {
		this.creadoEl = creadoEl;
	}
	public Integer getPort() {
		return port;
	}
	public void setPort(Integer port) {
		this.port = port;
	}

}
