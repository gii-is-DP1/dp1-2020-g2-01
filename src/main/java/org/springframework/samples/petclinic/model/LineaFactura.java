package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;
import org.springframework.data.annotation.Transient;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="linea_factura")
public class LineaFactura extends BaseEntity{


	@NotNull
	@JoinColumn(name = "precio_base")
	private Double precioBase;

	@Range(min=0,max=100)
	@NotNull
	@JoinColumn(name = "descuento")
	private Integer descuento;

	@ManyToOne
	@NotNull
	@JoinColumn(name="reparacion")
	private Reparacion reparacion;

	@ManyToOne
	@JoinColumn(name="factura")
	private Factura factura;
	
	private String recambio; // Cambiarlo cuando se haga el recambio

	@Transient
	public Double getPrecio() {
		return Math.round(precioBase*(1-descuento/100.0)*100d)/100d;
	}
<<<<<<< Upstream, based on origin/master
}
=======
}
>>>>>>> 4172321 Añadiendo linea factura a reparacion
