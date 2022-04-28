package com.jacaranda.lectores;

import java.time.LocalDate;
import com.jacaranda.recursos.Ejemplar;


public class Prestamo {
	
	private LocalDate fechaInicio;
	private LocalDate fechaFin;
	private Ejemplar ejemplar;
	
	
	public Prestamo(Ejemplar ejemplar) throws PrestamoException {
		super();
		if(ejemplar==null) {
			throw new PrestamoException("El ejemplar no puede ser nulo.");
		}
		this.ejemplar = ejemplar;
		this.fechaInicio = LocalDate.now();
		this.fechaFin = fechaInicio.plusDays(20);
	}


	public LocalDate getFechaFin() {
		return fechaFin;
	}


	public LocalDate getFechaInicio() {
		return fechaInicio;
	}

	
	public Ejemplar getEjemplar() {
		return ejemplar;
	}


	@Override
	public String toString() {
		return "Ejemplar prestado: " + ejemplar + ". Fecha de inicio del préstamo: " + fechaInicio.getDayOfMonth() + "-" 
				+ fechaInicio.getMonthValue() + "-" + fechaInicio.getYear() + ". Fecha de fin de multa: " + fechaFin.getDayOfMonth() 
				+ "-" + fechaFin.getMonthValue() + "-" + fechaFin.getYear();
	}
}
