package org.springframework.samples.petclinic.model;

import java.time.Month;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name="balances")
public class BalanceEconomicoMensual extends BaseEntity {

	@NotNull
	@Column(name="mes")
	private Month mes;
	
	@NotNull
	@Column(name="anyo")
	private int anyo;
	
	@OneToMany
	private List<Factura> factura;
	
	@OneToMany
	private List<FacturaRecambio> facturaRecambio;
	
	@Transient
	@Column(name="ingresos_mensuales")
	public Double getIngresosMensuales() {
		Double res=0.;
		for(Factura factura:factura) {
			res+=factura.getPrecioConDescuento();
		}
		return res;
	}
	
	@Transient
	@Column(name="gastos_mensuales")
	public Double getGastosMensuales() {
		Double res=0.;
		for(FacturaRecambio facturaR:facturaRecambio) {
			res+=facturaR.getPrecioTotal();
		}
		return res;
	}
	
	@Transient
	@Column(name="balance_mensual")
	public Double getBalanceMensual() {
		return getIngresosMensuales()-getGastosMensuales();
	}
}
