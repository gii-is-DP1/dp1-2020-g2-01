package org.springframework.samples.petclinic.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotEmpty;

import org.springframework.format.annotation.DateTimeFormat;

@MappedSuperclass
public class Persona extends BaseEntity{
	
	@Column(name="dni")
	@NotEmpty
	protected String dni;
	
	@Column(name="nombre")
	@NotEmpty
	protected String nombre;
	
	@Column(name="apellidos")
	@NotEmpty
	protected String apellidos;
	
	@Column(name="fechaNacimiento")
	@NotEmpty
	@DateTimeFormat(pattern="yyyy/MM/dd")
	protected LocalDate fechaNacimiento;
	
	@Column(name="telefono")
	@NotEmpty
	protected Integer telefono;

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

	public Integer getTelefono() {
		return telefono;
	}

	public void setTelefono(Integer telefono) {
		this.telefono = telefono;
	}
	
}
