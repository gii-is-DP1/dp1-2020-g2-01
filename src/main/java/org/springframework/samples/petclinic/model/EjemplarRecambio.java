package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
@Table(name="ejemplarrecambios")
public class EjemplarRecambio extends BaseEntity {
	
	@JoinColumn(name="recambio_id")
	@ManyToOne(optional=false)
	private Recambio recambio;
	
	@NotNull
	@Min(0)
	private Integer cantidad;
}