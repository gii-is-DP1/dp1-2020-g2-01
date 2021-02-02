package org.springframework.samples.petclinic.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Recambio;
import org.springframework.samples.petclinic.repository.RecambioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RecambioService {
	
	
	@Autowired
	private RecambioRepository recambioRepository;

	@Transactional
	public void saveRecambio(Recambio recambio) {
		this.recambioRepository.save(recambio);
	}

	@Transactional(readOnly = true)
	public Iterable<Recambio> findAll() throws DataAccessException {
		return recambioRepository.findAll();
	}

	@Transactional(readOnly = true)
	public Optional<Recambio> findRecambioById(int id) throws DataAccessException {
		return recambioRepository.findById(id);
	}
	
	@Transactional(readOnly = true)
	public Optional<Recambio> findRecambioByNombre(String nombre) throws DataAccessException {
		return recambioRepository.findRecambioByName(nombre);
	}

	@Transactional
	public void delete(Recambio recambio) {
		recambioRepository.delete(recambio);
	}
	
	@Transactional
	public Optional<Recambio> findRecambioByName(String name){
		return recambioRepository.findRecambioByName(name);
	}
}
