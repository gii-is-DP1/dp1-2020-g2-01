package org.springframework.samples.petclinic.service;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Proveedor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class ProveedorServiceTest {
	
	
	@Autowired
	protected ProveedorService proveedorService;
	
	@Test
	@Transactional
	void shouldInsertProveedor() {
		Proveedor p = new Proveedor();
		p.setNombre("Norauto");
		p.setNif("12345678A");
		p.setTelefono(665123456);
		p.setEmail("norauto@gmail.com");
		
		proveedorService.saveProveedor(p);
		
		assertEquals(p, proveedorService.findProveedorById(p.getId()).get());
	}

	@Test
	@Transactional
	void shouldNotInsertProveedor() {
		Proveedor p = new Proveedor();
		p.setNombre("");
		p.setNif("12345678A");
		p.setTelefono(665112233);
		p.setEmail("norauto@gmail.com");
		
		assertThrows(ConstraintViolationException.class, ()->proveedorService.saveProveedor(p));
		
	}
	
	
}
