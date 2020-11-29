package org.springframework.samples.petclinic.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Empleado;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class EmpleadoServiceTest {

	@Autowired
	protected EmpleadoService empleadoService;
	
	@Test
	void shouldInsertEmpleado() {
		Empleado e = new Empleado();
		
		e.setApellidos("Ramirez Perez");
		e.setCorreo("laurita@gmail.com");
		e.setDni("36283951R");
		e.setFecha_fin_contrato(LocalDate.now().plusYears(10));
		e.setFecha_ini_contrato(LocalDate.now().minusYears(2));
		e.setFechaNacimiento(LocalDate.now().minusYears(40));
		e.setNombre("Laura");
		e.setNum_seg_social("82938103-23183-21");
		e.setSueldo(1098L);
		e.setTelefono("678456736");
		
		empleadoService.saveEmpleado(e);
		assertEquals(e, empleadoService.findEmpleadoDni("36283951R"));
	}
	
	@Test
	void shouldNotInsertEmpleadoInvalido() {
		Empleado e = new Empleado();
		
		e.setApellidos("Ramirez Perez");
		e.setCorreo("laurita@gmail.com");
		e.setDni("");
		e.setFecha_fin_contrato(LocalDate.now().plusYears(10));
		e.setFecha_ini_contrato(LocalDate.now().minusYears(2));
		e.setFechaNacimiento(LocalDate.now().minusYears(40));
		e.setNombre("Laura");
		e.setNum_seg_social("82938103-23183-21");
		e.setSueldo(1098L);
		e.setTelefono("678456736");
		
		assertThrows(ConstraintViolationException.class, () -> empleadoService.saveEmpleado(e));
	}
	
	@Test
	void shouldUpdateEmpleado() {
		Empleado e = new Empleado();
		
		e.setApellidos("Ramirez Perez");
		e.setCorreo("laurita@gmail.com");
		e.setDni("36283951R");
		e.setFecha_fin_contrato(LocalDate.now().plusYears(10));
		e.setFecha_ini_contrato(LocalDate.now().minusYears(2));
		e.setFechaNacimiento(LocalDate.now().minusYears(40));
		e.setNombre("Laura");
		e.setNum_seg_social("82938103-23183-21");
		e.setSueldo(1098L);
		e.setTelefono("678456736");
		
		empleadoService.saveEmpleado(e);
		
		Empleado e1 = empleadoService.findEmpleadoDni("36283951R");
		e1.setSueldo(1180L);
		
		empleadoService.saveEmpleado(e1);
		
		assertEquals(e, empleadoService.findEmpleadoDni("36283951R"));
	}
	
	@Test
	void shouldNotUpdateEmpleadoInvalido() {
		Empleado e = new Empleado();
		
		e.setApellidos("Ramirez Perez");
		e.setCorreo("laurita@gmail.com");
		e.setDni("36283951R");
		e.setFecha_fin_contrato(LocalDate.now().plusYears(10));
		e.setFecha_ini_contrato(LocalDate.now().minusYears(2));
		e.setFechaNacimiento(LocalDate.now().minusYears(40));
		e.setNombre("Laura");
		e.setNum_seg_social("82938103-23183-21");
		e.setSueldo(1098L);
		e.setTelefono("678456736");
		
		empleadoService.saveEmpleado(e);
		
		Empleado e1 = empleadoService.findEmpleadoDni("36283951R");
		e1.setDni("");
		empleadoService.saveEmpleado(e1);
		
		assertFalse(this.empleadoService.findById(e1.getId()).isPresent());
		
	}
	
	@Test 
	void shoulDeleteEmplead() {
		Empleado e = new Empleado();
		
		e.setApellidos("Ramirez Perez");
		e.setCorreo("laurita@gmail.com");
		e.setDni("36283951R");
		e.setFecha_fin_contrato(LocalDate.now().plusYears(10));
		e.setFecha_ini_contrato(LocalDate.now().minusYears(2));
		e.setFechaNacimiento(LocalDate.now().minusYears(40));
		e.setNombre("Laura");
		e.setNum_seg_social("82938103-23183-21");
		e.setSueldo(1098L);
		e.setTelefono("678456736");
		
		empleadoService.saveEmpleado(e);
		assertEquals(e, empleadoService.findEmpleadoDni("36283951R"));
		
		empleadoService.delete(e);
		
		assertNull(empleadoService.findEmpleadoDni("36283951R"));

	}
}
