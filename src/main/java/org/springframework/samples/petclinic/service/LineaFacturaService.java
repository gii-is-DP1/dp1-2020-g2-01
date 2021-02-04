package org.springframework.samples.petclinic.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.LineaFactura;
import org.springframework.samples.petclinic.repository.LineaFacturaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class LineaFacturaService {

	@Autowired
	private LineaFacturaRepository lineafacturaRepository;

	@Transactional
	public void saveLineaFactura(LineaFactura lineafactura) {
		lineafacturaRepository.save(lineafactura);
		log.info("Linea factura creada");
	}

	@Transactional(readOnly = true)
	public Iterable<LineaFactura> findAll() throws DataAccessException {
		return lineafacturaRepository.findAll();
	}

	@Transactional(readOnly = true)
	public Optional<LineaFactura> findLineaFacturaById(int id) throws DataAccessException {
		return lineafacturaRepository.findById(id);
	}

	@Transactional
	public void delete(LineaFactura factura) {
		lineafacturaRepository.delete(factura);
		log.info("Linea factura con id " + factura.getId() + " borrada");
	}

}