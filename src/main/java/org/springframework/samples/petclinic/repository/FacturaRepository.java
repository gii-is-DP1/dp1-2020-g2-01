package org.springframework.samples.petclinic.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Factura;

public interface FacturaRepository extends CrudRepository<Factura, Integer>{

}
