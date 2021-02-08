package org.springframework.samples.petclinic.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Cita;
import org.springframework.samples.petclinic.model.Empleado;
import org.springframework.samples.petclinic.model.HoraTrabajada;
import org.springframework.samples.petclinic.model.LineaFactura;
import org.springframework.samples.petclinic.model.Recambio;
import org.springframework.samples.petclinic.model.Reparacion;
import org.springframework.samples.petclinic.model.Taller;
import org.springframework.samples.petclinic.model.TipoCita;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.exceptions.CitaSinPresentarseException;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedMatriculaException;
import org.springframework.samples.petclinic.service.exceptions.EmpleadoYCitaDistintoTallerException;
import org.springframework.samples.petclinic.service.exceptions.FechasReparacionException;
import org.springframework.samples.petclinic.service.exceptions.InvalidPasswordException;
import org.springframework.samples.petclinic.service.exceptions.Max3ReparacionesSimultaneasPorEmpleadoException;
import org.springframework.samples.petclinic.service.exceptions.NoMayorEdadEmpleadoException;
import org.springframework.samples.petclinic.service.exceptions.NotAllowedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class LineaFacturaServiceTest {

	@Autowired
	protected LineaFacturaService lfService;
	
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
	protected ClienteService clienteService;
	
	
	@Autowired
	protected RecambioService recambioService;
	
	@Autowired
	protected ProveedorService proveedorService;	

	@Autowired
	protected HorasTrabajadasService horasTrabajadasService;
	
	public LineaFactura lf;
	
	public Recambio rec;
	
	public Reparacion r;
	
	@BeforeEach
	void insertLineaFactura() throws DataAccessException, FechasReparacionException, Max3ReparacionesSimultaneasPorEmpleadoException, EmpleadoYCitaDistintoTallerException, NotAllowedException, CitaSinPresentarseException, NoMayorEdadEmpleadoException, InvalidPasswordException {
		LineaFactura lf = new LineaFactura();
		lf.setDescuento(0);
		lf.setDescripcion("Descripcion de prueba de una factura");
		
	///////
		
		Recambio rec = new Recambio();
		rec.setName("Neumáticos Pirelli");
		rec.setCantidadActual(100);
		rec.setTipoVehiculo(tipoVehiculoService.findByTipo("COCHE").get());


		recambioService.saveRecambio(rec);
		
		this.rec = rec;
		
	///////
		
		Reparacion r = new Reparacion();

		r.setDescripcion("Una descripcion hola que tal");
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
		
		citaService.saveCita(c, "jesfunrud");
		
		r.setCita(citaService.findCitaByFechaAndHora(LocalDate.now().plusDays(2), 18));
		
		Empleado e1 = new Empleado();
		User userP2 = new User();
		userP2.setUsername("nombreusuario1");
		userP2.setPassword("passdeprueba1");
		userP2.setEnabled(true);
		e1.setNombre("Pepito");
		e1.setApellidos("Grillo");
		e1.setDni("89898988A");
		e1.setFechaNacimiento(LocalDate.now().minusYears(20));
		e1.setFecha_ini_contrato(LocalDate.now().minusDays(10));
		e1.setFecha_fin_contrato(LocalDate.now().plusYears(1));
		e1.setSueldo(1000);
		e1.setUsuario(userP2);
		e1.setNum_seg_social("298756432121");
		e1.setEmail("prueba@prueba.com");
		e1.setTelefono("777777777");
		
		e1.setTaller(taller);
		empleadoService.saveEmpleado(e1);

		HoraTrabajada hora = new HoraTrabajada();
		hora.setEmpleado(e1);
		hora.setHorasTrabajadas(10);
		hora.setPrecioHora(10.5);
		hora.setTrabajoRealizado("Cambio de rueda");
		
		List<HoraTrabajada> horas = new ArrayList<>();
		horas.add(hora);
		
		horasTrabajadasService.save(hora);
		
		r.setHorasTrabajadas(horas);
		
		reparacionService.saveReparacion(r);
		
		this.r=r;
		
		lf.setReparacion(r);
		lf.setPrecioBase(20.03);
		lf.setRecambio(rec);
		lfService.saveLineaFactura(lf);
		
		this.lf = lf;
	}
	
	@Test
	@Transactional
	void shouldInsertLineaFactura() {

		assertEquals(lf, lfService.findLineaFacturaById(lf.getId()).get());
	}
	
	@Test
	@Transactional
	void shouldNotLineaFactura() throws DataAccessException, FechasReparacionException, Max3ReparacionesSimultaneasPorEmpleadoException, DuplicatedMatriculaException, EmpleadoYCitaDistintoTallerException, NotAllowedException, CitaSinPresentarseException, NoMayorEdadEmpleadoException, InvalidPasswordException{
		

		LineaFactura lf1 = new LineaFactura();
		lf1.setDescuento(0);
		//Sin descripción
			
		
		lf1.setReparacion(r);
		lf1.setPrecioBase(20.03);
		lf1.setRecambio(rec);

		assertThrows(ConstraintViolationException.class, () -> this.lfService.saveLineaFactura(lf1));
	}
	
	@Test
	@Transactional
	void shouldDeleteLineaFactura() {
		assertTrue(lfService.findLineaFacturaById(lf.getId()).isPresent());
		
		lfService.delete(lf);
		assertFalse(lfService.findLineaFacturaById(lf.getId()).isPresent());
	}
	
}