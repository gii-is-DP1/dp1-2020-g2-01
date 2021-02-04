package org.springframework.samples.petclinic.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.TipoVehiculo;
import org.springframework.samples.petclinic.repository.TipoVehiculoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TipoVehiculoService {
	@Autowired
	private TipoVehiculoRepository tipoVehiculoRep;
	
	@Transactional(readOnly=true)
	public List<TipoVehiculo> findAll() {
		return tipoVehiculoRep.findAll();
	}
	
	@Transactional(readOnly=true)
	public Optional<TipoVehiculo> findById(Integer id) {
		return tipoVehiculoRep.findById(id);
	}
	
	@Transactional(readOnly=true)
	public Optional<TipoVehiculo> findByTipo(String tipo) {
		return tipoVehiculoRep.findByTipo(tipo);
	}
}
