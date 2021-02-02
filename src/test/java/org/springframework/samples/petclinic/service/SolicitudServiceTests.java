package org.springframework.samples.petclinic.service;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Empleado;
import org.springframework.samples.petclinic.model.Proveedor;
import org.springframework.samples.petclinic.model.Recambio;
import org.springframework.samples.petclinic.model.Solicitud;
import org.springframework.samples.petclinic.model.Taller;
import org.springframework.samples.petclinic.model.TipoVehiculo;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedProveedorNifException;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SolicitudServiceTests {
	
	@Autowired
	protected SolicitudService solicitudService;
	
	@Autowired
	protected EmpleadoService empleadoService;
	
	@Autowired
	protected RecambioService recambioService;
	
	
	@Autowired
	protected TallerService tallerService;
	
	@Autowired
	protected TipoVehiculoService tipoVehiculoService;
	
	@Autowired
	protected ProveedorService proveedorService;
	
	
	@BeforeAll
	void setup() throws DataAccessException, DuplicatedProveedorNifException {
		Proveedor p = new Proveedor();
		p.setName("Norauto");
		p.setNif("98765432A");
		p.setTelefono("665112233");
		p.setEmail("norauto@gmail.com");
		proveedorService.saveProveedor(p);
		
		Taller taller = new Taller();
		taller.setCorreo("test@test.com");
		taller.setName("test");
		taller.setTelefono("123456789");
		taller.setUbicacion("calle test");
		
		tallerService.saveTaller(taller);
		
		Empleado e1 = new Empleado();
		User userP = new User();
		userP.setUsername("nombreusuario");
		userP.setPassword("passdeprueba");
		userP.setEnabled(true);
		e1.setNombre("Pepito");
		e1.setApellidos("Grillo");
		e1.setDni("89898988A");
		e1.setFechaNacimiento(LocalDate.now().minusYears(20));
		e1.setFecha_ini_contrato(LocalDate.now().minusDays(10));
		e1.setFecha_fin_contrato(LocalDate.now().plusYears(1));
		e1.setSueldo(1000);
		e1.setUsuario(userP);
		e1.setNum_seg_social("244567890145");
		e1.setEmail("prueba@prueba.com");
		e1.setTelefono("777777777");
		

		e1.setTaller(taller);
		
		empleadoService.saveEmpleado(e1);
		
		Recambio r1 = new Recambio();
		r1.setName("Recambio prueba 1");
		r1.setCantidadActual(5);
		TipoVehiculo tipo = tipoVehiculoService.findByTipo("COCHE").get();
		r1.setTipoVehiculo(tipo);
		r1.setProveedor(p);
		
		
		recambioService.saveRecambio(r1);
			
		
		Solicitud s1 = new Solicitud();
		s1.setRecambio(r1);
		s1.setEmpleado(e1);
		s1.setCantidad(10);
		s1.setTerminada(true);
		
		Solicitud s2 = new Solicitud();
		s2.setRecambio(r1);
		s2.setEmpleado(e1);
		s2.setCantidad(5);
		s2.setTerminada(false);
		
		solicitudService.saveSolicitud(s1);
		solicitudService.saveSolicitud(s2);

		
	}
	
	
	
	@Test
	void findSolicitudesTerminadas() {
		List<Solicitud> solicitudesTerminadas = solicitudService.findSolicitudesTerminadas();
		for(Solicitud s: solicitudesTerminadas) {
			assertEquals(true, s.getTerminada());
		}
	
	}
	
	@Test
	void findSolicitudesNoTerminadas() {
		List<Solicitud> solicitudesNoTerminadas = solicitudService.findSolicitudesNoTerminadas();
		for(Solicitud s: solicitudesNoTerminadas) {
			assertEquals(false, s.getTerminada());
		}
	}
	
	

}
