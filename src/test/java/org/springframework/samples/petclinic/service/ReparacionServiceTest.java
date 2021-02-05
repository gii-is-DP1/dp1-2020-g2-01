package org.springframework.samples.petclinic.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
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
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Cita;
import org.springframework.samples.petclinic.model.Empleado;
import org.springframework.samples.petclinic.model.HorasTrabajadas;
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
	
	@Test
	void shouldInsertReparacion() throws DataAccessException, FechasReparacionException, Max3ReparacionesSimultaneasPorEmpleadoException, DuplicatedMatriculaException, EmpleadoYCitaDistintoTallerException, NotAllowedException, CitaSinPresentarseException, NoMayorEdadEmpleadoException, InvalidPasswordException {
		
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

		HorasTrabajadas hora = new HorasTrabajadas();
		hora.setEmpleado(e1);
		hora.setHorasTrabajadas(10);
		hora.setPrecioHora(10.5);
		hora.setTrabajoRealizado("Cambio de rueda");
		
		List<HorasTrabajadas> horas = new ArrayList<>();
		horas.add(hora);
		
		horasTrabajadasService.save(hora);
		
		r.setHorasTrabajadas(horas);
		
		reparacionService.saveReparacion(r);
		assertEquals(r, reparacionService.findReparacionById(r.getId()).get());
		
		
	}
	
	
	@Test
	@Transactional
	void shouldNotInsertReparacionInvalida() throws DataAccessException, DuplicatedMatriculaException, EmpleadoYCitaDistintoTallerException, NotAllowedException, CitaSinPresentarseException, NoMayorEdadEmpleadoException, InvalidPasswordException {
		
		Reparacion r = new Reparacion();

		r.setDescripcion(""); //Descripción vacía

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
		e1.setNum_seg_social("234567290145");
		e1.setEmail("prueba@prueba.com");
		e1.setTelefono("777777777");

		e1.setTaller(taller);
		
		empleadoService.saveEmpleado(e1);

		HorasTrabajadas hora = new HorasTrabajadas();
		hora.setEmpleado(e1);
		hora.setHorasTrabajadas(10);
		hora.setPrecioHora(10.5);
		hora.setTrabajoRealizado("Cambio de rueda");
		
		List<HorasTrabajadas> horas = new ArrayList<>();
		horas.add(hora);
		
		horasTrabajadasService.save(hora);
		
		r.setHorasTrabajadas(horas);
		
		assertThrows(ConstraintViolationException.class, () -> this.reparacionService.saveReparacion(r));
		
	}
	
	@Test
	void shouldNotInsertReparacionConFechasIncorrectas() throws DataAccessException, DuplicatedMatriculaException, EmpleadoYCitaDistintoTallerException, NotAllowedException, CitaSinPresentarseException, NoMayorEdadEmpleadoException, InvalidPasswordException {
		
		Reparacion r = new Reparacion();
		r.setDescripcion("Una descripcion hola que tal");
		r.setFechaEntrega(LocalDate.now().plusDays(7));
		r.setTiempoEstimado(LocalDate.now().plusDays(8));
		r.setFechaFinalizacion(LocalDate.now().plusDays(11));
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
		e1.setNum_seg_social("234567890141");
		e1.setEmail("prueba@prueba.com");
		e1.setTelefono("777777777");
		
		e1.setTaller(taller);
		empleadoService.saveEmpleado(e1);

		HorasTrabajadas hora = new HorasTrabajadas();
		hora.setEmpleado(e1);
		hora.setHorasTrabajadas(10);
		hora.setPrecioHora(10.5);
		hora.setTrabajoRealizado("Cambio de rueda");
		
		List<HorasTrabajadas> horas = new ArrayList<>();
		horas.add(hora);
		
		horasTrabajadasService.save(hora);
		
		r.setHorasTrabajadas(horas);
		
		assertThrows(FechasReparacionException.class, () -> this.reparacionService.saveReparacion(r));
		
	}
	
	
	@Test
	void shouldNotInsertReparacionConEmpleadoCon3ReparacionesSimultaneas() throws DataAccessException, FechasReparacionException, Max3ReparacionesSimultaneasPorEmpleadoException, DuplicatedMatriculaException, EmpleadoYCitaDistintoTallerException, NotAllowedException, CitaSinPresentarseException, NoMayorEdadEmpleadoException, InvalidPasswordException {
		//Setup inicial de reparacion (faltan cosas)
		Reparacion r = new Reparacion();
		r.setDescripcion("Descripción de prueba"); 
		r.setFechaEntrega(LocalDate.now().plusDays(7));
		r.setTiempoEstimado(LocalDate.now().plusDays(8));
		r.setFechaRecogida(LocalDate.now().plusDays(10));
	
		//Setup de reparacion y cita
		Cita c = new Cita();
		TipoCita t = tipoCitaService.findById(1).get();
		List<TipoCita> tipos = new ArrayList<>();
		tipos.add(t);
		c.setFecha(LocalDate.now().plusDays(2));
		c.setHora(18);
		c.setTiposCita(tipos);
		c.setVehiculo(vehiculoService.findVehiculoByMatricula("1234ABC").get());
		
		//Setup de taller
		Taller taller = new Taller();
		taller.setCorreo("test@test.com");
		taller.setName("test");
		taller.setTelefono("123456789");
		taller.setUbicacion("calle test");
		
		tallerService.saveTaller(taller);
		
		c.setTaller(taller);
		
		citaService.saveCita(c, "jesfunrud");
		
		//Asigna una cita a la reparacion
		r.setCita(citaService.findCitaByFechaAndHora(LocalDate.now().plusDays(2), 18));
		
		//Añadir un empleado
		Empleado e1 = new Empleado();
		User userP = new User();
		userP.setUsername("nombreusuario2");
		userP.setPassword("passdeprueba1");
		userP.setEnabled(true);
		e1.setNombre("Pepito1");
		e1.setApellidos("Grillo1");
		e1.setDni("89898983A");
		e1.setFechaNacimiento(LocalDate.now().minusYears(20));
		e1.setFecha_ini_contrato(LocalDate.now().minusDays(10));
		e1.setFecha_fin_contrato(LocalDate.now().plusYears(1));
		e1.setSueldo(1000);
		e1.setUsuario(userP);
		e1.setNum_seg_social("234567890141");
		e1.setEmail("prueba@prueba.com");
		e1.setTelefono("777777777");
		

		e1.setTaller(taller);
		
		empleadoService.saveEmpleado(e1);

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
		c1.setTiposCita(tipos);
		c1.setVehiculo(vehiculoService.findVehiculoByMatricula("1234ABC").get());
		c1.setTaller(taller);
		
		citaService.saveCita(c1, "jesfunrud");
		
		r1.setCita(citaService.findCitaByFechaAndHora(LocalDate.now().plusDays(2), 10));
		
		HorasTrabajadas hora = new HorasTrabajadas();
		hora.setEmpleado(e1);
		hora.setHorasTrabajadas(10);
		hora.setPrecioHora(10.5);
		hora.setTrabajoRealizado("Cambio de rueda");
		
		List<HorasTrabajadas> horas = new ArrayList<>();
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
		c2.setTiposCita(tipos);
		c2.setVehiculo(vehiculoService.findVehiculoByMatricula("1234ABC").get());
		c2.setTaller(taller);
		
		citaService.saveCita(c2, "jesfunrud");
		
		r2.setCita(citaService.findCitaByFechaAndHora(LocalDate.now().plusDays(2), 11));
		
		HorasTrabajadas hora1 = new HorasTrabajadas();
		hora1.setEmpleado(e1);
		hora1.setHorasTrabajadas(10);
		hora1.setPrecioHora(10.5);
		hora1.setTrabajoRealizado("Cambio de rueda");
		
		List<HorasTrabajadas> horas1 = new ArrayList<>();
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
		c3.setTiposCita(tipos);
		c3.setVehiculo(vehiculoService.findVehiculoByMatricula("1234ABC").get());
		c3.setTaller(taller);
		
		citaService.saveCita(c3, "jesfunrud");
		
		r3.setCita(citaService.findCitaByFechaAndHora(LocalDate.now().plusDays(2), 12));
		
		HorasTrabajadas hora2 = new HorasTrabajadas();
		hora2.setEmpleado(e1);
		hora2.setHorasTrabajadas(10);
		hora2.setPrecioHora(10.5);
		hora2.setTrabajoRealizado("Cambio de rueda");
		
		List<HorasTrabajadas> horas2 = new ArrayList<>();
		horas2.add(hora2);
		
		horasTrabajadasService.save(hora2);
		
		r3.setHorasTrabajadas(horas2);
		
		reparacionService.saveReparacion(r3);
		
		r.setHorasTrabajadas(horas);
		assertThrows(Max3ReparacionesSimultaneasPorEmpleadoException.class, () -> this.reparacionService.saveReparacion(r));
	}	
	
	@Test
	void shouldUpdateReparacion() throws DataAccessException, FechasReparacionException, Max3ReparacionesSimultaneasPorEmpleadoException, DuplicatedMatriculaException, EmpleadoYCitaDistintoTallerException, NotAllowedException, CitaSinPresentarseException, NoMayorEdadEmpleadoException, InvalidPasswordException {
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
		e1.setNum_seg_social("234567890144");
		e1.setEmail("prueba@prueba.com");
		e1.setTelefono("777777777");
		
		e1.setTaller(taller);
		empleadoService.saveEmpleado(e1);
		
		HorasTrabajadas hora = new HorasTrabajadas();
		hora.setEmpleado(e1);
		hora.setHorasTrabajadas(10);
		hora.setPrecioHora(10.5);
		hora.setTrabajoRealizado("Cambio de rueda");
		
		List<HorasTrabajadas> horas = new ArrayList<>();
		horas.add(hora);
		
		horasTrabajadasService.save(hora);
		
		r.setHorasTrabajadas(horas);
		
		reparacionService.saveReparacion(r);
		
		Reparacion r1 = reparacionService.findReparacionById(r.getId()).get();
		r1.setDescripcion("Descripcion cambiada");
		
		reparacionService.saveReparacion(r1);
		
		assertTrue(reparacionService.findReparacionById(r1.getId()).isPresent());

	}
	
	@Test
	void shouldNotUpdateReparacionInvalida() throws DataAccessException, FechasReparacionException, Max3ReparacionesSimultaneasPorEmpleadoException, DuplicatedMatriculaException, EmpleadoYCitaDistintoTallerException, NotAllowedException, CitaSinPresentarseException, NoMayorEdadEmpleadoException, InvalidPasswordException {
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
		e1.setNum_seg_social("234567890345");
		e1.setEmail("prueba@prueba.com");
		e1.setTelefono("777777777");
		
		e1.setTaller(taller);
		
		empleadoService.saveEmpleado(e1);

		HorasTrabajadas hora = new HorasTrabajadas();
		hora.setEmpleado(e1);
		hora.setHorasTrabajadas(10);
		hora.setPrecioHora(10.5);
		hora.setTrabajoRealizado("Cambio de rueda");
		
		List<HorasTrabajadas> horas = new ArrayList<>();
		horas.add(hora);
		
		horasTrabajadasService.save(hora);
		
		r.setHorasTrabajadas(horas);
		
		reparacionService.saveReparacion(r);
		
		Reparacion r1 = reparacionService.findReparacionById(r.getId()).get();
		r1.setDescripcion("");
		
		
		
		assertThrows(ConstraintViolationException.class, () ->{
			reparacionService.saveReparacion(r1);
			em.flush();
		});

	}
	
	
	@Test
	void shouldDeleteReparacion() throws DataAccessException, FechasReparacionException, Max3ReparacionesSimultaneasPorEmpleadoException, DuplicatedMatriculaException, EmpleadoYCitaDistintoTallerException, NotAllowedException, CitaSinPresentarseException, NoMayorEdadEmpleadoException, InvalidPasswordException {
		
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
		e1.setNum_seg_social("234567891145");
		e1.setEmail("prueba@prueba.com");
		e1.setTelefono("777777777");
		
		e1.setTaller(taller);
		empleadoService.saveEmpleado(e1);

		HorasTrabajadas hora = new HorasTrabajadas();
		hora.setEmpleado(e1);
		hora.setHorasTrabajadas(10);
		hora.setPrecioHora(10.5);
		hora.setTrabajoRealizado("Cambio de rueda");
		
		List<HorasTrabajadas> horas = new ArrayList<>();
		horas.add(hora);
		
		horasTrabajadasService.save(hora);
		
		r.setHorasTrabajadas(horas);
		
		reparacionService.saveReparacion(r);
		assertTrue(reparacionService.findReparacionById(r.getId()).isPresent());
		
		reparacionService.delete(r);
		assertFalse(reparacionService.findReparacionById(r.getId()).isPresent());
		
	}
	
	@Test
	@Transactional
	void shouldFinalizar() throws DataAccessException, FechasReparacionException, Max3ReparacionesSimultaneasPorEmpleadoException, DuplicatedMatriculaException, EmpleadoYCitaDistintoTallerException, NotAllowedException, CitaSinPresentarseException, NoMayorEdadEmpleadoException, InvalidPasswordException{
		Reparacion r = new Reparacion();
		r.setDescripcion("Una descripcion hola que tal");
		r.setFechaEntrega(LocalDate.now().plusDays(2));
		r.setTiempoEstimado(LocalDate.now().plusDays(9));
		r.setFechaFinalizacion(LocalDate.now().plusDays(9));
		r.setFechaRecogida(LocalDate.now().plusDays(11));
		
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
		e1.setNum_seg_social("234567890148");
		e1.setEmail("prueba@prueba.com");
		e1.setTelefono("777777777");
		
		e1.setTaller(taller);
		empleadoService.saveEmpleado(e1);

		HorasTrabajadas hora = new HorasTrabajadas();
		hora.setEmpleado(e1);
		hora.setHorasTrabajadas(10);
		hora.setPrecioHora(10.5);
		hora.setTrabajoRealizado("Cambio de rueda");
		
		List<HorasTrabajadas> horas = new ArrayList<>();
		horas.add(hora);
		
		horasTrabajadasService.save(hora);
		
		r.setHorasTrabajadas(horas);
		
		reparacionService.saveReparacion(r);
		
		reparacionService.finalizar(r);
		assertEquals(r.getFechaFinalizacion(), LocalDate.now());
		

	}
	
	@Test
	void shouldGetReparacionesCliente() throws DataAccessException, FechasReparacionException, Max3ReparacionesSimultaneasPorEmpleadoException, DuplicatedMatriculaException, EmpleadoYCitaDistintoTallerException, NotAllowedException, CitaSinPresentarseException, NoMayorEdadEmpleadoException, InvalidPasswordException {
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
		e1.setNum_seg_social("234567890345");
		e1.setEmail("prueba@prueba.com");
		e1.setTelefono("777777777");
		
		e1.setTaller(taller);
		empleadoService.saveEmpleado(e1);

		HorasTrabajadas hora = new HorasTrabajadas();
		hora.setEmpleado(e1);
		hora.setHorasTrabajadas(10);
		hora.setPrecioHora(10.5);
		hora.setTrabajoRealizado("Cambio de rueda");
		
		List<HorasTrabajadas> horas = new ArrayList<>();
		horas.add(hora);
		
		horasTrabajadasService.save(hora);
		
		r.setHorasTrabajadas(horas);
		
		reparacionService.saveReparacion(r);
		
		assertEquals(r, reparacionService.findReparacionById(r.getId()).get());
	}
	
}
