package org.springframework.samples.petclinic.model;


import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "citas" , uniqueConstraints = @UniqueConstraint(columnNames = {"fecha", "hora"}))
public class Cita extends BaseEntity{

	@ManyToOne
	@NotNull
	@JoinColumn(name = "taller_id")
	private Taller taller;
	
	@ManyToMany
	@JoinTable(name="citas_empleados")
	private List<Empleado> empleados;
  
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
	@Range(min = 9, max = 20)
	private Integer hora;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name="citas_tipocita")
	@Size(min=1, max=3)
	@NotEmpty
	private List<TipoCita> tiposCita;

	@JoinColumn(name="descripcion")
	private String descripcion;
	
	@Column(name="asistido")
	private boolean asistido;
}
