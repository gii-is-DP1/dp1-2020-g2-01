package org.springframework.samples.petclinic.service;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.NoSuchElementException;

import javax.persistence.EntityManager;
import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Proveedor;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedProveedorNifException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class ProveedorServiceTest {
	
	
	@Autowired
	protected ProveedorService proveedorService;
	
	@Autowired
	protected EntityManager em;
	
	
	//Proveedor que se inserta antes de cada test
	public Proveedor p;
	
	@BeforeEach
	void insertProveedor() throws DataAccessException, DuplicatedProveedorNifException {
		Proveedor p = new Proveedor();
		p.setNombre("Norauto");
		p.setNif("98765432A");
		p.setTelefono("665112233");
		p.setEmail("norauto@gmail.com");
		proveedorService.saveProveedor(p);
		this.p = p;
		
	}
	
	
	
	
	@Test
	void shouldFindProveedor() {
		assertEquals(p, proveedorService.findProveedorById(p.getId()).get());
		
	}
	
	@Test
	void shouldNotFindProveedor() {
		proveedorService.delete(p);
		assertThrows(NoSuchElementException.class, () -> proveedorService.findProveedorById(p.getId()).get());
		
	}
	
	
	
	@Test
	@Transactional
	void shouldInsertProveedor() throws DataAccessException, DuplicatedProveedorNifException {
		assertEquals(p, proveedorService.findProveedorById(p.getId()).get());
	}

	
	@Test
	@Transactional
	void shouldNotInsertProveedor() {
		Proveedor p = new Proveedor();
		p.setNombre("");
		p.setNif("98765432Z");
		p.setTelefono("665112233");
		p.setEmail("norauto@gmail.com");
		
		assertThrows(ConstraintViolationException.class, ()->proveedorService.saveProveedor(p));
		
	}
	
	@Test
	@Transactional
	void shouldNotInsertTwoProveedoresWithSameNif() throws DataAccessException, DuplicatedProveedorNifException {
		Proveedor p2 = new Proveedor();
		p2.setNombre("Prueba2");
		p2.setNif("98765432A");
		p2.setTelefono("665112233");
		p2.setEmail("norauto@gmail.com");
		
		assertThrows(DuplicatedProveedorNifException.class, ()->proveedorService.saveProveedor(p2));
	}
	
	
	@Test
	@Transactional
	void shouldUpdateProveedor() throws DataAccessException, DuplicatedProveedorNifException {
		Proveedor p2 = new Proveedor();
		p2.setNombre("Prueba");
		p2.setNif("98765432B");
		p2.setTelefono("665112245");
		p2.setEmail("prueba@gmail.com");
		p2.setId(p.getId());
		
		
		proveedorService.saveProveedor(p2);
		
		assertEquals(p2, proveedorService.findProveedorById(p.getId()).get());
		
		
	}
	
	
	
	@Test
	@Transactional
	void shouldNotUpdateProveedor() throws DataAccessException, DuplicatedProveedorNifException {	
		Proveedor p2 = new Proveedor();
		p2.setNombre("");
		p2.setNif("98765432B");
		p2.setTelefono("665112245");
		p2.setEmail("prueba@gmail.com");
		p2.setId(p.getId());
		
		
		assertThrows(ConstraintViolationException.class, () ->{
			this.proveedorService.saveProveedor(p2);
			em.flush();
		});	
		
	}
	
	
	@Test
	@Transactional
	void shouldDeleteProveedor() throws DataAccessException, DuplicatedProveedorNifException {
		proveedorService.delete(p);
		assertThrows(NoSuchElementException.class, () -> proveedorService.findProveedorById(p.getId()).get());
	}
	
	
}
