package org.springframework.samples.petclinic.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Cita;
import org.springframework.samples.petclinic.model.TipoCita;
import org.springframework.samples.petclinic.model.TipoVehiculo;
import org.springframework.samples.petclinic.model.Vehiculo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class CitaServiceTest {
	
	@Autowired
	protected VehiculoService vehiculoService;
	
	@Autowired
	protected CitaService citaService;
	
	@Autowired
	protected TipoCitaService tipoCitaService;
	
	@Autowired
	protected EntityManager em;
	
	@Autowired
	protected TipoVehiculoService tipoVehiculoService;

	@Test
	@Transactional
	void shouldInsertCita() {
		Cita c = new Cita();
		TipoCita tipo = tipoCitaService.findById(1).get();
		TipoVehiculo tipoveh = tipoVehiculoService.findById(1).get();
		
		c.setFecha(LocalDate.now().plusDays(1));
		c.setHora(10);
		List<TipoCita> tipos = new ArrayList<TipoCita>();
		tipos.add(tipo);
		c.setTiposCita(tipos);
		
		Vehiculo v = new Vehiculo();
		
		v.setMatricula("1111AAA");
		v.setModelo("Seat Ibiza");
		v.setNumBastidor("1");
		v.setTipoVehiculo(tipoveh);
		vehiculoService.saveVehiculo(v);
		
		c.setVehiculo(vehiculoService.findVehiculoByMatricula("1111AAA"));
		
		citaService.saveCita(c);
		
		assertEquals(c, citaService.findCitaByFechaAndHora(LocalDate.now().plusDays(1), 10));
	}
	
	@Test
	@Transactional
	void shouldInsertCitaInvalida() {
		Cita c = new Cita();
		TipoCita tipo = tipoCitaService.findById(1).get();
		TipoVehiculo tipoveh = tipoVehiculoService.findById(1).get();
	
		
		c.setFecha(LocalDate.now());
		c.setHora(10);
		List<TipoCita> tipos = new ArrayList<TipoCita>();
		tipos.add(tipo);
		c.setTiposCita(tipos);
		
		Vehiculo v = new Vehiculo();
		
		v.setMatricula("1111AAA");
		v.setModelo("Seat Ibiza");
		v.setNumBastidor("1");
		v.setTipoVehiculo(tipoveh);
		vehiculoService.saveVehiculo(v);
		
		c.setVehiculo(vehiculoService.findVehiculoByMatricula("1111AAA"));
	
		
		assertThrows(ConstraintViolationException.class, () -> this.citaService.saveCita(c));
	}
	
	
	@Test
	@Transactional
	void shouldUpdateCita() {
		Cita c = new Cita();
		TipoCita tipo = tipoCitaService.findById(1).get();
		TipoVehiculo tipoveh = tipoVehiculoService.findById(1).get();
		
		c.setFecha(LocalDate.now().plusDays(1));
		c.setHora(10);
		List<TipoCita> tipos = new ArrayList<TipoCita>();
		tipos.add(tipo);
		c.setTiposCita(tipos);
		
		Vehiculo v = new Vehiculo();
		
		v.setMatricula("1111AAA");
		v.setModelo("Seat Ibiza");
		v.setNumBastidor("1");
		v.setTipoVehiculo(tipoveh);
		vehiculoService.saveVehiculo(v);
		
		c.setVehiculo(vehiculoService.findVehiculoByMatricula("1111AAA"));
		
		citaService.saveCita(c);
		Cita c1 = citaService.findCitaByFechaAndHora(LocalDate.now().plusDays(1), 10);
		
		c1.setFecha(LocalDate.now().plusDays(3));
		citaService.saveCita(c1);
		assertEquals(c1, citaService.findCitaByFechaAndHora(LocalDate.now().plusDays(3), 10));
	}
	
	@Test
	@Transactional
	void shouldNotUpdateInvalidCita() {
		Cita c = new Cita();
		TipoCita tipo = tipoCitaService.findById(1).get();
		TipoVehiculo tipoveh = tipoVehiculoService.findById(1).get();
		
		c.setFecha(LocalDate.now().plusDays(1));
		c.setHora(10);
		List<TipoCita> tipos = new ArrayList<TipoCita>();
		tipos.add(tipo);
		c.setTiposCita(tipos);
		
		Vehiculo v = new Vehiculo();
		
		v.setMatricula("1111AAA");
		v.setModelo("Seat Ibiza");
		v.setNumBastidor("1");
		v.setTipoVehiculo(tipoveh);
		vehiculoService.saveVehiculo(v);
		
		c.setVehiculo(vehiculoService.findVehiculoByMatricula("1111AAA"));
		citaService.saveCita(c);
		
		Cita c1 = new Cita();
		c1.setId(c.getId());

		c1.setHora(10);
		c.setTiposCita(tipos);
		c1.setVehiculo(vehiculoService.findVehiculoByMatricula("1111AAA"));
		c1.setFecha(LocalDate.now());
		assertThrows(ConstraintViolationException.class, () ->{
			this.citaService.saveCita(c1);
			em.flush();
		});	
	}
	
	@Test
	void shouldDeleteCita() {
		Cita c = new Cita();
		TipoCita tipo = tipoCitaService.findById(1).get();
		TipoVehiculo tipoveh = tipoVehiculoService.findById(1).get();
		
		c.setFecha(LocalDate.now().plusDays(1));
		c.setHora(10);
		List<TipoCita> tipos = new ArrayList<TipoCita>();
		tipos.add(tipo);
		c.setTiposCita(tipos);
		
		Vehiculo v = new Vehiculo();
		
		v.setMatricula("1111AAA");
		v.setModelo("Seat Ibiza");
		v.setNumBastidor("1");
		v.setTipoVehiculo(tipoveh);
		vehiculoService.saveVehiculo(v);
		
		c.setVehiculo(vehiculoService.findVehiculoByMatricula("1111AAA"));
		citaService.saveCita(c);
		
		assertEquals(c, citaService.findCitaByFechaAndHora(LocalDate.now().plusDays(1), 10));
		
		citaService.delete(citaService.findCitaByFechaAndHora(LocalDate.now().plusDays(1), 10));
		
		assertNull(citaService.findCitaByFechaAndHora(LocalDate.now().plusDays(1), 10));
	}

}
