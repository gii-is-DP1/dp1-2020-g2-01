package org.springframework.samples.petclinic.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "vehiculos")
public class Vehiculo extends BaseEntity {
	
	@NotNull
	@NotEmpty
	@Column(unique=true)
	@Pattern(regexp="^[0-9]{4}[A-Z]{3}$",
	message="La matrícula debe estar compuesta de 4 números y 3 letras.")
	private String matricula;
	
	@NotNull
	@NotEmpty
	@Pattern(regexp="^[A-HJ-NPR-Z0-9]{17}$",
	message="El número de bastidor está compuesto de 17 caracteres.")
	private String numBastidor;
	
	@NotNull
	@NotEmpty
	private String modelo;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name="tipo_vehiculo_id")
	private TipoVehiculo tipoVehiculo;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "vehiculo")
	private List<Cita> citas;

	@ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
	
	
}
