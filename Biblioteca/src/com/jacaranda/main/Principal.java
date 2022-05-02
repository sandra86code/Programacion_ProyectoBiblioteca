package com.jacaranda.main;

import java.util.Scanner;

public class Principal {
	
	private static Scanner teclado = new Scanner(System.in);
	
	public static void main(String[] args) {
		
	}
	
	public static boolean esDniCorrecto(String dni) {
		boolean esCorrecto = false;
		String caracteres = "TRWAGMYFPDXBNJZSQVHLCKE";
		if(dni.length()==8 || dni.length()==9) {
			int dniSinLetra=Integer.parseInt(dni.substring(0, dni.length()-1));
			char letra = dni.charAt(dni.length()-1);
			int resto = dniSinLetra%23;
	        if(caracteres.charAt(resto)==letra) {
	        	esCorrecto = true;
	        }
		}
		return esCorrecto;
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

	/**
	 * Método de lectura de números reales (double) por teclado
	 * @param texto que le sale al usuario por consola
	 * @return el número real (double) introducido por el usuario
	 */
	public static double leerReal(String texto) {
		System.out.println(texto);
		double resultado = 0;
		boolean lecturaCorrecta = false;
		do {
			try {
				resultado = Double.parseDouble(teclado.nextLine());
				lecturaCorrecta = true;
			} catch (Exception e) {
				System.out.println("No has introducido un número real (decimal). Prueba otra vez.");
			}
		} while (!lecturaCorrecta);

		return resultado;
	}

	/**
	 * Método de lectura de un carácter (char) por teclado
	 * @param texto que le sale al usuario por consola
	 * @return el carácter introducido por el usuario
	 */
	public static char leerCaracter(String texto) {
		System.out.println(texto);
		char resultado = 0;
		boolean lecturaCorrecta = false;
		do {
			try {
				resultado = teclado.nextLine().charAt(0);
				lecturaCorrecta = true;
			} catch (Exception e) {
				System.out.println("No has introducido un carácter. Prueba otra vez.");
			}
		} while (!lecturaCorrecta);

		return resultado;
	}
}
