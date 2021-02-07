package org.springframework.samples.petclinic.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="facturas_recambios")
public class FacturaRecambio extends BaseEntity{

	@NotNull
	@Column(name="fecha_pago")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private LocalDate fechaPago;
	
	@OneToOne
	@JoinColumn(name = "pedido_recambio")
	private PedidoRecambio PedidoRecambio;
	
	@NotNull
	@Column(name="precio")
	private Double precioTotal;
}
