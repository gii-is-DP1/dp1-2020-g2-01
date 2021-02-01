package org.springframework.samples.petclinic.service;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.samples.petclinic.model.Cita;
import org.springframework.samples.petclinic.model.Cliente;
import org.springframework.samples.petclinic.model.Empleado;
import org.springframework.samples.petclinic.model.Vehiculo;
import org.springframework.samples.petclinic.repository.CitaRepository;
import org.springframework.samples.petclinic.service.exceptions.EmpleadoYCitaDistintoTallerException;
import org.springframework.samples.petclinic.service.exceptions.NotAllowedException;
import org.springframework.samples.petclinic.util.LoggedUser;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javassist.NotFoundException;

@Service
public class CitaService {
	
	@Autowired
	private CitaRepository citaRepository;
	
	@Autowired
	private SendEmailService sendEmailService;
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private VehiculoService vehiculoService;
	
	
	@Transactional
	public void saveCita(Cita cita) throws DataAccessException, EmpleadoYCitaDistintoTallerException, NotAllowedException
	{	
		List<Empleado> empleados = cita.getEmpleados();
		if(empleados!=null) {
			for(Empleado e:empleados) {
				if(!e.getTaller().equals(cita.getTaller())) {
					throw new EmpleadoYCitaDistintoTallerException();
				}
			}
		}
		

		String username = LoggedUser.getUsername();
		Optional<Cliente> c = clienteService.findClientesByUsername(username);
		if(c.isPresent()) {
			List<Vehiculo> vehiculosCliente = vehiculoService.findVehiculosCliente(c.get());
			if(!vehiculosCliente.contains(cita.getVehiculo())) {
				throw new NotAllowedException();
			}
		}
		
		citaRepository.save(cita);
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
		return citaRepository.findCitaByTallerUbicacion(ubicacion, Sort.by(Sort.Direction.ASC, "fecha", "hora"));
	}
	
	@Transactional
	public void delete(Cita cita) {
		citaRepository.delete(cita);
	}
	
	@Transactional
	public void deleteCOVID() throws DataAccessException{
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
		
		List<Cita> citas = this.findAll();
			for(int i=0;i<citas.size();i++) {
				Cita cita = citas.get(i);
				String to = cita.getVehiculo().getCliente().getEmail();
				LocalDate fecha = cita.getFecha();
				if(fecha.isAfter(inicioCuarentena) && fecha.isBefore(finCuarentena)) {
					sendEmailService.sendEmail(to, subject, content);
					this.delete(cita);
					
				}
			}
	}
	
	@Transactional(readOnly = true)
	public Cita findCitaByFechaAndHora(LocalDate fecha, Integer hora) throws DataAccessException {
		Cita c = citaRepository.findCitaByFechaAndHora(fecha, hora);
		return c;
	}
	
	@Transactional
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
	public List<Cita> findByCliente(Cliente cliente) throws DataAccessException{
		return citaRepository.findByUsername(cliente, Sort.by(Sort.Direction.ASC, "fecha", "hora"));
	}
	
	
}
