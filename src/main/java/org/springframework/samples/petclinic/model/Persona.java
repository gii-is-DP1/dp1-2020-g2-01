package org.springframework.samples.petclinic.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.springframework.format.annotation.DateTimeFormat;

@MappedSuperclass
public class Persona extends BaseEntity{
	
	@Column(name="dni", unique=true)
	@NotEmpty
	@Pattern(regexp = "^[0-9]{8}[ABCDEFGHIJKLMNOPQRSTUVWXYZ]", 
	message = "El DNI debe seguir tener 8 números y una letra. Patrón: 12345678K")
	protected String dni;
	
	@Column(name="nombre")
	@NotEmpty
	protected String nombre;
	
	@Column(name="apellidos")
	@NotEmpty
	protected String apellidos;
	
	@Past
	@Column(name="fecha_nacimiento")
	@DateTimeFormat(pattern="dd/MM/yyyy")
	protected LocalDate fechaNacimiento;
	
	@Column(name="telefono")
	@NotEmpty
	@Pattern(regexp="^[0-9]{9,9}$")
	private String telefono;
	
	@Column(name="email")
	@Email
	@NotEmpty
	private String email;


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
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email=email;
	}
	
}
