package org.springframework.samples.petclinic.repository;


import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Cliente;
import org.springframework.samples.petclinic.model.Factura;

public interface FacturaRepository extends CrudRepository<Factura, Integer>{

	List<Factura> findFacturaByFechaPagoAfterAndFechaPagoBefore(LocalDate iniMes, LocalDate finMes, Sort sort);
	
	List<Factura> findAll(Sort sort);

	@Query("SELECT f FROM Factura f WHERE :cliente IN (SELECT ln.reparacion.cita.vehiculo.cliente FROM f.lineaFactura ln)")
	Iterable<Factura> findFacturaByCliente(@Param("cliente") Cliente cliente);
}
