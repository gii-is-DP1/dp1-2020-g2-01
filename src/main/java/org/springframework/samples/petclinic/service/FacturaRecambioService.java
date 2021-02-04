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
import org.springframework.samples.petclinic.model.FacturaRecambio;
import org.springframework.samples.petclinic.repository.FacturaRecambioRepository;
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
	public List<FacturaRecambio> findAll() throws DataAccessException {
		return facturaRecambioRepository.findAll(Sort.by(Sort.Direction.DESC, "fechaPago"));
	}

	@Transactional(readOnly = true)
	public Optional<FacturaRecambio> findFacturaRecambioById(int id) throws DataAccessException {
		return facturaRecambioRepository.findById(id);
	}

	@Transactional
	public void delete(FacturaRecambio fRecambio) {
		facturaRecambioRepository.delete(fRecambio);
	}
	
	@Transactional
	public List<Integer> getAnyosFacturaRecambio() throws DataAccessException{
		Set<Integer> res = new HashSet<>();
		List<FacturaRecambio> facturas = this.findAll();
		for(FacturaRecambio factura:facturas) {
			res.add(factura.getFechaPago().getYear());
		}
		return res.stream().collect(Collectors.toList());
	}
	
	@Transactional
	public List<Month> getMesesFacturaRecambio() throws DataAccessException{
		Set<Month> res = new HashSet<>();
		List<FacturaRecambio> facturas = this.findAll();
		for(FacturaRecambio factura:facturas) {
			res.add(factura.getFechaPago().getMonth());
		}
		return res.stream().collect(Collectors.toList());
	}
	
	@Transactional 
	public List<FacturaRecambio> findFacturasRecambioMesAnyo(Month m, int y) throws DataAccessException{
		LocalDate iniMes = LocalDate.of(y, m.getValue(), 1);
		LocalDate finMes = LocalDate.of(y, m.getValue(), LocalDate.now().lengthOfMonth());
		return facturaRecambioRepository.findFacturaRecambioByFechaPagoAfterAndFechaPagoBefore(iniMes,finMes, Sort.by(Sort.Direction.DESC, "fechaPago"));
	}
	
	

}
