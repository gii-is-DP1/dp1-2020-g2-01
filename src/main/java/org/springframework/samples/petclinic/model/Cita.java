package org.springframework.samples.petclinic.model;


import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
@Entity
@Table(name = "citas" , uniqueConstraints = @UniqueConstraint(columnNames = {"fecha", "hora"}))
public class Cita extends BaseEntity{

//	@ManyToOne(cascade = CascadeType.ALL)
//	@JoinColumn(name = "taller_id")
//	private Taller taller;
	
//	@ManyToMany
//	@JoinColumn(name ="empleados")
//	private List<Empleado> empleados;
  
	@ManyToOne
	@JoinColumn(name = "vehiculo_id")
	private Vehiculo vehiculo;
	
	@NotNull
	@Future
	@JoinColumn(name = "fecha")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private LocalDate fecha;
	
	@NotNull
	@JoinColumn(name="hora")
	@Range(min = 9, max = 21)
	private Integer hora;
	
	@JoinColumn(name="tipo")
	@ManyToOne
	private TipoCita tipoCita;
	
	
	
	
}
