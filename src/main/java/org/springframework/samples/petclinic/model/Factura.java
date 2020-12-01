package org.springframework.samples.petclinic.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
@Table(name="factura")
public class Factura extends BaseEntity{
	
	@JoinColumn(name = "fecha_pago")
	private LocalDate fechaPago;
	
	@NotNull
	@JoinColumn(name = "cantidad")
	private Integer cantidad;
	
	@Min(0)
	@Max(1)
	@NotNull
	@JoinColumn(name = "porcentaje_descuento")
	private Double porcentajeDescuento;
	
	@ManyToOne
	@NotNull
	private Cliente cliente;
	
	// Falta asociación con recambios y reparación

}
