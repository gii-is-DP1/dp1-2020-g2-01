package org.springframework.samples.petclinic.service;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.samples.petclinic.model.Cita;
import org.springframework.samples.petclinic.model.Cliente;
import org.springframework.samples.petclinic.model.Empleado;
import org.springframework.samples.petclinic.model.Vehiculo;
import org.springframework.samples.petclinic.repository.CitaRepository;
import org.springframework.samples.petclinic.service.exceptions.CitaSinPresentarseException;
import org.springframework.samples.petclinic.service.exceptions.EmpleadoYCitaDistintoTallerException;
import org.springframework.samples.petclinic.service.exceptions.FechasFuturaException;
import org.springframework.samples.petclinic.service.exceptions.NotAllowedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CitaService {
	
	
	private CitaRepository citaRepository;
	private SendEmailService sendEmailService;
	private ClienteService clienteService;
	private VehiculoService vehiculoService;
	
	@Autowired
	public CitaService(CitaRepository citaRepository, SendEmailService sendEmailService,
			ClienteService clienteService, VehiculoService vehiculoService) {
		this.citaRepository = citaRepository;
		this.sendEmailService = sendEmailService;
		this.clienteService = clienteService;
		this.vehiculoService = vehiculoService;
		
	}
	
	
	@Transactional
	public void saveCita(Cita cita, String username) throws DataAccessException, EmpleadoYCitaDistintoTallerException, NotAllowedException, CitaSinPresentarseException, FechasFuturaException
	{	
		List<Empleado> empleados = cita.getEmpleados();
		List<Cita> citasNoReparacion = this.findByUsername(username)
				.stream().filter(x->!x.isAsistido() && x.getFecha().isBefore(LocalDate.now()))
				.collect(Collectors.toList()); // Necesita el username
		if(empleados!=null) {
			for(Empleado e:empleados) {
				if(!e.getTaller().equals(cita.getTaller())) {
					throw new EmpleadoYCitaDistintoTallerException();
				}
			}
		}
		if(citasNoReparacion.size()>=3 && cita.getId() == null) {
			Boolean masDeUnaSemana = true;
				for(Cita citaR:citasNoReparacion) {
					
					if(citaR.getFecha().isAfter(LocalDate.now().minusDays(7))) {
						masDeUnaSemana = false;
						}
				}
				if(!masDeUnaSemana) {
					throw new CitaSinPresentarseException();
				}
			}
		
		if(cita.getId() == null && cita.getFecha().isBefore(LocalDate.now().plusDays(1))) {
			throw new FechasFuturaException();
		}
		
		Optional<Cliente> c = clienteService.findClientesByUsername(username);
		if(c.isPresent()) {
			List<Vehiculo> vehiculosCliente = vehiculoService.findVehiculosCliente(c.get());
			if(!vehiculosCliente.contains(cita.getVehiculo())) {
				throw new NotAllowedException();
			}
		}
		
		citaRepository.save(cita);
		log.info("Cita guardada");
		
	}
	
	@Transactional(readOnly = true)
	public List<Cita> findAll() throws DataAccessException {
		return citaRepository.findAll(Sort.by(Sort.Direction.ASC, "fecha", "hora"));
	}
	
	@Transactional(readOnly = true)
	public Cita findCitaById(int id) throws DataAccessException, NotFoundException{
		Optional<Cita> c = citaRepository.findById(id);
		if(!c.isPresent()) {
			throw new NotFoundException("");
		}
		
		Cita cita = c.get();
		return cita;
	}
	
	@Transactional(readOnly = true)
	public List<Cita> findCitaByTallerUbicacion(String ubicacion) throws DataAccessException {
		return citaRepository.findCitaByTallerUbicacionAndFechaAfter(ubicacion, LocalDate.now(), Sort.by(Sort.Direction.ASC, "fecha", "hora"));
	}
	
	@Transactional
	public void delete(Cita cita) {
		citaRepository.delete(cita);
		log.info("Cita con id " + cita.getId() + " borrada");
	}
	
	@Transactional
	public void deleteCOVID(String ubicacion) throws DataAccessException{
		LocalDate inicioCuarentena = LocalDate.now().minusDays(1);
		LocalDate finCuarentena = LocalDate.now().plusDays(15);
		String subject="Cancelación de su cita a causa del COVID-19";
		String content="Estimado cliente,\nSirva este correo para comunicarle que en nuestro taller se ha dado un"
				+ " caso confirmado de COVID-19, y por tanto, siguiendo las directrices del Ministerio de Sanidad "
				+ "todos los integrantes del taller deben guardar cuarentena durante los próximos 14 días. "
				+ "Es por ello que nos vemos obligados a cancelar su cita que estaba prevista dentro de las "
				+ "próximas dos semanas.\nSi lo desea, puede solicitar una nueva cita para un día a partir de "
				+ finCuarentena.toString()+".\n\n"
				+ "Sentimos todas las molestias que esto pudiera ocasionar,\nEl jefe del taller.";
		
		List<Cita> citas = this.findCitaByTallerUbicacion(ubicacion);
		for(int i=0;i<citas.size();i++) {
				Cita cita = citas.get(i);
				String to = cita.getVehiculo().getCliente().getEmail();
				LocalDate fecha = cita.getFecha();
				if(fecha.isAfter(inicioCuarentena) && fecha.isBefore(finCuarentena)) {
					sendEmailService.sendEmail(to, subject, content);
					this.delete(cita);
					log.info("Borrada cita con id " + cita.getId());
					
				}
			}
	}
	
	@Transactional(readOnly = true)
	public Cita findCitaByFechaAndHora(LocalDate fecha, Integer hora) throws DataAccessException {
		Cita c = citaRepository.findCitaByFechaAndHora(fecha, hora);
		return c;
	}
	
	@Transactional(readOnly=true)
	public Boolean hayCitaParaElDia(LocalDate fecha) {
		List<Cita> cita = citaRepository.findCitasByFecha(fecha);
		return cita.size() < 21 - 9; // El taller admite citas desde las 9 hasta las 21
	}
	
	@Transactional(readOnly=true)
	public List<Cita> findCitaSinReparacion() {
		List<Cita> citasPosibles = new ArrayList<>();
		List<Integer> ids = citaRepository.findCitaIdSinReparacion();
		for(Integer id: ids) {
			citasPosibles.add(citaRepository.findById(id).get());
		}
		return citasPosibles;
	}

	@Transactional(readOnly=true)
	public List<Cita> findCitasFuturasByCliente(Cliente cliente) throws DataAccessException{
		return citaRepository.findCitaByClienteAndFechaAfter(cliente, LocalDate.now(), Sort.by(Sort.Direction.ASC, "fecha", "hora"));
	}
	
	@Transactional(readOnly=true)
	public List<Cita> findByUsername(String username) throws DataAccessException{
		return citaRepository.findByUsername(username, Sort.by(Sort.Direction.ASC, "fecha", "hora"));
	}
	
	
	@Transactional(readOnly=true)
	public List<Cita> findCitasFuturas() {
		return citaRepository.findCitasByFechaAfter(LocalDate.now(), Sort.by(Sort.Direction.ASC, "fecha", "hora"));
	}

	@Transactional(readOnly=true)
	public List<Cita> findCitasHoy() {
		return citaRepository.findCitasByFechaEquals(LocalDate.now(), Sort.by(Sort.Direction.ASC, "fecha", "hora"));
	}

	@Transactional(readOnly=true)
	public List<Cita> findCitasPasadas() {
		return citaRepository.findCitasByFechaBefore(LocalDate.now(), Sort.by(Sort.Direction.DESC, "fecha", "hora"));
	}


	public List<Cita> findCitaHoyByTallerUbicacion(String ubicacion) {
		return citaRepository.findCitaByTallerUbicacionAndFechaEquals(ubicacion, LocalDate.now(), Sort.by(Sort.Direction.DESC, "fecha"));
	}


	public List<Cita> findCitasPasadasByTallerUbicacion(String ubicacion) {
		return citaRepository.findCitaByTallerUbicacionAndFechaBefore(ubicacion, LocalDate.now(), Sort.by(Sort.Direction.DESC, "fecha"));
	}


	public List<Cita> findCitaFuturasByTallerUbicacion(String ubicacion) {
		return citaRepository.findCitaByTallerUbicacionAndFechaAfter(ubicacion, LocalDate.now(), Sort.by(Sort.Direction.DESC, "fecha"));
	}


	public List<Cita> findCitasPasadasByVehiculoCliente(Cliente cliente) {
		return citaRepository.findCitaByVehiculoClienteAndFechaBefore(cliente, LocalDate.now(), Sort.by(Sort.Direction.DESC, "fecha"));
	}


	public List<Cita> findCitaHoyByVehiculoCliente(Cliente cliente) {
		return citaRepository.findCitaByVehiculoClienteAndFechaEquals(cliente, LocalDate.now(), Sort.by(Sort.Direction.DESC, "fecha"));
	}
	
}
