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
		
	@OneToMany(mappedBy="factura")
	private List<LineaFactura> lineaFactura;
	
	@Transient
	public Double getPrecioConDescuento() {
		return (double)Math.round(getPrecioTotal()*(1-getDescuento()/100.0)*100d)/100d;
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
	
	@Transient
	public Integer getDescuento() {
		return Math.min(calcularDiasPasadasFechaEsperada()*10,50);
	}

	public Integer calcularDiasPasadasFechaEsperada() {
		int i = (int) ((int) lineaFactura.get(0).getReparacion().getFechaFinalizacion().toEpochDay() - lineaFactura.get(0).getReparacion().getTiempoEstimado().toEpochDay());
		return i/10;
	}

}
