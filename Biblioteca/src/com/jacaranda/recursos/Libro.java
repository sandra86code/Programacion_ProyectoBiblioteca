package com.jacaranda.recursos;

import java.time.LocalDate;

public class Libro extends Recurso {
	
	private GeneroLibro genero;
	
	
	public Libro(String codigo, String titulo, String editorial, LocalDate fechaPublicacion, 
			String genero) throws RecursoException {
		super(codigo, titulo, editorial, fechaPublicacion);
		this.setGenero(genero);
	}


	public String getGenero() {
		return genero.toString();
	}



	@Override
	protected void setCodigo(String codigo) throws RecursoException {
		boolean incorrecto = false;
		if(codigo==null || codigo.isBlank() || codigo.isEmpty()) {
			throw new RecursoException("El ISBN de la revista no puede ser nulo, estar vacío o solo contener espacios en blanco.");
		}
		if(codigo.length()!=13) {
			throw new RecursoException("El ISBN tiene que tener 13 dígitos.");
		}
		for(int i=0; i<codigo.length() && !incorrecto; i++) {
			if(!Character.isDigit(codigo.charAt(i))) {
				incorrecto = true;
			}
		}
		if(!incorrecto) {
			this.codigo = codigo;
		}else {
			throw new RecursoException("El ISBN solo puede contener dígitos.");
		}
	}


	private void setGenero(String genero) throws RecursoException {
		try {
			this.genero = GeneroLibro.valueOf(genero.toUpperCase());
		}catch(Exception e) {
			throw new RecursoException("Valor incorrecto. Los posibles valores son: infantil, idiomas, novela, poesia, teatro, ensayo, comic, juvenil y técnico.");
		}
	}

	@Override
	public String toString() {
		return "ISBN: " + super.codigo + ". " + super.toString() + ". Género: " + genero + "\n";
	}
}

