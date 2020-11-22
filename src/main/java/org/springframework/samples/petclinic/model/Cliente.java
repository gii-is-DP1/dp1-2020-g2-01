package org.springframework.samples.petclinic.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="clientes")
public class Cliente extends Persona{

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "cliente")
	private List<Vehiculo> vehiculos;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="username", referencedColumnName = "username")
	private User user;
	
	
	
	
	
}
