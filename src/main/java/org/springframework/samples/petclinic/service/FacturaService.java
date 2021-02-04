package org.springframework.samples.petclinic.service;

import java.time.LocalDate;
import java.time.Month;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Sort;
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
	public List<Factura> findAll() throws DataAccessException {
		return facturaRepository.findAll(Sort.by(Sort.Direction.DESC, "fechaPago"));
	}

	@Transactional(readOnly = true)
	public Optional<Factura> findFacturaById(int id) throws DataAccessException {
		return facturaRepository.findById(id);
	}

	@Transactional
	public void delete(Factura factura) {
		facturaRepository.delete(factura);
	}
	
	@Transactional
	public List<Factura> findFacturasMesActual() throws DataAccessException{
		int y = LocalDate.now().getYear();
		Month m = LocalDate.now().getMonth();
		LocalDate iniMes = LocalDate.of(y, m.getValue(), 1);
		LocalDate finMes = LocalDate.of(y, m.getValue(), LocalDate.now().lengthOfMonth());
		return facturaRepository.findFacturaByFechaPagoAfterAndFechaPagoBefore(iniMes,finMes, Sort.by(Sort.Direction.DESC, "fechaPago"));
	}
	
	@Transactional 
	public List<Factura> findFacturasMesAnyo(Month m, int y) throws DataAccessException{
		LocalDate iniMes = LocalDate.of(y, m.getValue(), 1);
		LocalDate finMes = LocalDate.of(y, m.getValue(), LocalDate.now().lengthOfMonth());
		return facturaRepository.findFacturaByFechaPagoAfterAndFechaPagoBefore(iniMes,finMes, Sort.by(Sort.Direction.DESC, "fechaPago"));
	}
	
	@Transactional
	public List<Integer> getAnyosFactura() throws DataAccessException{
		Set<Integer> res = new HashSet<>();
		List<Factura> facturas = this.findAll();
		for(Factura factura:facturas) {
			res.add(factura.getFechaPago().getYear());
		}
		return res.stream().collect(Collectors.toList());
	}
	
	@Transactional
	public List<Month> getMesesFactura() throws DataAccessException{
		Set<Month> res = new HashSet<>();
		List<Factura> facturas = this.findAll();
		for(Factura factura:facturas) {
			res.add(factura.getFechaPago().getMonth());
		}
		return res.stream().collect(Collectors.toList());
	}
	
	
	
	
	

}
