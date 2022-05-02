package com.jacaranda.recursos;

import java.time.LocalDate;
import java.util.Objects;

public class Autor {
	
	private String nombre;
	private String nacionalidad;
	private LocalDate fechaNacimiento;
	
	
	public Autor(String nombre, String nacionalidad, LocalDate fechaNacimiento) throws AutorException {
		super();
		this.setNombre(nombre);
		this.setNacionalidad(nacionalidad);
		this.setFechaNacimiento(fechaNacimiento);
	}

	public Autor(String nombre) throws AutorException {
		super();
		this.setNombre(nombre);
	}
	
	
	public String getNombre() {
		return nombre;
	}


	public String getNacionalidad() {
		return nacionalidad;
	}


	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}


	private void setNombre(String nombre) throws AutorException {
		if(nombre==null || nombre.isBlank() || nombre.isEmpty()) {
			throw new AutorException("El nombre del autor no puede ser nulo, estar vacío o solo contener espacios en blanco.");
		}
		this.nombre = nombre.toUpperCase();
	}


	private void setNacionalidad(String nacionalidad) throws AutorException {
		if(nacionalidad==null || nacionalidad.isBlank() || nacionalidad.isEmpty()) {
			throw new AutorException("La nacionalidad del autor no puede ser nula, estar vacía o solo contener espacios en blanco.");
		}
		this.nacionalidad = nacionalidad.toUpperCase();
	}


	private void setFechaNacimiento(LocalDate fechaNacimiento) throws AutorException {
		if(fechaNacimiento==null) {
			throw new AutorException("La fecha de nacimiento no puede ser nula.");
		}
		this.fechaNacimiento = fechaNacimiento;
	}


	@Override
	public int hashCode() {
		return Objects.hash(nombre);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Autor other = (Autor) obj;
		return Objects.equals(nombre, other.nombre);
	}


	@Override
	public String toString() {
		return nombre + ", con nacionalidad " + nacionalidad + ", con fecha de nacimiento " + fechaNacimiento.getDayOfMonth() 
			+ "-" + fechaNacimiento.getMonthValue() + "-" + fechaNacimiento.getYear() + ".\n";
	}
	
	
}
