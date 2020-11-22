package org.springframework.samples.petclinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
@Table(name="proveedores")
public class Proveedor extends BaseEntity {
	
	@NotNull
	@NotEmpty
	private String nombre;
	
	@NotNull
	@NotEmpty
	@Column(unique=true)
	private String nif;
	
	@NotNull
	private int telefono;
	
	@Email
	@NotEmpty
	private String email;
	
	
	
	//pedidosrecambios
	//recambios

	/* Restricciones:
	 * - Dni distinto
	 * - Nombre, nif y telefono son obligatorio
	 */
	
	
}
