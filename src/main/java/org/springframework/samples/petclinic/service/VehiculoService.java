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
import org.springframework.samples.petclinic.service.exceptions.DuplicatedMatriculaException;
import org.springframework.security.core.context.SecurityContextHolder;
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
	
	@Transactional(rollbackFor = DuplicatedMatriculaException.class)
	public void saveVehiculo(Vehiculo vehiculo) throws DataAccessException, DuplicatedMatriculaException {
		Optional<Vehiculo> v = vehiculoRepository.findDistinctVehiculoByMatricula(vehiculo.getMatricula());
		if(v.isPresent() && !v.get().getId().equals(vehiculo.getId())) {   
			throw new DuplicatedMatriculaException();
		} else {
			vehiculoRepository.save(vehiculo);
		}	
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
		return vehiculoRepository.findVehiculosByCliente(cliente);
	}
	
	@Transactional
	public void delete(Vehiculo vehiculo) {
		vehiculoRepository.delete(vehiculo);
	}
	
	@Transactional(readOnly = true)
	public Optional<Vehiculo> findVehiculoByMatricula(String matricula) throws DataAccessException {
		return vehiculoRepository.findDistinctVehiculoByMatricula(matricula);
	}

	public TipoVehiculo findVehiculoTypeById(int id) {
		return vehiculoRepository.findVehiculosById(id);
	}



//	@Transactional(readOnly=true)
//	public List<Vehiculo> findByClienteId(Integer id) {
//		return vehiculoRepository.findByClienteId(id);
//	}
  
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

	@Transactional(readOnly=true)
	public List<TipoVehiculo> findVehiculoTypes() {
		return vehiculoRepository.findVehiculoTypes();
	}
	
	
	//Comprueba si el usuario logeado y el propietario del vehiculo que se quiere eliminar son el mismo
	@Transactional(readOnly=true)
	public Boolean comprobarUsuarioYPropietario(Integer vehiculoId, Vehiculo v) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		String propietario = v.getCliente().getUser().getUsername();
		return username.equals(propietario);
	}
	

}
