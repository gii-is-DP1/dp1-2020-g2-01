package org.springframework.samples.petclinic.service;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Cita;
import org.springframework.samples.petclinic.model.TipoCita;
import org.springframework.samples.petclinic.model.Vehiculo;
import org.springframework.samples.petclinic.repository.CitaRepository;
import org.springframework.samples.petclinic.repository.TipoCitaRepository;
import org.springframework.samples.petclinic.repository.VehiculoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CitaService {
	
	@Autowired
	private CitaRepository citaRepository;
	
	@Autowired
	private VehiculoRepository vehiculoRepository;
	
	@Autowired
	private TipoCitaRepository tipoCitaRepository;
	
	@Autowired
	protected EntityManager em;
	
	@Transactional
	public void saveCita(Cita cita) throws DataAccessException {
		citaRepository.save(cita);
	}
	
	@Transactional(readOnly = true)
	public List<Cita> findAll() throws DataAccessException {
		return citaRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public Optional<Cita> findCitaById(int id) throws DataAccessException {
		return citaRepository.findById(id);
	}
	
	@Transactional
	public void delete(Cita cita) {
		citaRepository.delete(cita);
	}
	
	@Transactional(readOnly = true)
	public Cita findCitaByFechaAndHora(LocalDate fecha, Integer hora) throws DataAccessException {
		Cita c = citaRepository.findCitaByFechaAndHora(fecha, hora);
		return c;
	}

	@Transactional
	public List<Vehiculo> getVehiculosSeleccionadoPrimero(Cita cita) {
		Integer vehiculoId = cita.getVehiculo().getId();
		em.clear(); // Con esto evito que el cliente y las citas sean null por lo que pongo para evitar el stackoverflow
		Vehiculo vehiculo = vehiculoRepository.findById(vehiculoId).get();
		List<Vehiculo> vehiculos = vehiculoRepository.findAll();
		vehiculos.remove(vehiculo); 
		vehiculos.add(0, vehiculo); 
		return vehiculos;
	}

	@Transactional
	public List<TipoCita> geTiposCitaSeleccionadoPrimero(Cita cita) {
		Integer tipoCitaId = cita.getTipoCita().getId();
		TipoCita tipo = tipoCitaRepository.findById(tipoCitaId).get();
		List<TipoCita> tipos = tipoCitaRepository.findAll();
		tipos.remove(tipo);
		tipos.add(0, tipo);
		return tipos;
	}
}
