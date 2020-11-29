package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
@Table(name="tipocita")
public class TipoCita extends BaseEntity {
	
	@NotNull
	@NotEmpty
	private String tipo;

}
