package org.springframework.samples.petclinic.model;


import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="factura")
public class Factura extends BaseEntity{

	@NotNull
	@JoinColumn(name="fechaPago")
	private LocalTime fechaPago;
	
	@NotNull
	@JoinColumn(name="precioTotal")
	private Double precioTotal;
	
	@Min(0)
	@Max(1)
	@NotNull
	@JoinColumn(name = "porcentaje_descuento")
	private Double porcentajeDescuento;
	
	@ManyToOne
	@NotNull
	@JoinColumn(name="cliente")
	private Cliente cliente;
		
}
