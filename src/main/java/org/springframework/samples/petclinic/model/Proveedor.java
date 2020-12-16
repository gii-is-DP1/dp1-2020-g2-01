package org.springframework.samples.petclinic.model;


import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

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
	@Pattern(regexp="^[0-9]{8,8}[A-Z]$")
	private String nif;
	
	@NotNull
	@Pattern(regexp="^[0-9]{9,9}$")
	private String telefono;
	
	@Email
	@NotEmpty
	@Pattern(regexp="^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")
	private String email;
	
	
	
	//pedidosrecambios
	//recambios

	/* Restricciones:
	 * - Dni distinto
	 * - Nombre, nif y telefono son obligatorio
	 */
	
	
}
