package org.springframework.samples.petclinic.service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Empleado;
import org.springframework.samples.petclinic.model.Vehiculo;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.repository.EmpleadoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmpleadoService {
	
	@Autowired
	private EmpleadoRepository empleadoRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthoritiesService authService;
	
	@Transactional
	public void saveEmpleado(Empleado empleado) throws DataAccessException {
		
		empleado.getUsuario().setAuthorities(new ArrayList<>());
		empleadoRepository.save(empleado);
		userService.saveUser(empleado.getUsuario());
		authService.saveAuthorities(empleado.getUsuario().getUsername(), "empleado");
	}
	
	@Transactional
	public Iterable<Empleado> findAll() {
		return empleadoRepository.findAll();
	}
	
	@Transactional
	public Optional<Empleado> findById(Integer id) {
		return empleadoRepository.findById(id);
	}
	
	@Transactional
	public void delete(Empleado empleado) {
		empleadoRepository.delete(empleado);
	}
	
	
	@Transactional(readOnly = true)
	public Optional<Empleado> findEmpleadoDni(String dni) throws DataAccessException {
		return empleadoRepository.findEmpleadoDNI(dni);
	}

}
