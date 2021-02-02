package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "recambios")
public class Recambio extends NamedEntity {
	
	//PREGUNTAR CAMBIOS EN EL UML
	
	@NotNull
	@ManyToOne
	@JoinColumn(name="tipo_vehiculo_id")
	private TipoVehiculo tipoVehiculo;
	
	@Min(0)
	@JoinColumn(name="cantidad_actual")
	private int cantidadActual;
	
}
