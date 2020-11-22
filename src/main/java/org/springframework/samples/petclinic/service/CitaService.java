package org.springframework.samples.petclinic.service;


import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Cita;
import org.springframework.samples.petclinic.repository.CitaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CitaService {
	private CitaRepository citaRepository;
	
	@Autowired
	public CitaService(CitaRepository citaRepository) {
		this.citaRepository = citaRepository;
	}
	
	@Transactional
	public void saveCita(Cita cita) throws DataAccessException {
		citaRepository.save(cita);
	}
	
	@Transactional(readOnly = true)
	public Iterable<Cita> findAll() throws DataAccessException {
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
}
