package com.jacaranda.recursos;

import java.time.LocalDate;

public class Revista extends Recurso {
	
	private PeriodicidadRevista periodicidad;
	
	
	public Revista(String codigo, String titulo, String editorial, LocalDate fechaPublicacion, 
			String periodicidad) throws RecursoException {
		super(codigo, titulo, editorial, fechaPublicacion);
		this.setPeriodicidad(periodicidad);
	}



	public String getPeriodicidad() {
		return periodicidad.toString();
	}

	@Override
	protected void setCodigo(String codigo) throws RecursoException {
		boolean incorrecto = false;
		if(codigo==null || codigo.isBlank() || codigo.isEmpty()) {
			throw new RecursoException("El ISSN de la revista no puede ser nulo, estar vacío o solo contener espacios en blanco.");
		}
		if(codigo.length()!=8) {
			throw new RecursoException("El ISSN tiene que tener 8 dígitos.");
		}
		for(int i=0; i<codigo.length() && !incorrecto; i++) {
			if(!Character.isDigit(codigo.charAt(i))) {
				incorrecto = true;
			}
		}
		if(!incorrecto) {
			this.codigo = codigo;
		}else {
			throw new RecursoException("El ISSN solo puede contener dígitos.");
		}
	}


	private void setPeriodicidad(String periodicidad) throws RecursoException {
		try {
			this.periodicidad = PeriodicidadRevista.valueOf(periodicidad.toUpperCase());
		}catch(Exception e) {
			throw new RecursoException("Valor incorrecto. Solo puede ser semanal, mensual o anual.");
		}
	}


	@Override
	public String toString() {
		return "ISSN: " + super.codigo + ". " + super.toString() + ". Periodicidad: " + periodicidad + "\n";
	}
}
