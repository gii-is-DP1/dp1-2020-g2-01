package org.springframework.samples.petclinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
@Table(name = "vehiculos")
public class Vehiculo extends BaseEntity {
	
	@NotNull
	@NotEmpty
	private String matricula;
	
	@NotNull
	@NotEmpty
	private String numBastidor;
	
	@NotNull
	@NotEmpty
	private String modelo;
	
	private TipoVehiculo tipoVehiculo;

	
	
	
}
