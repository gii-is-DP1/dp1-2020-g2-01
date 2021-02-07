package org.springframework.samples.petclinic.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.FacturaRecambio;

public interface FacturaRecambioRepository extends CrudRepository<FacturaRecambio, Integer>{
	
	List<FacturaRecambio> findAll(Sort sort);
	
	List<FacturaRecambio> findFacturaRecambioByFechaPagoAfterAndFechaPagoBefore(LocalDate iniMes, LocalDate finMes, Sort sort);
}
