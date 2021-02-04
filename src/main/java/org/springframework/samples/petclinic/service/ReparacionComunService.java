package org.springframework.samples.petclinic.service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.ReparacionComun;
import org.springframework.samples.petclinic.repository.ReparacionComunRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReparacionComunService {
	
	@Autowired
	private ReparacionComunRepository reparacionComunRep;
	
	@Transactional
	public void saveReparacionComun(ReparacionComun rc) {
		reparacionComunRep.save(rc);
	}
	
	@Transactional
	public Iterable<ReparacionComun> findAll() {
		return reparacionComunRep.findAll();
	}

	@Transactional
	public Optional<ReparacionComun> findById(Integer id) {
		return reparacionComunRep.findById(id);
	}
	
	@Transactional
	public void delete(ReparacionComun rc) {
		reparacionComunRep.delete(rc);
	}
	
	@Transactional(readOnly = true)
	public Collection<ReparacionComun> findRepComByNombre(String nombre) throws DataAccessException {
		return reparacionComunRep.findByNombre(nombre);
	}
	

}
