package org.springframework.samples.petclinic.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
@Entity
@Table(name = "citas")
public class Cita extends BaseEntity{

//	@ManyToOne
//	@JoinColumn(name = "taller_id")
//	private Taller taller;
	
//	@OneToMany(cascade = CascadeType.ALL, mappedBy = "cita", fetch = FetchType.EAGER)
//	private Set<Empleado> empleados;
	
	@ManyToOne
	@JoinColumn(name = "vehiculo_id")
	private Vehiculo vehiculo;
	
//	@OneToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "reparacion_id")
//	private Reparacion reparacion;
	
	@NotNull
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private LocalDate fecha;
	
	@NotNull
	@Digits(fraction = 24, integer = 2)
	private Integer hora;
	
	@NotNull
	private TipoCita tipoCita;
}
