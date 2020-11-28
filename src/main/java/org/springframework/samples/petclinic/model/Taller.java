package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name = "talleres")
public class Taller extends NamedEntity {
	
	@NotNull
	private Integer telefono;
	
	@NotNull
	@NotEmpty
	private String correo, ubicacion;

}
