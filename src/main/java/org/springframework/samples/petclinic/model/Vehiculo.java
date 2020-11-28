package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
@Table(name = "vehiculos")
public class Vehiculo extends BaseEntity {
	
	@NotNull
	@NotEmpty
	private String matricula;
	
	@NotNull
	@NotEmpty
	private String numBastidor;
	
	@NotNull
	@NotEmpty
	private String modelo;
	
	@ManyToOne
	@JoinColumn(name="tipo_vehiculo_id")
	private TipoVehiculo tipoVehiculo;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "vehiculo", fetch = FetchType.LAZY)
	private List<Cita> citas;

	@ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
	
	
}
