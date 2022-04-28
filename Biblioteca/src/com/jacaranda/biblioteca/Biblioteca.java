package com.jacaranda.biblioteca;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import com.jacaranda.lectores.Lector;
import com.jacaranda.lectores.LectorException;
import com.jacaranda.recursos.Autor;
import com.jacaranda.recursos.AutorException;
import com.jacaranda.recursos.Ejemplar;
import com.jacaranda.recursos.EjemplarException;
import com.jacaranda.recursos.Libro;
import com.jacaranda.recursos.Recurso;
import com.jacaranda.recursos.RecursoAutor;
import com.jacaranda.recursos.RecursoAutorException;
import com.jacaranda.recursos.RecursoException;
import com.jacaranda.recursos.Revista;

public class Biblioteca {
	
	private int nombre;
	private List<Lector> lectores;
	private List<Recurso> recursos;
	private List<Autor> autores;
	private List<RecursoAutor> recursosAutores;
	
	
	public void addLector(String nombrePila, String apellidos, String dni, String movil, String direccion, String email) throws BibliotecaException {
		try {
			Lector lector = new Lector(nombrePila, apellidos, dni, movil, direccion, email);
			if(this.lectores.indexOf(lector)==-1) {
				lectores.add(lector);
			}else {
				throw new BibliotecaException("Socio ya existente.");
			}
		} catch (LectorException e) {
			throw new BibliotecaException(e.getMessage());
		}
	}
	
	public void addAutor(String nombre, String nacionalidad, LocalDate fechaNacimiento) throws BibliotecaException {
		try {
			Autor autor = new Autor(nombre, nacionalidad, fechaNacimiento);
			if(this.autores.indexOf(autor)==-1) {
				autores.add(autor);
			}else {
				throw new BibliotecaException("Autor ya existente.");
			}
		} catch (AutorException e) {
			throw new BibliotecaException(e.getMessage());
		}
			
	}
	
	public void addEjemplarRevista(Autor autor, String codigo, String titulo, String editorial, String periodicidad, 
			LocalDate fechaPublicacion, String estado, String localizacion) throws BibliotecaException {
		if(autor==null) {
			throw new BibliotecaException("El autor no puede ser nulo.");
		}
		try {
			Revista revista = new Revista(codigo, titulo, editorial, fechaPublicacion, periodicidad);
			if(autores.indexOf(autor)==-1) {
				autores.add(autor);
			}else {
				RecursoAutor ra = new RecursoAutor(revista, autor);
				if(recursosAutores.indexOf(ra)==-1) {
					recursosAutores.add(ra);
				}else {
					
				}
			}
			if(this.recursos.indexOf(revista)==-1) {
				recursos.add(revista);
			}
			this.recursos.get(this.recursos.indexOf(revista)).addEjemplar(new Ejemplar(estado, localizacion));
			
			
		} catch (RecursoException e) {
			throw new BibliotecaException(e.getMessage());
		} catch (EjemplarException e) {
			throw new BibliotecaException(e.getMessage());
		} catch (RecursoAutorException e) {
			throw new BibliotecaException(e.getMessage());
		}
	}
	
	public void addEjemplarLibro(String codigo, String titulo, String editorial, LocalDate fechaPublicacion, String genero, 
			String estado, String localizacion) throws BibliotecaException {
		try {
			Libro libro = new Libro(codigo, titulo, editorial, fechaPublicacion, genero);
			if(this.recursos.indexOf(libro)==-1) {
				recursos.add(libro);
			}
			this.recursos.get(this.recursos.indexOf(libro)).addEjemplar(new Ejemplar(estado, localizacion));
		} catch (RecursoException e) {
			throw new BibliotecaException(e.getMessage());
		} catch (EjemplarException e) {
			throw new BibliotecaException(e.getMessage());
		}
	}
	
	public void addPrestamoLibro(String dniSocio, String codigo) {
		if(codigo.length()==) {
			//es un libro
		}else if(codigo.length()==8) {
			//es una revista
		}
	}
	
	private void devolverPrestamo(String dniSocio, String codigo) {
		
	}
	
	
	private String buscarSocio(String dni) {
		
	}
	
	private String buscarLibroPorIsbn(String isbn) {
		
	}
	
	private String buscarRevistaPorIssn(String issn) {
		
	}
	
	private String buscarObrasDeAutor(String nombre, LocalDate fechaNacimiento) {
		
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
		Biblioteca other = (Biblioteca) obj;
		return nombre == other.nombre;
	}
	
	
	
}
