package org.springframework.samples.petclinic.model;


import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "solicitudes")
public class Solicitud extends BaseEntity {
	
	@NotNull
	private Boolean terminada;
	
	@NotNull
	@Min(1)
	private Integer cantidad;
	
	@NotNull
	@ManyToOne(optional=false)
	@JoinColumn(name = "recambio_id")
	private Recambio recambio;
	
	@NotNull
	@ManyToOne(optional=false)
	@JoinColumn(name = "empleado_id")
	private Empleado empleado;
	
	
	@NotNull
	@ManyToOne(optional=false)
	@JoinColumn(name="reparacion_id")
	private Reparacion reparacion;
	

}
