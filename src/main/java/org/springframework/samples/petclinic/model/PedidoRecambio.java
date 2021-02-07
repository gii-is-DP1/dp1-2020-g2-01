package org.springframework.samples.petclinic.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.springframework.data.annotation.Transient;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "pedidos")
public class PedidoRecambio extends NamedEntity{
	
	@NotNull
	@Column (name="cantidad")
	@Positive
	private Integer cantidad;
	
	@NotNull
	@Positive
	@Column(name="precio_unidad")
	private Double precioPorUnidad;

	@Column(name="recibido")
	private boolean seHaRecibido = false;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name="proveedor_id")
	private Proveedor proveedor;
	
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "PedidoRecambio", fetch = FetchType.EAGER)
	private FacturaRecambio facturaRecambio;
	
	@Transient
	public Double getPrecio() {
		return precioPorUnidad*cantidad;
	}
	
//	@NotNull
//	@OneToMany(mappedBy = "pedidoRecambio")
//	@JoinColumn(name="ejemplar_id")
//	private EjemplarRecambio ejemplarRecambio;
}
