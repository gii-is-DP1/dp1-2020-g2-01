package org.springframework.samples.petclinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "pedidos")
public class PedidoRecambio extends NamedEntity{
	
	@NotNull
	@NotEmpty
	@Column (name="cantidad")
	@Positive
	private Integer cantidad;
	
	
	@NotNull
	@Positive
	@NotEmpty
	@Column(name="precio_unidad")
	private float precioPorUnidad;

	@Column(name="recibido")
	private boolean seHaRecibido;
	
	@NotNull
	@NotEmpty
	@ManyToOne
	@JoinColumn(name="proveedor_id")
	private Proveedor proveedor;
	
}
