package org.springframework.samples.petclinic.model;

import java.time.LocalDate;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.websocket.OnMessage;


import org.springframework.format.annotation.DateTimeFormat;

@MappedSuperclass
public class Persona extends BaseEntity{
	
	@Column(name="dni")
	@NotEmpty
	@NotNull
	@Pattern(regexp = "^[0-9]{8}[ABCDEFGHIJKLMNOPQRSTUVWXYZ]", 
	message = "El DNI debe seguir tener 8 números y una letra. Patrón: 12345678K")
	protected String dni;
	
	@Column(name="nombre")
	@NotEmpty
	@NotNull
	protected String nombre;
	
	@Column(name="apellidos")
	@NotEmpty
	protected String apellidos;
	
	@Past
	@Column(name="fechaNacimiento")
	@DateTimeFormat(pattern="yyyy/MM/dd")
	protected LocalDate fechaNacimiento;
	
	@Column(name="telefono")
	@NotEmpty
	private String telefono;


	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(LocalDate fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}


	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	
}
