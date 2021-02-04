package org.springframework.samples.petclinic.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "reparaciones")
public class Reparacion extends BaseEntity {
	
	@NotNull
	@FutureOrPresent
	@DateTimeFormat(pattern="dd/MM/yyyy")
	private LocalDate tiempoEstimado;
	
	@Nullable
	@FutureOrPresent
	@DateTimeFormat(pattern="dd/MM/yyyy")
	private LocalDate fechaFinalizacion;
	
	@FutureOrPresent
	@DateTimeFormat(pattern="dd/MM/yyyy")
	private LocalDate fechaEntrega;
	
	@FutureOrPresent
	@DateTimeFormat(pattern="dd/MM/yyyy")
	private LocalDate fechaRecogida;
	
	@NotEmpty
	@NotNull
	private String descripcion;
	
	@NotNull
	@OneToOne(optional=false)
	private Cita cita;
	
	@NotNull
	@NotEmpty
	@OneToMany
	@JoinTable(name="horas_reparacion")
	private List<HorasTrabajadas> horasTrabajadas;
	
	
	@OneToMany(mappedBy="reparacion")
	private List<LineaFactura> lineaFactura;

}
