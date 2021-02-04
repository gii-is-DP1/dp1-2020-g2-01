package org.springframework.samples.petclinic.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.FacturaRecambio;

public interface FacturaRecambioRepository extends CrudRepository<FacturaRecambio, Integer>{
	
	List<FacturaRecambio> findAll(Sort sort);
	
	List<FacturaRecambio> findFacturaRecambioByFechaPagoAfterAndFechaPagoBefore(LocalDate iniMes, LocalDate finMes, Sort sort);

}
