
package org.springframework.samples.petclinic.model;

import java.time.LocalDate;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;

import lombok.Data;

@Data
@Entity
@Table(name = "reparaciones")
public class Reparacion extends NamedEntity {
	
	@NotNull
//	@FutureOrPresent
	@DateTimeFormat(pattern="dd/MM/yyyy")
	private LocalDate tiempoEstimado;
	
	@Nullable
//	@FutureOrPresent
	@DateTimeFormat(pattern="dd/MM/yyyy")
	private LocalDate fechaFinalizacion;
	
//	@FutureOrPresent
	@DateTimeFormat(pattern="dd/MM/yyyy")
	private LocalDate fechaEntrega;
	
//	@FutureOrPresent
	@DateTimeFormat(pattern="dd/MM/yyyy")
	private LocalDate fechaRecogida;
	
	@NotEmpty
	@NotNull
	private String descripcion;
	
	@OneToOne(optional=false)
	private Cita cita;
	
	//Reparacioon->Empleado, ManyToMany
	@ManyToMany
	private Collection<Empleado> empleados;
	
	
	//Reparacion->Factura, OneToOne(optional)
	//private Factura factura;

}
