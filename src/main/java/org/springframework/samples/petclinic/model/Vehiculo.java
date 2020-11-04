package org.springframework.samples.petclinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
@Table(name = "vehiculos")
public class Vehiculo extends BaseEntity {
	
	@NotNull
	private String matricula;
	
	@NotNull
	private String numBastidor;
	
	@NotNull
	private String modelo;
	
	private TipoVehiculo tipoVehiculo;

	
	
	
}
