package org.springframework.samples.petclinic.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Taller;
import org.springframework.samples.petclinic.repository.TallerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TallerService {

	@Autowired
	private TallerRepository tallerRepository;
	
	@Transactional
	public void saveTaller(Taller taller) {
		tallerRepository.save(taller);
		log.info("Taller creado");
	}
	
	@Transactional(readOnly = true)
	public List<Taller> findAll() {
		return tallerRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public Optional<Taller> findById(Integer id) {
		return tallerRepository.findById(id);
	}
	
	@Transactional
	public void delete(Taller taller) {
		tallerRepository.delete(taller);
		log.info("Taller con id " + taller.getId() + " borrado");
	}

	@Transactional(readOnly = true)
	public Optional<Taller> findByUbicacion(String ubicacion) throws DataAccessException{
		return tallerRepository.findByUbicacion(ubicacion);
	}
}
