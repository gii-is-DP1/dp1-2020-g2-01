package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Transient;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "horas_trabajadas")
public class HorasTrabajadas extends BaseEntity{
	
	@NotNull
	@NotEmpty
	@JoinColumn(name="trabajo_realizado")
	private String trabajoRealizado;
	
	@NotNull
	@Min(0)
	@JoinColumn(name="precio_hora")
	private Double precioHora;
	
	@NotNull
	@Min(0)
	@JoinColumn(name="horas_trabajadas")
	private Integer horasTrabajadas;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name="empleado")
	private Empleado empleado;
	
	@Transient
	public Double getPrecioTotal() {
		return precioHora*horasTrabajadas;
	}

}
