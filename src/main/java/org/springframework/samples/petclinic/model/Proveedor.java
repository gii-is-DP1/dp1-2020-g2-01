package org.springframework.samples.petclinic.model;


import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name="proveedores")
public class Proveedor extends NamedEntity {
	
	
	@NotNull
	@NotEmpty
	@Pattern(regexp="^[0-9]{8,8}[A-Z]$",
	message="El NIF debe estar compuesto de 8 números y 1 letra mayúscula.")
	@Column(name="nif")
	private String nif;
	
	@NotNull
	@Column(name="telefono")
	@Pattern(regexp="^[0-9]{9,9}$")
	private String telefono;
	
	@Email
	@NotEmpty
	@Column(name="email")
	private String email;
	
	
	@ManyToMany
	//@JoinColumn(name="recambio_id")
	private Collection<Recambio> recambios;
	
	@OneToMany(mappedBy="proveedor")
	private Collection<PedidoRecambio> pedidos;

	/* Restricciones:
	 * - Dni distinto
	 * - Nombre, nif y telefono son obligatorio
	 */
	
	
}
