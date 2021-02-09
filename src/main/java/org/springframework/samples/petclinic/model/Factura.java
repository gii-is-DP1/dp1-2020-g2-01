package org.springframework.samples.petclinic.model;


import java.time.LocalDate;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.PastOrPresent;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="facturas")
public class Factura extends BaseEntity{
	
	@JoinColumn(name="fecha_Pago")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@PastOrPresent
	private LocalDate fechaPago;
		
	@OneToMany(mappedBy="factura")
	private List<LineaFactura> lineaFactura;
	
	@Transient
	public Double getPrecioConDescuento() {
		return (double)Math.round(getPrecioTotal()*(1-getDescuento()/100.0)*100d)/100d + getCargaExtraMonetaria();
	}

	
	@Transient
	public Double getPrecioTotal() {
		Double resultado = 0.;
		for(LineaFactura linea:lineaFactura) {
			resultado += linea.getPrecio();
		}
		for(HoraTrabajada horas: lineaFactura.get(0).getReparacion().getHorasTrabajadas()) {
			resultado += horas.getPrecioTotal();
		}
		return resultado;
	}
	
	@Transient
	public Integer getDescuento() {
		return Math.min(calcularDiasPasadasFechaEsperada()*10,50);
	}
	
	@Transient
	public Integer getCargaExtraMonetaria() {
		Integer dias = Math.max(10, calcularDiasPasadasFechaRecogida()) - 10;
		return dias*20;
	}

	public Integer calcularDiasPasadasFechaEsperada() {
		int i = (int) ((int) lineaFactura.get(0).getReparacion()
				.getFechaFinalizacion().toEpochDay() - lineaFactura.get(0).getReparacion().getTiempoEstimado().toEpochDay());
		return i/10;
	}
	
	public Integer calcularDiasPasadasFechaRecogida() {
		int fecha = lineaFactura.get(0).getReparacion()
				.getFechaRecogida() != null ? (int) lineaFactura.get(0).getReparacion().getFechaRecogida().toEpochDay() 
						: (int) LocalDate.now().toEpochDay();
				
		int i = (int) (fecha - lineaFactura.get(0).getReparacion().getFechaFinalizacion().toEpochDay());
		return i;
	}

}
