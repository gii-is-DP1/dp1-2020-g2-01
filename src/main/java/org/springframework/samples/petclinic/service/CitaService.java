package org.springframework.samples.petclinic.service;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Cita;
import org.springframework.samples.petclinic.repository.CitaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CitaService {
	
	@Autowired
	private CitaRepository citaRepository;
	
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
	
	@Transactional
	public void deleteCOVID() throws DataAccessException{
		LocalDate inicioCuarentena = LocalDate.now().minusDays(1);
		LocalDate finCuarentena = LocalDate.now().plusDays(15);
		List<Cita> citas = this.findAll();
			for(int i=0;i<citas.size();i++) {
				Cita cita = citas.get(i);
				LocalDate fecha = cita.getFecha();
				if(fecha.isAfter(inicioCuarentena) && fecha.isBefore(finCuarentena)) {
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
		List<Cita> cita = citaRepository.findCitaByFecha(fecha);
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
	
	
}
