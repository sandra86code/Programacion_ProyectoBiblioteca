package com.jacaranda.main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

import com.jacaranda.biblioteca.Biblioteca;
import com.jacaranda.biblioteca.BibliotecaException;
import com.jacaranda.recursos.AutorException;
import com.jacaranda.recursos.RecursoException;

public class PrincipalBBDD {
	
	private static Scanner teclado = new Scanner(System.in);
	
	public static void main(String[] args) {
		
		boolean added = false;
		String nombreBiblio;
		int opc;
		int subopcion;
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
		
		
		//BBDD
		try {
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_biblioteca", "root", "root");

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
				
				Statement instruccion;
				String query, query2, query3, query4;
				
				opc = leerEntero("Introduce la opción deseada: ");
				switch (opc) {
				case 1: //Añadir un autor. 
					nombre = leerCadena("Introduce el nombre y apellidos del autor: ").toUpperCase();
					nacionalidad = leerCadena("Introduce la nacionalidad del autor: ").toUpperCase();
					fechaNacimiento = leerFecha("nacimiento del autor");
					//Insertar en la base de datos
					try {
						instruccion = conexion.createStatement();
						query = "INSERT INTO AUTOR VALUES ('" + nombre + "','" + nacionalidad + "','" + fechaNacimiento +"');";
						instruccion.executeUpdate(query);
						System.out.println("Autor añadido.\n");
					}catch(Exception e) {
						System.out.println("No se ha podido añadir el autor: " + e.getMessage());
					}
					break;
				case 2: //Añadir una revista. 
					nombre = leerCadena("Introduce el nombre y apellidos del autor de la revista: ").toUpperCase();
					codigo = leerCadena("Introduce el ISSN de la revista (8 dígitos): ").toUpperCase();
					while(codigo.length()!=8) {
						codigo = leerCadena("Error en la longitud. Introduce el ISSN de la revista (8 dígitos): ").toUpperCase();
					}
					titulo = leerCadena("Introduce el título de la revista: ").toUpperCase();
					editorial = leerCadena("Introduce el nombre de la editorial que publica la revista: ").toUpperCase();
					fechaPublicacion = leerFecha("publicación de la revista");
					periodicidad = leerCadena("Introduce la periodicidad de publicación de la revista (semanal-mensual-anual): ").toUpperCase();
					try {
						instruccion = conexion.createStatement();
						//Inserto en la tabla recurso
						query = "INSERT INTO RECURSO VALUES ('" + codigo + "','" + titulo + "','" + editorial + "','" + fechaPublicacion + "');";
						instruccion.executeUpdate(query);
						//Obtengo el código para esa periodicidad
						query2 = "SELECT CODIGO FROM PERIODICIDADREVISTA WHERE " + "'" + periodicidad + "'" + " = PERIODICIDAD;";
						ResultSet resultados = instruccion.executeQuery(query2);
						int codigoPeriodicidad = 0;
						while(resultados.next()) {
							codigoPeriodicidad = resultados.getInt("CODIGO");
						}
						//Inserto en la tabla revista
						query3 = "INSERT INTO REVISTA VALUES ('" + codigo + "','" + codigoPeriodicidad + "');";
						instruccion.executeUpdate(query3);
						//Inserto en la tabla recursoautor
						query4 = "INSERT INTO RECURSOAUTOR VALUES ('" + nombre + "','" + codigo + "');";
						instruccion.executeUpdate(query4);
						System.out.println("Revista añadida.\n");
					}catch(Exception e) {
						System.out.println("No se ha podido añadir la revista: " + e.getMessage());
					}
					break;
				case 3: //Añadir un libro. 
					nombre = leerCadena("Introduce el nombre y apellidos del autor del libro: ").toUpperCase();
					codigo = leerCadena("Introduce el ISBN del libro (13 dígitos): ").toUpperCase();
					while(codigo.length()!=13) {
						codigo = leerCadena("Error en la longitud. Introduce el ISBN del libro (13 dígitos): ").toUpperCase();
					}
					titulo = leerCadena("Introduce el título del libro: ").toUpperCase();
					editorial = leerCadena("Introduce el nombre de la editorial que publica el libro: ").toUpperCase();
					fechaPublicacion = leerFecha("publicación del libro");
					genero = leerCadena("Introduce el género del libro (infantil-idiomas-novela-poesia-teatro-ensayo-comic-juvenil-tecnico): ").toUpperCase();
					try {
						instruccion = conexion.createStatement();
						//Inserto en la tabla recurso
						query = "INSERT INTO RECURSO VALUES ('" + codigo + "','" + titulo + "','" + editorial + "','" + fechaPublicacion + "');";
						instruccion.executeUpdate(query);
						//Obtengo el código para ese genero
						query2 = "SELECT CODIGO FROM GENEROLIBRO WHERE " + "'" + genero + "'" + " = GENERO;";
						ResultSet resultados2 = instruccion.executeQuery(query2);
						int codigoGenero = 0;
						while(resultados2.next()) {
							codigoGenero = resultados2.getInt("CODIGO");
						}
						//Inserto en la tabla libro
						query3 = "INSERT INTO LIBRO VALUES ('" + codigo + "','" + codigoGenero + "');";
						instruccion.executeUpdate(query3);
						//Inserto en la tabla recursoautor
						query4 = "INSERT INTO RECURSOAUTOR VALUES ('" + nombre + "','" + codigo + "');";
						instruccion.executeUpdate(query4);
						System.out.println("Libro añadido.\n");
					}catch(Exception e) {
						System.out.println("No se ha podido añadir el libro: " + e.getMessage());
					}
					break;
				case 4: //Borrar un autor
					nombre = leerCadena("Introduce el nombre y apellidos del autor: ").toUpperCase();
					try {
						Statement instruccion2 = conexion.createStatement();
						Statement instruccion3 = conexion.createStatement();
						//Busco los códigos de los recursos asociados a ese autor en la tabla RECURSO_AUTOR
						query = "SELECT CODIGO_RECURSO FROM RECURSOAUTOR WHERE NOMBRE_AUTOR = " + "'" + nombre + "';";
						ResultSet resultados3 = instruccion3.executeQuery(query);
						
						//Creo un array para poder añadir todos los códigos ya que no puedo iterar sobre el ResultSet y a la vez hacerle
						//el delete a la otra tabla
						ArrayList<String> codigosRecursos = new ArrayList<>();
						
						//Recorro el ResultSet y añado cada valor al ArrayList
						ResultSetMetaData infoResultados = resultados3.getMetaData();
						int col = infoResultados.getColumnCount();
						System.out.println(col);
						while (resultados3.next()) {
							for (int i = 1; i <= col; i++) {
								codigosRecursos.add(resultados3.getString(i));
							}
						}
						//Recorro el array y ejecuto el query delete en cada iteración con el valor adecuado para cada recurso.
						//El on delete cascade de RecursoAutor me va eliminar automáticamente todos los recursos asociados al autor
						for(String recurso : codigosRecursos) {
							query2 = "DELETE FROM RECURSO WHERE CODIGO = '" + recurso + "';";
							instruccion3.executeUpdate(query2);
						}
						
						//Por último, borro al autor de la tabla autor
						query3 = "DELETE FROM AUTOR WHERE NOMBRE = '" + nombre + "';";
						instruccion3.executeUpdate(query3);
					}catch(Exception e) {
						System.out.println("No se ha podido borrar el autor: " + e.getMessage());
					}
					break;
				case 5: //Borrar un recurso
					codigo = leerCadena("Introduce el ISSN o ISBN del recurso: ").toUpperCase();
					try {
						instruccion = conexion.createStatement();
						query = "DELETE FROM RECURSO WHERE CODIGO = '" + codigo + "';";
						instruccion.executeUpdate(query);
					}catch(Exception e) {
						System.out.println("No se ha podido borrar el recurso: " + e.getMessage());
					}
					break;
				case 6: //Modificar un autor
					menuModificaAutor();
					subopcion = leerEntero("Introduce la opción deseada: ");
					switch (subopcion) {
					case 1:
						nacionalidad = leerCadena("Introduce la nacionalidad del autor: ").toUpperCase();
						try {
							//Modificar en la base de datos
							instruccion = conexion.createStatement();
							query = "UPDATE AUTOR SET NACIONALIDAD = " + nacionalidad + ";";
							instruccion.executeUpdate(query);
						}catch(Exception e) {
							System.out.println("No se ha podido modificar la nacionalidad del autor: " + e.getMessage());
						}
						break;
					case 2:
						fechaNacimiento = leerFecha("nacimiento del autor");
						try {
							//Modificar en la base de datos
							instruccion = conexion.createStatement();
							query = "UPDATE AUTOR SET FECHANACIMIENTO = " + fechaNacimiento + ";";
							instruccion.executeUpdate(query);
						}catch(Exception e) {
							System.out.println("No se ha podido modificar la fecha de nacimiento del autor: " + e.getMessage());
						}
						break;
					default:
						System.out.println("Opción incorrecta.");
						break;
					}
					break;
				case 7: //Modificar un recurso
					menuModificaRecurso();
					subopcion = leerEntero("Introduce la opción deseada: ");
					switch (subopcion) {
					case 1:
						titulo = leerCadena("Introduce el título del libro: ").toUpperCase();
						try {
							//Modificar en la base de datos
							instruccion = conexion.createStatement();
							query = "UPDATE RECURSO SET TITULO = " + titulo + ";";
							instruccion.executeUpdate(query);
						}catch(Exception e) {
							System.out.println("No se ha podido modificar el titulo del recurso: " + e.getMessage());
						}
						break;
					case 2:
						editorial = leerCadena("Introduce el nombre de la editorial que publica el libro: ").toUpperCase();
						try {
							//Modificar en la base de datos
							instruccion = conexion.createStatement();
							query = "UPDATE RECURSO SET EDITORIAL = " + editorial + ";";
							instruccion.executeUpdate(query);
						}catch(Exception e) {
							System.out.println("No se ha podido modificar la editorial del recurso: " + e.getMessage());
						}
						break;
					case 3:
						fechaPublicacion = leerFecha("publicación del libro");
						try {
							//Modificar en la base de datos
							instruccion = conexion.createStatement();
							query = "UPDATE RECURSO SET FECHAPUBLICACION = " + fechaPublicacion + ";";
							instruccion.executeUpdate(query);
						}catch(Exception e) {
							System.out.println("No se ha podido modificar la fecha de publicación del recurso: " + e.getMessage());
						}
						break;
					default:
						System.out.println("Opción incorrecta.");
						break;
					}
					break;
				case 8: //Listar las obras de un autor.
					nombre = leerCadena("Introduce el nombre y apellidos del autor: ").toUpperCase();
					instruccion = conexion.createStatement();
					//Obtengo los resultados para ese autor de tabla recursoAutor
					query = "SELECT * FROM RECURSO WHERE " + "'" + nombre + "'" + " = NOMBRE_AUTOR;";
					ResultSet resultados2 = instruccion.executeQuery(query);

					ArrayList<String> codigosRecursos2 = new ArrayList<>();
					
					ResultSetMetaData infoResultados2 = resultados2.getMetaData();
					int col2 = infoResultados2.getColumnCount();
					while (resultados2.next()) {
						for (int i = 1; i <= col2; i++)
							codigosRecursos2.add(resultados2.getString(i));
					}
					for(String codigoRecurso : codigosRecursos2) {
						
					}
					
					break;
				default:
					if(opc!=9) {
						System.out.println("Opción incorrecta.");
					}
					break;
				}
			}while(opc!=9);

			System.out.println("Hasta la próxima");

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static void menu() {
		System.out.println("1. Añadir un autor.\n"
						+ "2. Añadir una revista.\n"
						+ "3. Añadir un libro.\n"
						+ "4. Borrar un autor.\n"
						+ "5. Borrar un recurso.\n"
						+ "6. Modificar un autor.\n"
						+ "7. Modificar un recurso.\n"
						+ "8. Listar las obras de un autor.\n"
						+ "9. Salir.\n");
	}
	
	public static void menuModificaAutor() {
		System.out.println("1. Modificar nacionalidad.\n"
						+ "2. Modificar fecha de nacimiento.\n");
	}
	
	public static void menuModificaRecurso() {
		System.out.println("1. Modificar título.\n"
						+ "2. Modificar editorial.\n"
						+ "3. Modificar fecha de publicación.\n");
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
