package com.jacaranda.recursos;

import java.util.Objects;

public class Ejemplar {
	
	private int codigo;
	private EstadoEjemplar estado;
	private String localizacion;
	
	
	public Ejemplar(String estado, String localizacion) throws EjemplarException {
		super();
		this.codigo = hashCode();
		this.setEstado(estado);
		this.setLocalizacion(localizacion);
	}


	public void setEstado(String estado) throws EjemplarException {
		if(estado==null) {
			throw new EjemplarException("El estado del ejemplar no puede ser nulo.");
		}
		try {
			this.estado = EstadoEjemplar.valueOf(estado.toUpperCase());
		}catch(Exception e) {
			throw new EjemplarException(e.getMessage());
		}
	}


	private void setLocalizacion(String localizacion) throws EjemplarException {
		if(localizacion==null || localizacion.isBlank() || localizacion.isEmpty()) {
			throw new EjemplarException("La localización del ejemplar no puede ser nula, estar vacía o solo contener espacios en blanco.");
		}
		this.localizacion = localizacion.toUpperCase();
	}

	
	public int getCodigo() {
		return codigo;
	}


	public String getEstado() {
		return estado.toString();
	}


	public String getLocalizacion() {
		return localizacion;
	}


	@Override
	public int hashCode() {
		return Objects.hash(localizacion);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ejemplar other = (Ejemplar) obj;
		return Objects.equals(localizacion, other.localizacion);
	}


	@Override
	public String toString() {
		return "Ejemplar con código: " + codigo + ", estado: " + estado.toString() + ", localización: " + localizacion;
			
	}
	
	
	
}
