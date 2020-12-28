package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;

import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity
@Table(name = "talleres")
public class Taller extends NamedEntity {
	
	@NotNull
	@Pattern(regexp="^[0-9]{9,9}$",
	message="El teléfono debe estar compuesto de 9 números.")
	private String telefono;
	
	@NotNull
	@NotEmpty
	@Pattern(regexp="^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$",
	message="El correo electrónico no tiene la forma apropiada.")
	private String correo;
	
	@NotNull
	@NotEmpty
	private String ubicacion;
	
	

}
