package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="ejemplarrecambios")
public class EjemplarRecambio extends BaseEntity {

	
	@JoinColumn(name="recambio_id")
	@ManyToOne(optional=false)
	private Recambio recambio;
	
}
