package org.springframework.samples.petclinic.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
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
	
	@OneToMany
	private List<PedidoRecambio> lineaPedido;
	
	@Transient
	public Double getPrecioTotal() {
		Double res=0.;
		for(PedidoRecambio pedido:lineaPedido) {
			res+=pedido.getPrecio();
		}
		return res;
	}
}
