package org.springframework.samples.petclinic.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.PedidoRecambio;
import org.springframework.samples.petclinic.repository.PedidoRecambioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PedidoRecambioService {

		@Autowired
		private PedidoRecambioRepository pedidoRecambioRepository;

		@Transactional
		public void savePedidoRecambio(PedidoRecambio pedidoRecambio) {
			pedidoRecambioRepository.save(pedidoRecambio);
		}

		@Transactional(readOnly = true)
		public Iterable<PedidoRecambio> findAll() throws DataAccessException {
			return pedidoRecambioRepository.findAll();
		}

		@Transactional(readOnly = true)
		public Optional<PedidoRecambio> findLineaFacturaById(int id) throws DataAccessException {
			return pedidoRecambioRepository.findById(id);
		}

		@Transactional
		public void delete(PedidoRecambio pedidoRecambio) {
			pedidoRecambioRepository.delete(pedidoRecambio);
		}
		
		@Transactional(readOnly= true)
		public Optional<PedidoRecambio> findById(int id) {
			return pedidoRecambioRepository.findById(id);
		}

}
