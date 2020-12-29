package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="lineaFactura")
public class LineaFactura extends BaseEntity{


	@NotNull
	@JoinColumn(name = "precio")
	private Double precio;

	@Range(min=0,max=100)
	@NotNull
	@JoinColumn(name = "descuento")
	private Integer descuento;

	@ManyToOne
	@NotNull
	@JoinColumn(name="reparacion")
	private Reparacion reparacion;

	@ManyToOne
	@NotNull
	@JoinColumn(name="factura")
	private Factura factura;

}
