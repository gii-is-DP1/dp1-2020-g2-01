package org.springframework.samples.petclinic.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

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
	
	@Min(0)
	@Max(1)
	@NotNull
	@JoinColumn(name = "descuento")
	private Double descuento;
	
	@OneToOne
	@NotNull
	@JoinColumn(name="reparacion")
	private Reparacion reparacion;
	
	@ManyToOne
	@NotNull
	@JoinColumn(name="factura")
	private Factura factura;

}
