package org.springframework.samples.petclinic.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Cita;
import org.springframework.samples.petclinic.model.Empleado;
import org.springframework.samples.petclinic.model.Proveedor;
import org.springframework.samples.petclinic.model.Recambio;
import org.springframework.samples.petclinic.model.Reparacion;
import org.springframework.samples.petclinic.model.Solicitud;
import org.springframework.samples.petclinic.model.Taller;
import org.springframework.samples.petclinic.model.TipoCita;
import org.springframework.samples.petclinic.model.TipoVehiculo;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.exceptions.CitaSinPresentarseException;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedProveedorNifException;
import org.springframework.samples.petclinic.service.exceptions.EmpleadoYCitaDistintoTallerException;
import org.springframework.samples.petclinic.service.exceptions.FechasReparacionException;
import org.springframework.samples.petclinic.service.exceptions.Max3ReparacionesSimultaneasPorEmpleadoException;
import org.springframework.samples.petclinic.service.exceptions.NotAllowedException;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
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
	
	@Autowired
	protected ReparacionService reparacionService;

	@Autowired
	protected TipoCitaService tipoCitaService;
	
	@Autowired
	protected VehiculoService vehiculoService;
	
	@Autowired
	protected CitaService citaService;
	
	private Solicitud s1;
	
	private Solicitud s2;
	
	
	@BeforeEach
	void setup() throws DataAccessException, DuplicatedProveedorNifException, FechasReparacionException, Max3ReparacionesSimultaneasPorEmpleadoException, EmpleadoYCitaDistintoTallerException, NotAllowedException, CitaSinPresentarseException {
		Reparacion r = new Reparacion();
		r.setDescripcion("Una descripcion");
		r.setFechaEntrega(LocalDate.now().plusDays(7));
		r.setTiempoEstimado(LocalDate.now().plusDays(8));
		r.setFechaFinalizacion(LocalDate.now().plusDays(9));
		r.setFechaRecogida(LocalDate.now().plusDays(10));
	
		Cita c = new Cita();
		TipoCita t = tipoCitaService.findById(1).get();
		List<TipoCita> tipos = new ArrayList<>();
		tipos.add(t);
		c.setFecha(LocalDate.now().plusDays(2));
		c.setHora(18);
		c.setTiposCita(tipos);
		c.setVehiculo(vehiculoService.findVehiculoByMatricula("1234ABC").get());
		
		Taller taller = new Taller();
		taller.setCorreo("test@test.com");
		taller.setName("test");
		taller.setTelefono("123456789");
		taller.setUbicacion("calle test");
		
		tallerService.saveTaller(taller);
		
		c.setTaller(taller);
		
		citaService.saveCita(c, "admin");
		
		r.setCita(citaService.findCitaByFechaAndHora(LocalDate.now().plusDays(2), 18));
		
		Empleado e1 = new Empleado();
		List<Empleado> empleados = new ArrayList<>();
		User userP = new User();
		userP.setUsername("nombreusuario1");
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
		e1.setNum_seg_social("234567890145");
		e1.setEmail("prueba@prueba.com");
		e1.setTelefono("777777777");
		
		empleados.add(e1);
		e1.setTaller(taller);
		empleadoService.saveEmpleado(e1);

		r.setEmpleados(empleados);
		
		reparacionService.saveReparacion(r);
		
		
		Proveedor p = new Proveedor();
		p.setName("Norauto");
		p.setNif("98765432A");
		p.setTelefono("665112233");
		p.setEmail("norauto@gmail.com");
		proveedorService.saveProveedor(p);
		
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
		s1.setReparacion(r);
		
		this.s1 = s1;
		
		Solicitud s2 = new Solicitud();
		s2.setRecambio(r1);
		s2.setEmpleado(e1);
		s2.setCantidad(5);
		s2.setTerminada(false);
		s2.setReparacion(r);
		
		this.s2 = s2;
		
		solicitudService.saveSolicitud(s1);
		solicitudService.saveSolicitud(s2);

		
	}
	
	@Test
	void shouldInsertSolicitud() {
		
		assertTrue(s1.getCantidad().equals(10));
		assertFalse(s2.getEmpleado().getNombre().equals("Juan"));
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
