package org.springframework.samples.petclinic.service;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.LineaFactura;
import org.springframework.samples.petclinic.repository.LineaFacturaRepository;
import org.springframework.samples.petclinic.service.exceptions.FechasReparacionException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LineaFacturaService {

	private LineaFacturaRepository facturaRepository;
	
	@Autowired
	public LineaFacturaService(LineaFacturaRepository facturaRepository) {
		this.facturaRepository = facturaRepository;
	}
	
	@Transactional
	public void saveFactura(LineaFactura factura) throws DataAccessException, FechasReparacionException {
		LocalDate fechaPago = factura.getFechaPago();
		LocalDate fechaRecogida = factura.getReparacion().getFechaRecogida();
		if(fechaPago != null && fechaRecogida != null && !(fechaPago.isEqual(fechaRecogida))) {
			throw new FechasReparacionException();
		}
		
		facturaRepository.save(factura);
	}
	
	@Transactional(readOnly = true)
	public Iterable<LineaFactura> findAll() throws DataAccessException {
		return facturaRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public Optional<LineaFactura> findFacturaById(int id) throws DataAccessException {
		return facturaRepository.findById(id);
	}
	
	
	@Transactional
	public void delete(LineaFactura factura) {
		facturaRepository.delete(factura);
	}
	
}
