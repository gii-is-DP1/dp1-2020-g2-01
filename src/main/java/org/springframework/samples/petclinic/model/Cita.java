package org.springframework.samples.petclinic.model;


import java.time.LocalDate;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

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
	
	@ManyToOne(optional=false, cascade = CascadeType.ALL)
	@JoinColumn(name = "vehiculo_id")
	private Vehiculo vehiculo;
	
	@NotNull
	@Future
	@JoinColumn(name = "fecha")
	private LocalDate fecha;
	
	@NotNull
	@JoinColumn(name="hora")
	@Range(min = 0, max = 23)
	private Integer hora;
	
	@JoinColumn(name="tipo")
	@NotNull
	private TipoCita tipoCita;
}
