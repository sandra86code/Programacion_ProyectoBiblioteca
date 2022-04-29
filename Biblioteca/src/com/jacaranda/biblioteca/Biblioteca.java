package com.jacaranda.biblioteca;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
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

	private String nombre;
	private List<Lector> lectores;
	private List<Recurso> recursos;
	private List<Autor> autores;
	private List<RecursoAutor> recursosAutores;

	public Biblioteca(String nombre) throws BibliotecaException {
		super();
		this.setNombre(nombre);
		this.lectores = new ArrayList<>();
		this.recursos = new ArrayList<>();
		this.autores = new ArrayList<>();
		this.recursosAutores = new ArrayList<>();
	}

	public String getNombre() {
		return nombre;
	}

	private void setNombre(String nombre) throws BibliotecaException {
		if (nombre == null || nombre.isBlank() || nombre.isEmpty()) {
			throw new BibliotecaException(
					"El nombre de la biblioteca no puede ser nulo, estar vacío o solo contener espacios en blanco.");
		}
		this.nombre = nombre.toUpperCase();
	}

	public void addLector(String nombrePila, String apellidos, String dni, String movil, String direccion, String email)
			throws BibliotecaException {
		try {
			Lector lector = new Lector(nombrePila, apellidos, dni, movil, direccion, email);
			// Si el lector no está en la lista de lectores (por dni), lo añade a la lista.
			if (this.lectores.indexOf(lector) == -1) {
				lectores.add(lector);
				// Si el lector está en la lista de lectores, lanza excepción.
			} else {
				throw new BibliotecaException("Socio ya existente.");
			}
		} catch (LectorException e) {
			throw new BibliotecaException(e.getMessage());
		}
	}

	public void addAutor(String nombre, String nacionalidad, LocalDate fechaNacimiento) throws BibliotecaException {
		try {
			Autor autor = new Autor(nombre, nacionalidad, fechaNacimiento);
			// Si el autor no está en la lista de autores, lo añade a la lista
			if (this.autores.indexOf(autor) == -1) {
				autores.add(autor);
				// Si el autor está en la lista de autores, lanza excepción
			} else {
				throw new BibliotecaException("Autor ya existente.");
			}
		} catch (AutorException e) {
			throw new BibliotecaException(e.getMessage());
		}

	}

	public void addRevista(Autor autor, String codigo, String titulo, String editorial, LocalDate fechaPublicacion,
			String periodicidad) throws BibliotecaException {
		if (autor == null) {
			throw new BibliotecaException("El autor no puede ser nulo.");
		}
		Revista revista;
		try {
			revista = new Revista(codigo, titulo, editorial, fechaPublicacion, periodicidad);
			// Si la revista no está en la lista de recursos, lo añade a la lista
			if (this.recursos.indexOf(revista) == -1) {
				recursos.add(revista);
				// Si la revista está en la lista de recursos, lanza excepción.
			} else {
				throw new BibliotecaException("Revista ya existente.");
			}
			// Si el autor no está en la lista de autores, lo añade a la lista
			if (autores.indexOf(autor) == -1) {
				autores.add(autor);
			}
			RecursoAutor ra = new RecursoAutor(revista, autor);
			recursosAutores.add(ra);
		} catch (RecursoException e) {
			throw new BibliotecaException(e.getMessage());
		} catch (RecursoAutorException e) {
			throw new BibliotecaException(e.getMessage());
		}
	}

	public void addLibro(Autor autor, String codigo, String titulo, String editorial, LocalDate fechaPublicacion,
			String genero) throws BibliotecaException {
		if (autor == null) {
			throw new BibliotecaException("El autor no puede ser nulo.");
		}
		Libro libro;
		try {
			libro = new Libro(codigo, titulo, editorial, fechaPublicacion, genero);
			// Si el libro no está en la lista de recursos, lo añade a la lista
			if (this.recursos.indexOf(libro) == -1) {
				recursos.add(libro);
				// Si el libro está en la lista de recursos, lanza excepción.
			} else {
				throw new BibliotecaException("Revista ya existente.");
			}
			// Si el autor no está en la lista de autores, lo añade a la lista
			if (autores.indexOf(autor) == -1) {
				autores.add(autor);
			}
			RecursoAutor ra = new RecursoAutor(libro, autor);
			recursosAutores.add(ra);
		} catch (RecursoException e) {
			throw new BibliotecaException(e.getMessage());
		} catch (RecursoAutorException e) {
			throw new BibliotecaException(e.getMessage());
		}
	}

	public void addEjemplarRecurso(Recurso recurso, String estado, String localizacion) throws BibliotecaException {
		if (recurso == null) {
			throw new BibliotecaException("El recurso no puede ser nulo.");
		}
		// Si el recurso no está en la lista de recursos, lanzo excepción
		if (this.recursos.indexOf(recurso) == -1) {
			throw new BibliotecaException("El recurso no está registrado, primero debe añadirlo.");
		}
		try {
			// Añado el ejemplar al recurso
			this.recursos.get(this.recursos.indexOf(recurso)).addEjemplar(new Ejemplar(estado, localizacion));
		} catch (RecursoException e) {
			throw new BibliotecaException(e.getMessage());
		} catch (EjemplarException e) {
			throw new BibliotecaException(e.getMessage());
		}
	}

	// Añado préstamo al socio
	public void addPrestamo(String dniSocio, String codigo) throws BibliotecaException {
		// Compruebo si existe el lector
		Lector lector;
		try {
			lector = new Lector(dniSocio);
			if (this.lectores.indexOf(lector) == -1) {
				throw new BibliotecaException("Lector no registrado.");
			}
			// Compruebo si el lector está multado. Si lo está, lanzo excepción
			lector = this.lectores.get(this.lectores.indexOf(lector));
			if (lector.lectorMultado()) {
				throw new BibliotecaException(lector.finalizacionMulta());
			}
			// Compruebo si el lector tiene menos de 4 préstamos
			if (lector.numeroPrestamosActuales() == 4) {
				throw new BibliotecaException(
						"El lector no puede realizar más préstamos. Antes debe devolver algún préstamo de los actuales.");
			}
			// Comprobar si existe el recurso
			Recurso recurso = null;
			if (codigo != null && codigo.length() == 13) {
				recurso = new Libro(codigo);
			} else if (codigo != null && codigo.length() == 8) {
				recurso = new Revista(codigo);
			}
			if (this.recursos.indexOf(recurso) == -1) {
				throw new BibliotecaException("Recurso no registrado.");
			}
			// Comprobar si hay ejemplares disponibles del recurso
			Ejemplar e = this.recursos.get(this.recursos.indexOf(recurso)).ejemplarDisponible();
			if (e == null) {
				throw new BibliotecaException("No hay ejemplares disponibles para préstamo del recurso indicado.");
			}
			// Añado el préstamo
			lector.addPrestamo(e);
		} catch (LectorException e1) {
			throw new BibliotecaException(e1.getMessage());
		} catch (RecursoException e1) {
			throw new BibliotecaException(e1.getMessage());
		}
	}

	private void devolverPrestamo(String dniSocio, int codigoEjemplar) throws BibliotecaException {
		// Compruebo si existe el lector
		Lector lector;
		try {
			lector = new Lector(dniSocio);
			if (this.lectores.indexOf(lector) == -1) {
				throw new BibliotecaException("Lector no registrado.");
			}
			this.lectores.get(this.lectores.indexOf(lector)).removePrestamo(null);
			// Comprobar si el lector tiene un ejemplar de ese recurso prestado
			HashSet<Ejemplar> ejemplares = this.recursos.get(this.recursos.indexOf(recursos)).getEjemplares();
			
			// Devolver préstamo
			lector.removePrestamo(e);
		} catch (LectorException e1) {
			throw new BibliotecaException(e1.getMessage());
		} catch (RecursoException e1) {
			throw new BibliotecaException(e1.getMessage());
		}
	}

//	private String buscarSocio(String dni) {
//		
//	}
//
//	private String buscarLibroPorIsbn(String isbn) {
//
//	}
//
//	private String buscarRevistaPorIssn(String issn) {
//
//	}
//
//	private String buscarObrasDeAutor(String nombre, LocalDate fechaNacimiento) {
//
//	}

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
