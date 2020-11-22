package org.springframework.samples.petclinic.service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.TipoVehiculo;
import org.springframework.samples.petclinic.model.Vehiculo;
import org.springframework.samples.petclinic.repository.VehiculoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VehiculoService {

	private VehiculoRepository vehiculoRepository;
	
	@Autowired
	public VehiculoService(VehiculoRepository vehiculoRepository) {
		this.vehiculoRepository = vehiculoRepository;
	}
	
	@Transactional
	public void saveVehiculo(Vehiculo vehiculo) throws DataAccessException {
		vehiculoRepository.save(vehiculo);
	}
	
	@Transactional(readOnly = true)
	public Iterable<Vehiculo> findAll() throws DataAccessException {
		return vehiculoRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public Optional<Vehiculo> findVehiculoById(int id) throws DataAccessException {
		return vehiculoRepository.findById(id);
	}
	
	
	@Transactional
	public void delete(Vehiculo vehiculo) {
		vehiculoRepository.delete(vehiculo);
	}
	
	@Transactional(readOnly = true)
	public Vehiculo findVehiculoByMatricula(String matricula) throws DataAccessException {
		Vehiculo v = vehiculoRepository.findVehiculoMatricula(matricula);
		return v;
	}

	public List<TipoVehiculo> findVehiculoTypes() {
		return vehiculoRepository.findVehiculoTypes();
	}

}
