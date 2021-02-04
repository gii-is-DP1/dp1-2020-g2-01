<<<<<<< Upstream, based on origin/fraborcar
=======
package org.springframework.samples.petclinic.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.EjemplarRecambio;
import org.springframework.samples.petclinic.repository.EjemplarRecambioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EjemplarRecambioService {
	
	@Autowired
	private EjemplarRecambioRepository ejemplarRecambioRepository;

	@Transactional
	public void saveEjemplarRecambio(EjemplarRecambio ejemplarRecambio) {
		this.ejemplarRecambioRepository.save(ejemplarRecambio);
		log.info("Ejemplar recambio creado");
	}

	@Transactional(readOnly = true)
	public Iterable<EjemplarRecambio> findAll() throws DataAccessException {
		return ejemplarRecambioRepository.findAll();
	}

	@Transactional(readOnly = true)
	public Optional<EjemplarRecambio> findEjemplarRecambioById(int id) throws DataAccessException {
		return ejemplarRecambioRepository.findById(id);
	}

	@Transactional
	public void delete(EjemplarRecambio ejemplarRecambio) {
		ejemplarRecambioRepository.delete(ejemplarRecambio);
		log.info("Ejemplar recambio con id " + ejemplarRecambio.getId() + " borrado");
	}

}
>>>>>>> f5c2e78 Generación de log y correcciones menores
