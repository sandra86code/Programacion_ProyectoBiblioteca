package com.jacaranda.main;


import java.time.LocalDate;
import java.util.Scanner;

import com.jacaranda.biblioteca.Biblioteca;
import com.jacaranda.biblioteca.BibliotecaException;
import com.jacaranda.recursos.AutorException;
import com.jacaranda.recursos.RecursoException;

public class Principal {
	
	private static Scanner teclado = new Scanner(System.in);
	
	public static void main(String[] args) {
		
				
		boolean added = false;
		String nombreBiblio;
		int opc;
		//Datos del lector
		String nombrePila;
		String apellidos;
		String dni;
		String movil;
		String direccion;
		String email;
		//Datos autor
		String nombre;
		String nacionalidad;
		LocalDate fechaNacimiento;
		//Datos revista y libro
		String codigo;
		String titulo;
		String editorial;
		LocalDate fechaPublicacion;
		String periodicidad;
		String genero;
		//Datos ejemplar
		String estado;
		String localizacion;
		
		
		nombreBiblio = leerCadena("Introduce el nombre de la biblioteca: ");
		Biblioteca biblio = null;
		while(!added) {
			try {
				biblio = new Biblioteca(nombreBiblio);
				added = true;
			} catch (BibliotecaException e) {
				System.out.println(e.getMessage());
			}
		}
		System.out.println("Bienvenid@ a la Biblioteca " + biblio.getNombre() + "\n");
		do {
			menu();
			opc = leerEntero("Introduce la opción deseada: ");
			switch (opc) {
			case 1: //Añadir un lector
				nombrePila = leerCadena("Introduce el nombre de pila del lector: ");
				apellidos = leerCadena("Introduce los apellidos del lector: ");
				dni = leerCadena("Introduce el DNI del lector: ");
				movil = leerCadena("Introduce el n.º de teléfono móvil del lector: ");
				direccion = leerCadena("Introduce la dirección del lector: ");
				email = leerCadena("Introduce el email del lector: ");
				try {
					biblio.addLector(nombrePila, apellidos, dni, movil, direccion, email);
					System.out.println("Lector añadido.\n");
				} catch (BibliotecaException e) {
					System.out.println(e.getMessage() + "\n");
				}
				break;
			case 2: //Añadir un autor
				nombre = leerCadena("Introduce el nombre y apellidos del autor: ");
				nacionalidad = leerCadena("Introduce la nacionalidad del autor: ");
				fechaNacimiento = leerFecha("de nacimiento del autor");
				try {
					biblio.addAutor(nombre, nacionalidad, fechaNacimiento);
					System.out.println("Autor añadido.\n");
				} catch (BibliotecaException e) {
					System.out.println(e.getMessage() + "\n");
				}
				break;
			case 3: //Añadir una revista
				nombre = leerCadena("Introduce el nombre y apellidos del autor de la revista: ");
				codigo = leerCadena("Introduce el ISSN de la revista (8 dígitos): ");
				titulo = leerCadena("Introduce el título de la revista: ");
				editorial = leerCadena("Introduce el nombre de la editorial que publica la revista: ");
				fechaPublicacion = leerFecha("de publicación de la revista");
				periodicidad = leerCadena("Introduce la periodicidad de publicación de la revista (semanal-mensual-anual): ");
				try {
					biblio.addRevista(nombre, codigo, titulo, editorial, fechaPublicacion, periodicidad);
					System.out.println("Revista añadida.\n");
				} catch (BibliotecaException e) {
					System.out.println(e.getMessage() + "\n");
				}
				break;
			case 4: //Añadir un libro
				nombre = leerCadena("Introduce el nombre y apellidos del autor del libro: ");
				codigo = leerCadena("Introduce el ISBN del libro (13 dígitos): ");
				titulo = leerCadena("Introduce el título  del libro: ");
				editorial = leerCadena("Introduce el nombre de la editorial que publica el libro: ");
				fechaPublicacion = leerFecha("de publicación del libro");
				genero = leerCadena("Introduce el género del libro (infantil-idiomas-novela-poesia-teatro-ensayo-comic-juvenil-tecnico): ");
				try {
					biblio.addLibro(nombre, codigo, titulo, editorial, fechaPublicacion, genero);
					System.out.println("Libro añadido.\n");
				} catch (BibliotecaException e) {
					System.out.println(e.getMessage() + "\n");
				}
				break;
			case 5: //Añadir un ejemplar de un recurso (revista o libro).
				codigo = leerCadena("Introduce el ISSN de la revista (8 dígitos) o el ISBN del libro (13 dígitos): ");
				estado = leerCadena("Introduce el estado del ejemplar (prestado-disponible-reparacion): ");
				localizacion = leerCadena("Introduce la localización del ejemplar en la biblioteca: ");
				try {
					biblio.addEjemplarRecurso(codigo, estado, localizacion);
					System.out.println("Ejemplar añadido.\n");
				} catch (BibliotecaException | RecursoException e) {
					System.out.println(e.getMessage() + "\n");
				}
				break;
			case 6: //Añadir un préstamo a un lector
				dni = leerCadena("Introduce el DNI del lector: ");
				codigo = leerCadena("Introduce el ISSN de la revista (8 dígitos) o el ISBN del libro (13 dígitos): ");
				try {
					biblio.addPrestamo(dni, codigo);
					System.out.println("Préstamo realizado.\n");
				} catch (BibliotecaException e) {
					System.out.println(e.getMessage() + "\n");
				}
				break;
			case 7: //Devolver un préstamo de un lector
				dni = leerCadena("Introduce el DNI del lector: ");
				codigo = leerCadena("Introduce el código del ejemplar que quiere devolver: ");
				try {
					biblio.devolverPrestamo(dni, codigo);
					System.out.println("Devolución realizada.\n");
				} catch (BibliotecaException e) {
					System.out.println(e.getMessage() + "\n");
				}
				break;
			case 8: //Consultar los préstamos actuales de un lector.
				dni = leerCadena("Introduce el DNI del lector: ");
				try {
					System.out.println(biblio.listarPrestamosLector(dni));
				} catch (BibliotecaException e) {
					System.out.println(e.getMessage() + "\n");
				}
				break;
			case 9: //Listar lectores
				System.out.println(biblio.listarLectores());
				break;
			case 10: //Listar recursos con sus respectivos ejemplares.
				System.out.println(biblio.listarRecursosConEjemplares());
				break;
			case 11: //Listar los ejemplares de un recurso (revista o libro)
				codigo = leerCadena("Introduce el ISSN de la revista (8 dígitos) o el ISBN del libro (13 dígitos): ");
				try {
					System.out.println(biblio.verEjemplaresDeRecurso(codigo));
				} catch (RecursoException e) {
					System.out.println(e.getMessage() + "\n");
				}
				break;
			case 12: //Listar las obras de un autor
				nombre = leerCadena("Introduce el nombre y apellidos del autor: ");
				try {
					System.out.println(biblio.listarObrasDeAutor(nombre));
				} catch (AutorException e) {
					System.out.println(e.getMessage() + "\n");
				}
				break;
			default:
				if(opc!=13) {
					System.out.println("Opción incorrecta.");
				}
				break;
			}
		}while(opc!=13);
		
		System.out.println("Hasta la próxima");
	}
	
	public static void menu() {
		System.out.println("1. Añadir un lector.\n"
						+ "2. Añadir un autor.\n"
						+ "3. Añadir una revista.\n"
						+ "4. Añadir un libro.\n"
						+ "5. Añadir un ejemplar de un recurso (revista o libro).\n"
						+ "6. Realizar préstamo a un lector.\n"
						+ "7. Devolver préstamo de un lector.\n"
						+ "8. Consultar los préstamos actuales de un lector.\n"
						+ "9. Listar lectores.\n"
						+ "10. Listar recursos con sus respectivos ejemplares.\n"
						+ "11. Listar los ejemplares de un recurso (revista o libro).\n"
						+ "12. Listar las obras de un autor.\n"
						+ "13. Salir.\n");
	}
	
	
	public static LocalDate leerFecha(String cadena) {
		boolean fechaCorrecta = false;
		LocalDate fecha = null;
		while (!fechaCorrecta) {
			try {
				String fechaCadena = leerCadena("Introduce la fecha de " + cadena + " (DD/MM/AAAA): ");
				int separador = fechaCadena.indexOf("/");
				int dia = Integer.parseInt(fechaCadena.substring(0, separador));
				String restoFecha = fechaCadena.substring(separador + 1);
				separador = restoFecha.indexOf("/");
				int mes = Integer.parseInt(restoFecha.substring(0, separador));
				int anno = Integer.parseInt(restoFecha.substring(separador + 1));
				fecha = LocalDate.of(anno, mes, dia);
				if(fecha.isBefore(LocalDate.now())) {
					fechaCorrecta = true;
				}else {
					System.out.println("La fecha de " + cadena + " tiene que ser anterior a la fecha actual.");
				}
			} catch (Exception e) {
				System.out.println("La fecha no es correcta. Vuelve a introducir los datos.");
			}
		}
		return fecha;
	}
	
	
	
	/**
		 * Método de lectura de cadenas (String) por teclado
		 * @param texto que le sale al usuario por consola
		 * @return el String introducido por el usuario
		 */
	public static String leerCadena(String texto) {
		System.out.println(texto);
		return teclado.nextLine();
	}

	/**
	 * Método de lectura de números enteros (int) por teclado
	 * @param texto que le sale al usuario por consola
	 * @return el número entero introducido por el usuario
	 */
	public static int leerEntero(String texto) {
		System.out.println(texto);
		int resultado = 0;
		boolean lecturaCorrecta = false;
		do {
			try {
				resultado = Integer.parseInt(teclado.nextLine());
				lecturaCorrecta = true;
			} catch (Exception e) {
				System.out.println("No has introducido un número entero. Prueba otra vez.");
			}
		} while (!lecturaCorrecta);

		return resultado;
	}
}
