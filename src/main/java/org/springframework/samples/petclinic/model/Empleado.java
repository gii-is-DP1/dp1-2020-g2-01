package org.springframework.samples.petclinic.model;

import java.time.LocalDate;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import lombok.Data;

@Data
@Entity
@Table(name = "empleados")
public class Empleado extends Persona {
	
	@NotNull
	@NotEmpty
	private String correo;
	
	@NotNull
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private LocalDate fecha_ini_contrato, fecha_fin_contrato;
	
	@NotNull
	private Long sueldo;
	
	@NotNull
	@NotEmpty
	private String num_seg_social;
	
	@JoinColumn(name="username", referencedColumnName="username")
	@OneToOne(cascade=CascadeType.ALL)
	private User usuario;


	
	
    
	
	

}
