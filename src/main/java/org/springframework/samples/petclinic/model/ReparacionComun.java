package org.springframework.samples.petclinic.model;


import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
@Table(name = "reparacionescomunes")
public class ReparacionComun extends BaseEntity {

	@NotNull
	@NotEmpty
	private String nombre, descripcion;
}
