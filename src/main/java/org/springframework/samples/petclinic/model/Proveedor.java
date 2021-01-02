package org.springframework.samples.petclinic.model;


import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity
@Table(name="proveedores")
public class Proveedor extends BaseEntity {
	
	@NotNull
	@NotEmpty
	private String nombre;
	
	@NotNull
	@NotEmpty
	@Pattern(regexp="^[0-9]{8,8}[A-Z]$",
	message="El NIF debe estar compuesto de 8 números y 1 letra mayúscula.")
	private String nif;
	
	@NotNull
	@Pattern(regexp="^[0-9]{9,9}$")
	private String telefono;
	
	@Email
	@NotEmpty
	private String email;
	
	
	@ManyToMany
	private Collection<Recambio> recambios;
	
	
	//pedidosrecambios
	//recambios

	/* Restricciones:
	 * - Dni distinto
	 * - Nombre, nif y telefono son obligatorio
	 */
	
	
}
