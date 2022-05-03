package com.jacaranda.lectores;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import com.jacaranda.recursos.Ejemplar;
import com.jacaranda.recursos.EjemplarException;

public class Lector {
	
	private static int siguienteSocio = 1;
	private int numSocio;
	private String nombre;
	private String dni;
	private String movil;
	private String direccion;
	private String email;
	private Multa multa;
	private List<Prestamo> prestamos;
	
	public Lector(String nombrePila, String apellidos, String dni, String movil, String direccion, String email) throws LectorException {
		super();
		this.numSocio = Lector.siguienteSocio++;
		this.setNombre(nombrePila, apellidos);
		this.setDni(dni);
		this.setMovil(movil);
		this.setDireccion(direccion);
		this.setEmail(email);
		this.prestamos = new ArrayList<>();
	}
	
	
	
	public Lector(String dni) throws LectorException {
		super();
		this.setDni(dni);
	}


	public boolean lectorMultado() {
		boolean multado = false;
		if(multa!=null && multa.getFechaFin().isBefore(LocalDate.now())) {
			multado = true;
		}
		return multado;
	}
	
	public String finalizacionMulta() {
		String mensaje = null;
		if(lectorMultado()) {
			mensaje = "Fecha finalización de la multa: " + multa.getFechaFin().getDayOfMonth() + "-" 
					+ multa.getFechaFin().getMonthValue() + "-" + multa.getFechaFin().getYear();
		}
		return mensaje;
	}
	
	private void setNombre(String nombrePila, String apellidos) throws LectorException {
		if(nombrePila==null || nombrePila.isBlank() || nombrePila.isEmpty()) {
			throw new LectorException("El nombre de pila del socio no puede ser nulo, ni estar en vacío ni solo contener espacios en blanco.");
		}
		if(apellidos==null || apellidos.isBlank() || apellidos.isEmpty()) {
			throw new LectorException("El campo apellidos del socio no puede ser nulo, estar vacío o solo contener espacios en blanco.");
		}
		this.nombre = apellidos.toUpperCase() + ", " + nombrePila.toUpperCase();
	}


	private void setDni(String dni) throws LectorException {
		if(dni==null) {
			throw new LectorException("El DNI del socio no puede ser nulo.");
		}
		if(!esDniCorrecto(dni)) {
			throw new LectorException("El DNI introducido no es correcto.");
		}
		this.dni = dni.toUpperCase();
	}

	private boolean esDniCorrecto(String dni) {
		boolean esCorrecto = false;
		String caracteres = "TRWAGMYFPDXBNJZSQVHLCKE";
		if(dni.length()==8 || dni.length()==9) {
			int dniSinLetra=Integer.parseInt(dni.substring(0, dni.length()-1));
			char letra = dni.charAt(dni.length()-1);
			int resto = dniSinLetra%23;
	        if(caracteres.charAt(resto)==Character.toUpperCase(letra)) {
	        	esCorrecto = true;
	        }
		}
		return esCorrecto;
	}
	
	
	private void setMovil(String movil) throws LectorException {
		boolean movilIncorrecto = false;
		if(movil==null) {
			throw new LectorException("El móvil no puede ser nulo.");
		}
		if(movil.length()!=9) {
			throw new LectorException("Número de móvil incorrecto. Debe contener 9 dígitos");
		}
		
		for(int i=0; i<movil.length() && !movilIncorrecto; i++) {
			if(!Character.isDigit(movil.charAt(i))) {
				movilIncorrecto=true;
			}
		}
		if(movilIncorrecto) {
			throw new LectorException("El número de móvil solo puede contener dígitos.");
		}
		this.movil = movil;
	}


	private void setDireccion(String direccion) throws LectorException {
		if(direccion==null || direccion.isBlank() || direccion.isEmpty()) {
			throw new LectorException("La dirección del socio no puede ser nula, estar vacía o solo contener espacios en blanco.");
		}
		this.direccion = direccion.toUpperCase();
	}


	private void setEmail(String email) throws LectorException {
		if(movil==null || movil.isBlank() || movil.isEmpty()) {
			throw new LectorException("El email del socio no puede ser nulo, estar vacío o solo contener espacios en blanco.");
		}
		this.email = email.toLowerCase();
	}


	public int getNumSocio() {
		return numSocio;
	}


	public void setNumSocio(int numSocio) {
		this.numSocio = numSocio;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getDni() {
		return dni;
	}


	public String getMovil() {
		return movil;
	}


	public String getDireccion() {
		return direccion;
	}


	public String getEmail() {
		return email;
	}

	public void addPrestamo(Ejemplar ejemplar) throws LectorException {
		if(lectorMultado()) {
			throw new LectorException("Este socio está multado. Podrá hacer préstamos el " + multa.getFechaFin().getDayOfMonth() 
					+ "-" + multa.getFechaFin().getMonthValue() + "-" + multa.getFechaFin().getYear());
		}
		if(prestamos.size()>4) {
			throw new LectorException("No puede realizar préstamos ya que tiene 4 activos.");
		}
		if(ejemplar==null) {
			throw new LectorException("El ejemplar no puede ser nulo.");
		}
		if(ejemplar.getEstado().equalsIgnoreCase("PRESTADO") || ejemplar.getEstado().equalsIgnoreCase("REPARACION")) {
			throw new LectorException("El ejemplar no está disponible para el préstamo.");
		}
		Prestamo prestamo;
		try {
			prestamo = new Prestamo(ejemplar);
		} catch (PrestamoException e) {
			throw new LectorException(e.getMessage());
		}
		this.prestamos.add(prestamo);
		try {
			ejemplar.setEstado("PRESTADO");
		} catch (EjemplarException e) {
			throw new LectorException(e.getMessage());
		}
	}
	
	public void removePrestamo(Ejemplar ejemplar) throws LectorException {
		if(ejemplar==null) {
			throw new LectorException("El ejemplar no puede ser nulo.");
		}
		for(Prestamo p : prestamos) {
			Ejemplar e = p.getEjemplar();
			if(e.equals(ejemplar)) {
				try {
					e.setEstado("DISPONIBLE");
					if(p.getFechaFin().isAfter(LocalDate.now())) {
						long dias = Duration.between(p.getFechaFin(), LocalDate.now()).toDays(); 
						multa = new Multa((int)dias);
					}
					prestamos.remove(p);
				} catch (EjemplarException | MultaException e1) {
					throw new LectorException(e1.getMessage());
				}
			}
		}
	}
	

	
	public String listarPrestamos() {
		StringBuilder mensaje = new StringBuilder("El socio " + numSocio + " tiene los siguientes préstamos: \n");
		if(prestamos.isEmpty()) {
			mensaje.append("0 préstamos.");
		}else {
			for(Prestamo p : prestamos) {
				mensaje.append("\t" + p + "\n");
			}
		}
		return mensaje.toString();
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(dni);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Lector other = (Lector) obj;
		return Objects.equals(dni, other.dni);
	}


	@Override
	public String toString() {
		return "Lector con número de socio: " + numSocio + ", Nombre: " + nombre + ", DNI: " + dni + ", Móvil: " + movil
				+ ", Dirección: " + direccion + ", email: " + email;
	}



	public int numeroPrestamosActuales() {
		return prestamos.size();
	}
	
	
	
	
	
}
