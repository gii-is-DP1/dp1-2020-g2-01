package org.springframework.samples.petclinic.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Reparacion;
import org.springframework.samples.petclinic.repository.ReparacionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReparacionService {
	
	private ReparacionRepository reparacionRepository;
	
	@Autowired
	public ReparacionService(ReparacionRepository reparacionRepository) {
		this.reparacionRepository = reparacionRepository;
	}
	
	@Transactional
	public void saveReparacion(Reparacion reparacion) throws DataAccessException  {
		reparacionRepository.save(reparacion);
	}
	
	@Transactional(readOnly = true)
	public Iterable<Reparacion> findAll() throws DataAccessException {
		return reparacionRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public Optional<Reparacion> findReparacionById(int id) throws DataAccessException {
		return reparacionRepository.findById(id);
	}
	
	
	@Transactional
	public void delete(Reparacion reparacion) {
		reparacionRepository.delete(reparacion);
	}
	

}
