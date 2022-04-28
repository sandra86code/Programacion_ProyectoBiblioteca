package com.jacaranda.recursos;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;

public abstract class Recurso {
	
	protected String codigo;
	protected String titulo;
	protected String editorial;
	protected LocalDate fechaPublicacion;
	protected HashSet<Ejemplar> ejemplares;
	
	
	
	protected Recurso(String codigo, String titulo, String editorial, LocalDate fechaPublicacion) throws RecursoException {
		super();
		this.setCodigo(codigo);
		this.setTitulo(titulo);
		this.setEditorial(editorial);
		this.setFechaPublicacion(fechaPublicacion);
		this.ejemplares = new HashSet<>();
	}


	protected abstract void setCodigo(String codigo) throws RecursoException;

	
	public String getCodigo() {
		return codigo;
	}


	protected String getTitulo() {
		return titulo;
	}


	protected String getEditorial() {
		return editorial;
	}


	protected LocalDate getFechaPublicacion() {
		return fechaPublicacion;
	}


	private void setTitulo(String titulo) throws RecursoException {
		if(titulo==null || titulo.isBlank() || titulo.isEmpty()) {
			throw new RecursoException("El título no puede ser nulo, estar vacío o solo contener espacios en blanco.");
		}
		this.titulo = titulo.toUpperCase();
	}


	private void setEditorial(String editorial) throws RecursoException {
		if(editorial==null || editorial.isBlank() || editorial.isEmpty()) {
			throw new RecursoException("La editorial no puede ser nulo, estar vacío o solo contener espacios en blanco.");
		}
		this.editorial = editorial.toUpperCase();
	}


	private void setFechaPublicacion(LocalDate fechaPublicacion) throws RecursoException {
		if(fechaPublicacion==null) {
			throw new RecursoException("La fecha de publicación no puede ser nula.");
		}
		this.fechaPublicacion = fechaPublicacion;
	}


	
	@Override
	public int hashCode() {
		return Objects.hash(codigo);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Recurso other = (Recurso) obj;
		return Objects.equals(codigo, other.codigo);
	}


	@Override
	public String toString() {
		return "Título: " + titulo + ". Editorial: " + editorial + ". Fecha de publicación: " 
				+ fechaPublicacion.getDayOfMonth() + "-" + fechaPublicacion.getMonthValue() + "-" 
				+ fechaPublicacion.getYear() + ". Numéro de ejemplares: " + ejemplares.size() ;
	}	
	
	
	public void addEjemplar(Ejemplar e) throws RecursoException {
		if(e==null || !ejemplares.add(e)) {
			throw new RecursoException("Ejemplar nulo o repetido.");
		}
	}
	
	
	public String verEjemplares() {
		StringBuilder mensaje = new StringBuilder();
		for(Ejemplar e : ejemplares) {
			mensaje.append(e);
		}
		return mensaje.toString();
	}
}
