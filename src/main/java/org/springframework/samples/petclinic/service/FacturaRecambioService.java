package org.springframework.samples.petclinic.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Factura;
import org.springframework.samples.petclinic.model.FacturaRecambio;
import org.springframework.samples.petclinic.repository.FacturaRecambioRepository;
import org.springframework.samples.petclinic.repository.FacturaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FacturaRecambioService {

	@Autowired
	private FacturaRecambioRepository facturaRecambioRepository;

	@Transactional
	public void saveFacturaRecambio(FacturaRecambio fRecambio) {
		this.facturaRecambioRepository.save(fRecambio);
	}

	@Transactional(readOnly = true)
	public Iterable<FacturaRecambio> findAll() throws DataAccessException {
		return facturaRecambioRepository.findAll();
	}

	@Transactional(readOnly = true)
	public Optional<FacturaRecambio> findFacturaRecambioById(int id) throws DataAccessException {
		return facturaRecambioRepository.findById(id);
	}

	@Transactional
	public void delete(FacturaRecambio fRecambio) {
		facturaRecambioRepository.delete(fRecambio);
	}
	
	

}
