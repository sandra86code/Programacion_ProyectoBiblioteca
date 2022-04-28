package com.jacaranda.main;

public class Principal {

	public static void main(String[] args) {
		String dni = "15409981G";
		String dni2 = "75410143C";
		String dni3 = "15409981C";
		String dni4 = "154099851G";
		System.out.println(esDniCorrecto(dni));
		System.out.println(esDniCorrecto(dni2));
		System.out.println(esDniCorrecto(dni3));
		System.out.println(esDniCorrecto(dni4));
	}
	
	private static boolean esDniCorrecto(String dni) {
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
}
