package com.jacaranda.lectores;

import java.time.LocalDate;

public class Multa {
	
	private LocalDate fechaInicio;
	private LocalDate fechaFin;
	
	
	public Multa(int diasRetraso) throws MultaException {
		super();
		this.fechaInicio = LocalDate.now();
		if(diasRetraso<=0) {
			throw new MultaException("Los días de retraso de la entrega tienen que ser un número entero positivo.");
		}
		this.fechaFin = fechaInicio.plusDays(diasRetraso*10);
	}


	public LocalDate getFechaFin() {
		return fechaFin;
	}


	public LocalDate getFechaInicio() {
		return fechaInicio;
	}


	@Override
	public String toString() {
		return "Fecha de inicio de la multa: " + fechaInicio.getDayOfMonth() + "-" + fechaInicio.getMonthValue() + "-" 
				+ fechaInicio.getYear() + ". Fecha de fin de multa: " + fechaFin.getDayOfMonth() + "-" + fechaFin.getMonthValue() 
				+ "-" + fechaFin.getYear();
	}
	
	
	
}
