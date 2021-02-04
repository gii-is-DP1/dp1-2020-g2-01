package org.springframework.samples.petclinic.model;

import static java.time.temporal.ChronoUnit.DAYS;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;
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
		
	@Range(min = 0, max = 100)
	@NotNull
	@JoinColumn(name = "descuento")
//	private Integer descuento = calcularDiasPasadasFechaEsperada()*10;
	private Integer descuento;
	
	@OneToMany(mappedBy="factura")
	private List<LineaFactura> lineaFactura;
	
	@Transient
	public Double getPrecioConDescuento() {
		return (double)Math.round(getPrecioTotal()*(1-descuento/100.0)*100d)/100d;
	}
	
	@Transient
	public Double getPrecioTotal() {
		Double resultado = 0.;
		for(LineaFactura linea:lineaFactura) {
			resultado += linea.getPrecio();
		}
		for(HorasTrabajadas horas: lineaFactura.get(0).getReparacion().getHorasTrabajadas()) {
			resultado += horas.getPrecioTotal();
		}
		return resultado;
	}

	public Integer calcularDiasPasadasFechaEsperada() {
		int i = (int) DAYS.between(lineaFactura.get(0).getReparacion().getFechaFinalizacion(), lineaFactura.get(0).getReparacion().getTiempoEstimado());
		return i/10;
	}
}
