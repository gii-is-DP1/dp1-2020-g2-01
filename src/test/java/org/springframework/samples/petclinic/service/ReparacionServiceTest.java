package org.springframework.samples.petclinic.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Cita;
import org.springframework.samples.petclinic.model.Empleado;
import org.springframework.samples.petclinic.model.Factura;
import org.springframework.samples.petclinic.model.HoraTrabajada;
import org.springframework.samples.petclinic.model.LineaFactura;
import org.springframework.samples.petclinic.model.Recambio;
import org.springframework.samples.petclinic.model.Reparacion;
import org.springframework.samples.petclinic.model.Taller;
import org.springframework.samples.petclinic.model.TipoCita;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.repository.ReparacionRepository;
import org.springframework.samples.petclinic.service.exceptions.CitaSinPresentarseException;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedMatriculaException;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedUsernameException;
import org.springframework.samples.petclinic.service.exceptions.EmpleadoYCitaDistintoTallerException;
import org.springframework.samples.petclinic.service.exceptions.FechasFuturaException;
import org.springframework.samples.petclinic.service.exceptions.FechasReparacionException;
import org.springframework.samples.petclinic.service.exceptions.InvalidPasswordException;
import org.springframework.samples.petclinic.service.exceptions.Max3ReparacionesSimultaneasPorEmpleadoException;
import org.springframework.samples.petclinic.service.exceptions.NoMayorEdadEmpleadoException;
import org.springframework.samples.petclinic.service.exceptions.NoRecogidaSinPagoException;
import org.springframework.samples.petclinic.service.exceptions.NotAllowedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(MockitoExtension.class)
@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class ReparacionServiceTest {
	

	@Autowired
	protected ReparacionService reparacionService;

	@Autowired
	protected CitaService citaService;
	
	@Autowired
	protected TipoCitaService tipoCitaService;
	
	@Autowired
	protected VehiculoService vehiculoService;
	
	@Autowired
	protected TipoVehiculoService tipoVehiculoService;
	
	@Autowired
	protected EmpleadoService empleadoService;
	
	@Autowired
	protected TallerService tallerService;
	
	@Autowired
	protected EntityManager em;

	@Autowired
	protected ClienteService clienteService;
	
	@Autowired
	protected UserService userService;
	
	@Autowired
	protected HorasTrabajadasService horasTrabajadasService;
	
	@Autowired
	protected ProveedorService proveedorService;
	
	@Autowired
	protected RecambioService recambioService;
	
	@Autowired
	protected LineaFacturaService LFService;
	
	@Autowired
	protected FacturaService facturaService;
	
	@Autowired
	protected ReparacionRepository reparacionRepository;
	
	@Mock
	protected SendEmailService mock;
	
	
	public Reparacion r;
	
	public List<HoraTrabajada> horas;
	
	public Taller taller;
	
	public Empleado e1;
	
	@BeforeEach
	void insertReparacion() throws DataAccessException, FechasReparacionException, Max3ReparacionesSimultaneasPorEmpleadoException, EmpleadoYCitaDistintoTallerException, NotAllowedException, CitaSinPresentarseException, NoMayorEdadEmpleadoException, InvalidPasswordException, FechasFuturaException, DuplicatedUsernameException {
		Reparacion r = new Reparacion();
		r.setDescripcion("Una descripcion");
		r.setFechaEntrega(LocalDate.now());
		r.setTiempoEstimado(LocalDate.now());
		r.setFechaFinalizacion(LocalDate.now());
	
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
		this.taller=taller;
		
		c.setTaller(taller);
		
		citaService.saveCita(c, "jesfunrud");
		
		r.setCita(citaService.findCitaByFechaAndHora(LocalDate.now().plusDays(2), 18));
		
		Empleado e1 = new Empleado();
		User userP = new User();
		userP.setUsername("nombreusuario1");
		userP.setPassword("passdeprueba1");
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
		
		e1.setTaller(taller);
		empleadoService.saveEmpleado(e1);
		
		this.e1=e1;

		HoraTrabajada hora = new HoraTrabajada();
		hora.setEmpleado(e1);
		hora.setHorasTrabajadas(10);
		hora.setPrecioHora(10.5);
		hora.setTrabajoRealizado("Cambio de rueda");
		
		List<HoraTrabajada> horas = new ArrayList<>();
		horas.add(hora);
		
		horasTrabajadasService.save(hora);
		
		this.horas=horas;
		
		r.setHorasTrabajadas(horas);
		
		reparacionService.saveReparacion(r);
		
		this.r=r;
	}
	
	@Test
	void shouldInsertReparacion() {
		assertEquals(r, reparacionService.findReparacionById(r.getId()).get());
	}
	
	
	@Test
	@Transactional
	void shouldNotInsertReparacionInvalida(){
		
		Reparacion r1 = new Reparacion();

		r1.setDescripcion(""); //Descripción vacía

		r1.setFechaEntrega(LocalDate.now().plusDays(7));
		r1.setTiempoEstimado(LocalDate.now().plusDays(8));
		r1.setFechaFinalizacion(LocalDate.now().plusDays(9));
		r1.setFechaRecogida(LocalDate.now().plusDays(10));
		
		r1.setCita(citaService.findCitaByFechaAndHora(LocalDate.now().plusDays(2), 18));

		r1.setHorasTrabajadas(horas);

		assertThrows(ConstraintViolationException.class, () -> this.reparacionService.saveReparacion(r1));
		
	}
	
	@Test
	void shouldNotInsertReparacionConFechasIncorrectas() {
		Reparacion r1 = new Reparacion();
		r1.setDescripcion("Una descripcion hola que tal");
		r1.setFechaEntrega(LocalDate.now().plusDays(7));
		r1.setTiempoEstimado(LocalDate.now().plusDays(8));
		r1.setFechaFinalizacion(LocalDate.now().plusDays(11));
		r1.setFechaRecogida(LocalDate.now().plusDays(10));
	
		r1.setCita(citaService.findCitaByFechaAndHora(LocalDate.now().plusDays(2), 18));
				
		r1.setHorasTrabajadas(horas);
		
		assertThrows(FechasReparacionException.class, () -> this.reparacionService.saveReparacion(r1));

	}
	
	
	@Test
	void shouldNotInsertReparacionConEmpleadoCon3ReparacionesSimultaneas() throws DataAccessException, EmpleadoYCitaDistintoTallerException, NotAllowedException, CitaSinPresentarseException, FechasReparacionException, Max3ReparacionesSimultaneasPorEmpleadoException, FechasFuturaException{
		//Setup inicial de reparacion (faltan cosas)
		Reparacion r4 = new Reparacion();
		r4.setDescripcion("Descripción de prueba"); 
		r4.setFechaEntrega(LocalDate.now().plusDays(7));
		r4.setTiempoEstimado(LocalDate.now().plusDays(8));
		r4.setFechaRecogida(LocalDate.now().plusDays(10));
	
		//Setup de reparacion y cita
		Cita c4 = new Cita();
		TipoCita t4 = tipoCitaService.findById(1).get();
		List<TipoCita> tipos1 = new ArrayList<>();
		tipos1.add(t4);
		c4.setFecha(LocalDate.now().plusDays(22));
		c4.setHora(18);
		c4.setTiposCita(tipos1);
		c4.setVehiculo(vehiculoService.findVehiculoByMatricula("1234ABC").get());
				
		c4.setTaller(taller);
		
		citaService.saveCita(c4, "jesfunrud1");
		
		//Asigna una cita a la reparacion
		r4.setCita(citaService.findCitaByFechaAndHora(LocalDate.now().plusDays(2), 18));
		
		//Crear 3 reparaciones y asociarlas al empleado. Las reparaciones tienen mismo taller y vehiculo pero citas distintas
		
		//Reparacion 1
		Reparacion r1 = new Reparacion();
		r1.setDescripcion("Descripción de prueba"); 
		r1.setFechaEntrega(LocalDate.now().plusDays(7));
		r1.setTiempoEstimado(LocalDate.now().plusDays(8));
		r1.setFechaRecogida(LocalDate.now().plusDays(10));
	
		//CUIDADO CON PONER MISMA FECHA Y HORA QUE EL RESTO DE CITAS
		Cita c1 = new Cita();
		c1.setFecha(LocalDate.now().plusDays(2));
		c1.setHora(10);
		c1.setTiposCita(tipos1);
		c1.setVehiculo(vehiculoService.findVehiculoByMatricula("1234ABC").get());
		c1.setTaller(taller);
		
		citaService.saveCita(c1, "jesfunrud1");
		
		r1.setCita(citaService.findCitaByFechaAndHora(LocalDate.now().plusDays(2), 10));
		
		HoraTrabajada hora = new HoraTrabajada();
		hora.setEmpleado(e1);
		hora.setHorasTrabajadas(10);
		hora.setPrecioHora(10.5);
		hora.setTrabajoRealizado("Cambio de rueda");
		
		List<HoraTrabajada> horas = new ArrayList<>();
		horas.add(hora);
		
		horasTrabajadasService.save(hora);
		
		r1.setHorasTrabajadas(horas);
		
		reparacionService.saveReparacion(r1);
		
		//Reparacion 2
		Reparacion r2 = new Reparacion();
		r2.setDescripcion("Descripción de prueba"); 
		r2.setFechaEntrega(LocalDate.now().plusDays(7));
		r2.setTiempoEstimado(LocalDate.now().plusDays(8));
		r2.setFechaRecogida(LocalDate.now().plusDays(10));
	
	
		Cita c2 = new Cita();
		c2.setFecha(LocalDate.now().plusDays(2));
		c2.setHora(11);
		c2.setTiposCita(tipos1);
		c2.setVehiculo(vehiculoService.findVehiculoByMatricula("1234ABC").get());
		c2.setTaller(taller);
		
		citaService.saveCita(c2, "jesfunrud1");
		
		r2.setCita(citaService.findCitaByFechaAndHora(LocalDate.now().plusDays(2), 11));
		
		HoraTrabajada hora1 = new HoraTrabajada();
		hora1.setEmpleado(e1);
		hora1.setHorasTrabajadas(10);
		hora1.setPrecioHora(10.5);
		hora1.setTrabajoRealizado("Cambio de rueda");
		
		List<HoraTrabajada> horas1 = new ArrayList<>();
		horas1.add(hora1);
		
		horasTrabajadasService.save(hora1);
		
		r2.setHorasTrabajadas(horas1);
		
		reparacionService.saveReparacion(r2);
		
		
		//Reparacion 3
		Reparacion r3 = new Reparacion();
		r3.setDescripcion("Descripción de prueba"); 
		r3.setFechaEntrega(LocalDate.now().plusDays(7));
		r3.setTiempoEstimado(LocalDate.now().plusDays(8));
		r3.setFechaRecogida(LocalDate.now().plusDays(10));
	
	
		Cita c3 = new Cita();
		c3.setFecha(LocalDate.now().plusDays(2));
		c3.setHora(12);
		c3.setTiposCita(tipos1);
		c3.setVehiculo(vehiculoService.findVehiculoByMatricula("1234ABC").get());
		c3.setTaller(taller);
		
		citaService.saveCita(c3, "jesfunrud1");
		
		r3.setCita(citaService.findCitaByFechaAndHora(LocalDate.now().plusDays(2), 12));
		
		HoraTrabajada hora2 = new HoraTrabajada();
		hora2.setEmpleado(e1);
		hora2.setHorasTrabajadas(10);
		hora2.setPrecioHora(10.5);
		hora2.setTrabajoRealizado("Cambio de rueda");
		
		List<HoraTrabajada> horas2 = new ArrayList<>();
		horas2.add(hora2);
		
		horasTrabajadasService.save(hora2);
		
		r3.setHorasTrabajadas(horas2);
		
		reparacionService.saveReparacion(r3);
		
		r4.setHorasTrabajadas(horas);
		assertThrows(Max3ReparacionesSimultaneasPorEmpleadoException.class, () -> this.reparacionService.saveReparacion(r4));
	}	
	
	@Test
	void shouldUpdateReparacion() throws DataAccessException, FechasReparacionException, Max3ReparacionesSimultaneasPorEmpleadoException {
		Reparacion r1 = reparacionService.findReparacionById(r.getId()).get();
		r1.setDescripcion("Descripcion cambiada");
		
		reparacionService.saveReparacion(r1);
		
		assertTrue(reparacionService.findReparacionById(r1.getId()).isPresent());

	}
	
	@Test
	void shouldNotUpdateReparacionInvalida() throws DataAccessException, FechasReparacionException, Max3ReparacionesSimultaneasPorEmpleadoException, DuplicatedMatriculaException, EmpleadoYCitaDistintoTallerException, NotAllowedException, CitaSinPresentarseException {
		Reparacion r1 = reparacionService.findReparacionById(r.getId()).get();
		r1.setDescripcion("");
		
		assertThrows(ConstraintViolationException.class, () ->{
			reparacionService.saveReparacion(r1);
			em.flush();
		});

	}
	
	
	@Test
	void shouldDeleteReparacion() {
		assertTrue(reparacionService.findReparacionById(r.getId()).isPresent());
		
		reparacionService.delete(r);
		assertFalse(reparacionService.findReparacionById(r.getId()).isPresent());
		
	}
		
	@Test
	@Transactional
	void shouldFinalizar() throws DataAccessException, FechasReparacionException, Max3ReparacionesSimultaneasPorEmpleadoException {
		reparacionService = new ReparacionService(reparacionRepository, mock, horasTrabajadasService); 
		reparacionService.finalizar(r);
		verify(mock, times(1)).sendEmail(any(String.class), any(String.class), any(String.class));
		assertEquals(r.getFechaFinalizacion(), LocalDate.now());
	}
	
	//RN-3
	@Test
	void shouldRecoger() throws DataAccessException, FechasReparacionException, Max3ReparacionesSimultaneasPorEmpleadoException, NoMayorEdadEmpleadoException, InvalidPasswordException, EmpleadoYCitaDistintoTallerException, NotAllowedException, CitaSinPresentarseException, NoRecogidaSinPagoException {	
		Factura f = new Factura();
		f.setFechaPago(LocalDate.now());
		List<LineaFactura> lineas = new ArrayList<>();
		
		LineaFactura lf = new LineaFactura();
		lf.setDescuento(0);
		lf.setDescripcion("Descripcion de prueba de una factura");
		
		///////
		
		Recambio rec = new Recambio();
		rec.setName("Neumáticos Pirelli");
		rec.setCantidadActual(100);
		rec.setTipoVehiculo(tipoVehiculoService.findByTipo("COCHE").get());

		
		recambioService.saveRecambio(rec);
		
		///////
		
		lf.setReparacion(r);
		lf.setPrecioBase(20.03);
		lf.setRecambio(rec);
		lf.setCantidad(4);
		LFService.saveLineaFactura(lf);
		
		lineas.add(lf);
		f.setLineaFactura(lineas);
		facturaService.saveFactura(f);
		
		lf.setFactura(f);
		LFService.saveLineaFactura(lf);
		
		r.setLineaFactura(lineas);
		reparacionService.saveReparacion(r);
		
		
		reparacionService.recoger(r);
		assertEquals(LocalDate.now(), reparacionService.findReparacionById(r.getId()).get().getFechaRecogida());
	}
	
	
	
	
	//RN-3
	@Test
	void shouldNotRecoger() throws DataAccessException, FechasReparacionException, Max3ReparacionesSimultaneasPorEmpleadoException, NoMayorEdadEmpleadoException, InvalidPasswordException, EmpleadoYCitaDistintoTallerException, NotAllowedException, CitaSinPresentarseException {
		Factura f = new Factura();
		List<LineaFactura> lineas = new ArrayList<>();
		
		LineaFactura lf = new LineaFactura();
		lf.setDescuento(0);
		lf.setDescripcion("Descripcion de prueba de una factura");
		
		///////
		
		Recambio rec = new Recambio();
		rec.setName("Neumáticos Pirelli");
		rec.setCantidadActual(100);
		rec.setTipoVehiculo(tipoVehiculoService.findByTipo("COCHE").get());

		
		recambioService.saveRecambio(rec);
		
		///////
		
		lf.setReparacion(r);
		lf.setPrecioBase(20.03);
		lf.setRecambio(rec);
		lf.setCantidad(4);
		LFService.saveLineaFactura(lf);
		
		lineas.add(lf);
		f.setLineaFactura(lineas);
		facturaService.saveFactura(f);
		
		lf.setFactura(f);
		LFService.saveLineaFactura(lf);
		
		r.setLineaFactura(lineas);
		reparacionService.saveReparacion(r);
		
		
		assertThrows(NoRecogidaSinPagoException.class, () -> reparacionService.recoger(r));
	}
	
	
	
}

