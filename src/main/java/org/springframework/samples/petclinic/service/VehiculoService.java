package org.springframework.samples.petclinic.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Cita;
import org.springframework.samples.petclinic.model.Cliente;
import org.springframework.samples.petclinic.model.TipoVehiculo;
import org.springframework.samples.petclinic.model.Vehiculo;
import org.springframework.samples.petclinic.repository.VehiculoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VehiculoService {

	private VehiculoRepository vehiculoRepository;

	@Autowired
	protected EntityManager em;
	
	@Autowired
	public VehiculoService(VehiculoRepository vehiculoRepository) {
		this.vehiculoRepository = vehiculoRepository;
	}
	
	@Transactional
	public void saveVehiculo(Vehiculo vehiculo) throws DataAccessException {
		vehiculoRepository.save(vehiculo);
	}
	
	@Transactional(readOnly = true)
	public List<Vehiculo> findAll() throws DataAccessException {
		return vehiculoRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public Optional<Vehiculo> findVehiculoById(int id) throws DataAccessException {
		return vehiculoRepository.findById(id);
	}
	
	@Transactional
	public List<Vehiculo> findVehiculosCliente(Cliente cliente) throws DataAccessException {
		return vehiculoRepository.findVehiculosCliente(cliente);
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

<<<<<<< Upstream, based on origin/master
=======
	@Transactional(readOnly=true)
	public List<Vehiculo> findByClienteId(Integer id) {
		return vehiculoRepository.findByClienteId(id);
	}
  
>>>>>>> fa41054 Trabajando en H03
	@Transactional
	public List<Vehiculo> getVehiculosSeleccionadoPrimero(Cita cita) {
		Integer vehiculoId = cita.getVehiculo().getId();
		em.clear(); // Con esto evito que el cliente y las citas sean null por lo que pongo para evitar el stackoverflow
		Vehiculo vehiculo = vehiculoRepository.findById(vehiculoId).get();
		List<Vehiculo> vehiculos = vehiculoRepository.findAll();
		vehiculos.remove(vehiculo); 
		vehiculos.add(0, vehiculo); 
		return vehiculos;
	}

}
