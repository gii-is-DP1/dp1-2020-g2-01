package org.springframework.samples.petclinic.model;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
@Table(name="clientes")
public class Cliente extends Persona{

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "cliente")
	private List<Vehiculo> vehiculos;
	
	@NotNull
	@NotEmpty
	private String username;
	
	@NotNull
	@NotEmpty
	private String password;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "cliente")
	private List<Authorities> authorities;
	
	
	
	
	
}
