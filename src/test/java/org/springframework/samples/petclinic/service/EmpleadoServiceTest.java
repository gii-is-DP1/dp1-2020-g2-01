package org.springframework.samples.petclinic.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import javax.persistence.EntityManager;
import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Empleado;
import org.springframework.samples.petclinic.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class EmpleadoServiceTest {

	@Autowired
	protected EmpleadoService empleadoService;
	
	@Autowired
	protected EntityManager em;
  
	@Test
	void shouldInsertEmpleado() {
		
		User userP = new User();
		userP.setUsername("nombreusuario");
		userP.setPassword("passdeprueba");
		userP.setEnabled(true);
		Empleado e = new Empleado();
		
		e.setApellidos("Ramirez Perez");
		e.setEmail("laurita@gmail.com");
		e.setDni("36283951R");
		e.setFecha_fin_contrato(LocalDate.now().plusYears(10));
		e.setFecha_ini_contrato(LocalDate.now().minusYears(2));
		e.setFechaNacimiento(LocalDate.now().minusYears(40));
		e.setNombre("Laura");
		e.setNum_seg_social("82938103-23183-21");
		e.setSueldo(1098L);
		e.setTelefono("678456736");
		e.setUsuario(userP);
		
		User u = new User();
		u.setUsername("Laurita");
		u.setPassword("laura123");
		
		e.setUsuario(u);
		
		empleadoService.saveEmpleado(e);
		assertEquals(e, empleadoService.findEmpleadoDni("36283951R").get());
	}
	
	@Test
	@Transactional
	void shouldNotInsertEmpleadoInvalido() {
		
		User userP = new User();
		userP.setUsername("nombreusuario");
		userP.setPassword("passdeprueba");
		userP.setEnabled(true);
		Empleado e = new Empleado();
		
		e.setApellidos("Ramirez Perez");
		e.setEmail("laurita@gmail.com");
		e.setDni("");
		e.setFecha_fin_contrato(LocalDate.now().plusYears(10));
		e.setFecha_ini_contrato(LocalDate.now().minusYears(2));
		e.setFechaNacimiento(LocalDate.now().minusYears(40));
		e.setNombre("Laura");
		e.setNum_seg_social("82938103-23183-21");
		e.setSueldo(1098L);
		e.setTelefono("678456736");
		e.setUsuario(userP);
		
		User u = new User();
		u.setUsername("Laurita");
		u.setPassword("laura123");
		
		e.setUsuario(u);
		
		assertThrows(ConstraintViolationException.class, () -> empleadoService.saveEmpleado(e));
	}
	
	@Test
	@Transactional
	void shouldUpdateEmpleado() {
		User userP = new User();
		userP.setUsername("nombreusuario");
		userP.setPassword("passdeprueba");
		userP.setEnabled(true);
		Empleado e = new Empleado();
		
		e.setApellidos("Ramirez Perez");
		e.setEmail("laurita@gmail.com");
		e.setDni("36283951R");
		e.setFecha_fin_contrato(LocalDate.now().plusYears(10));
		e.setFecha_ini_contrato(LocalDate.now().minusYears(2));
		e.setFechaNacimiento(LocalDate.now().minusYears(40));
		e.setNombre("Laura");
		e.setNum_seg_social("82938103-23183-21");
		e.setSueldo(1098L);
		e.setTelefono("678456736");
		e.setUsuario(userP);
		
		User u = new User();
		u.setUsername("Laurita");
		u.setPassword("laura123");
		
		e.setUsuario(u);
		
		empleadoService.saveEmpleado(e);
		
		Empleado e1 = empleadoService.findEmpleadoDni("36283951R").get();
		e1.setDni("36283951M");
		
		empleadoService.saveEmpleado(e1);
		
		assertTrue(empleadoService.findEmpleadoDni("36283951M").isPresent());
		assertFalse(empleadoService.findEmpleadoDni("36283951R").isPresent());
	}
	
	@Test
	@Transactional
	void shouldNotUpdateEmpleadoInvalido() {
		User userP = new User();
		userP.setUsername("nombreusuario");
		userP.setPassword("passdeprueba");
		userP.setEnabled(true);
		
		Empleado e = new Empleado();
		
		e.setApellidos("Ramirez Perez");
		e.setEmail("laurita@gmail.com");
		e.setDni("36283951R");
		e.setFecha_fin_contrato(LocalDate.now().plusYears(10));
		e.setFecha_ini_contrato(LocalDate.now().minusYears(2));
		e.setFechaNacimiento(LocalDate.now().minusYears(40));
		e.setNombre("Laura");
		e.setNum_seg_social("82938103-23183-21");
		e.setSueldo(1098L);
		e.setTelefono("678456736");
		e.setUsuario(userP);
		
		User u = new User();
		u.setUsername("Laurita");
		u.setPassword("laura123");
		
		e.setUsuario(u);
		
		empleadoService.saveEmpleado(e);
		
		Empleado e1 = empleadoService.findEmpleadoDni("36283951R").get();
		e1.setDni("");
		assertThrows(ConstraintViolationException.class, () ->{
			empleadoService.saveEmpleado(e1);
			em.flush();
		});
		
	}
	
	@Test 
	@Transactional
	void shoulDeleteEmplead() {
		User userP = new User();
		userP.setUsername("nombreusuario");
		userP.setPassword("passdeprueba");
		userP.setEnabled(true);
		Empleado e = new Empleado();
		
		e.setApellidos("Ramirez Perez");
		e.setEmail("laurita@gmail.com");
		e.setDni("36283951R");
		e.setFecha_fin_contrato(LocalDate.now().plusYears(10));
		e.setFecha_ini_contrato(LocalDate.now().minusYears(2));
		e.setFechaNacimiento(LocalDate.now().minusYears(40));
		e.setNombre("Laura");
		e.setNum_seg_social("82938103-23183-21");
		e.setSueldo(1098L);
		e.setTelefono("678456736");
		e.setUsuario(userP);
		
		User u = new User();
		u.setUsername("Laurita");
		u.setPassword("laura123");
		
		e.setUsuario(u);
		
		empleadoService.saveEmpleado(e);
		assertEquals(e, empleadoService.findEmpleadoDni("36283951R").get());
		
		empleadoService.delete(e);
		
		assertFalse(empleadoService.findEmpleadoDni("36283951R").isPresent());

	}
}
