package org.springframework.samples.petclinic.model;


import java.time.LocalDate;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;
import org.springframework.data.annotation.Transient;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="facturas")
public class Factura extends BaseEntity{

	@NotNull
	@JoinColumn(name="fecha_Pago")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private LocalDate fechaPago;
	
	@NotNull
	@JoinColumn(name="precio_Total")
	private Double precioTotal;
	
	@NotNull
	@Range(min = 0, max = 100)
	@JoinColumn(name = "porcentaje_descuento")
	private Integer porcentajeDescuento;
	
	@OneToMany
	@JoinColumn(name="lineaFactura")
	private List<LineaFactura> lineaFactura;
	
	@Transient
	public Double getPrecioConDescuento() {
		return (double)Math.round(precioTotal*(1-porcentajeDescuento/100.0)*100d)/100d;
	}
}
