package org.springframework.samples.petclinic.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Factura;
import org.springframework.samples.petclinic.repository.FacturaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FacturaService {

	@Autowired
	private FacturaRepository facturaRepository;

	@Transactional
	public void saveFactura(Factura factura) {
		this.facturaRepository.save(factura);
	}

	@Transactional(readOnly = true)
	public Iterable<Factura> findAll() throws DataAccessException {
		return facturaRepository.findAll();
	}

	@Transactional(readOnly = true)
	public Optional<Factura> findFacturaById(int id) throws DataAccessException {
		return facturaRepository.findById(id);
	}

	@Transactional
	public void delete(Factura factura) {
		facturaRepository.delete(factura);
	}
	
	

}
