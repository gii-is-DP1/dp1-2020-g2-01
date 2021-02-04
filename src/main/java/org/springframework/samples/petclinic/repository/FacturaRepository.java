package org.springframework.samples.petclinic.repository;


import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Sort;

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Factura;

public interface FacturaRepository extends CrudRepository<Factura, Integer>{

	List<Factura> findFacturaByFechaPagoAfterAndFechaPagoBefore(LocalDate iniMes, LocalDate finMes, Sort sort);
	
	List<Factura> findAll(Sort sort);
}
