package com.jacaranda.recursos;

import java.util.Objects;

public class RecursoAutor {
	
	private Recurso recurso;
	private Autor autor;
	
	
	public RecursoAutor(Recurso recurso, Autor autor) throws RecursoAutorException {
		super();
		this.setRecurso(recurso);
		this.setAutor(autor);
	}


	public Autor getAutor() throws AutorException {
		return new Autor(autor.getNombre(), autor.getNacionalidad(), autor.getFechaNacimiento());
	}

	public Recurso getRecurso() {
		return recurso;
	}

	
	private void setAutor(Autor autor) throws RecursoAutorException {
		if(autor==null) {
			throw new RecursoAutorException("El autor no puede ser nulo.");
		}
		this.autor = autor;
	}


	private void setRecurso(Recurso recurso) throws RecursoAutorException {
		if(recurso==null) {
			throw new RecursoAutorException("El recurso no puede ser nulo.");
		}
		this.recurso = recurso;
	}


	@Override
	public int hashCode() {
		return Objects.hash(autor, recurso);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RecursoAutor other = (RecursoAutor) obj;
		return Objects.equals(autor, other.autor) && Objects.equals(recurso, other.recurso);
	}
	
	
	
}
