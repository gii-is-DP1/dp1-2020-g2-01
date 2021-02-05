package org.springframework.samples.petclinic.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Empleado;
import org.springframework.samples.petclinic.repository.EmpleadoRepository;
import org.springframework.samples.petclinic.service.exceptions.InvalidPasswordException;
import org.springframework.samples.petclinic.service.exceptions.NoMayorEdadEmpleadoException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.samples.petclinic.service.exceptions.InvalidPasswordException;
import org.springframework.samples.petclinic.service.exceptions.NoMayorEdadEmpleadoException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EmpleadoService {
	
	@Autowired
	private EmpleadoRepository empleadoRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthoritiesService authService;
	
	
	@Transactional
	public void saveEmpleado(Empleado empleado) throws NoMayorEdadEmpleadoException, DataAccessException, InvalidPasswordException {
		LocalDate fechaMin = LocalDate.now().minusYears(18);
		if(empleado.getFechaNacimiento().isAfter(fechaMin)) {
			throw new NoMayorEdadEmpleadoException();
		}
		
		empleado.getUsuario().setAuthorities(new ArrayList<>());
		empleadoRepository.save(empleado);
		userService.saveUser(empleado.getUsuario());
		authService.saveAuthorities(empleado.getUsuario().getUsername(), "empleado");
		log.info("Empleado y usuario con authorities empleado creado");
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
		log.info("Empleado con id " + empleado.getId() + " borrado");
	}
	
	@Transactional(readOnly = true)
	public Optional<Empleado> findEmpleadoDni(String dni) throws DataAccessException {
		return empleadoRepository.findEmpleadoDNI(dni);
	}
	
	@Transactional(readOnly = true)
	public Optional<Empleado> findEmpleadoByUsuarioUsername(String username) throws DataAccessException {
		return empleadoRepository.findEmpleadoByUsuarioUsername(username);
	}
	
	public Boolean verificarContraseña(String username, String password) {
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String passEncoded = passwordEncoder.encode(password);
		return passEncoded.equals(empleadoRepository.findEmpleadoByUsuarioUsername(username).get().getUsuario().getPassword());

	}
}
