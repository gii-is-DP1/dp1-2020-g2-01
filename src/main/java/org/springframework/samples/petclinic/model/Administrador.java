package org.springframework.samples.petclinic.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="administradores")
public class Administrador extends Persona{

	@NotNull
	@Pattern(regexp = "^[0-9]{12}", 
	message = "El Número de Seguridad Social debe seguir contener 12 dígitos. Patrón: 123456789012")
	private String num_seg_social;
	
	@JoinColumn(name="username", referencedColumnName="username")
	@OneToOne(cascade=CascadeType.ALL)
	private User usuario;
}
