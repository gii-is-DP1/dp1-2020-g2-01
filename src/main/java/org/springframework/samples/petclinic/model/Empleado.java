package org.springframework.samples.petclinic.model;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "empleados")
public class Empleado extends Persona {
	
	@NotNull
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private LocalDate fecha_ini_contrato, fecha_fin_contrato;
	
	@NotNull
	@Min(950)
	private Integer sueldo;
	
	@NotNull
	@Pattern(regexp = "^[0-9]{12}", 
	message = "El Número de Seguridad Social debe seguir contener 12 dígitos. Patrón: 123456789012")
	private String num_seg_social;
	
	@JoinColumn(name="username", referencedColumnName="username")
	@OneToOne(cascade=CascadeType.ALL)
	private User usuario;

	@ManyToOne
	@NotNull
	@JoinColumn(name = "taller_id")
	private Taller taller;
	
	
    
	
	

}
