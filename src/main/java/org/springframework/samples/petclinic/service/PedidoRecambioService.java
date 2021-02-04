package org.springframework.samples.petclinic.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.PedidoRecambio;
import org.springframework.samples.petclinic.repository.PedidoRecambioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PedidoRecambioService {

		@Autowired
		private PedidoRecambioRepository pedidoRecambioRepository;

		@Transactional
		public void savePedidoRecambio(PedidoRecambio pedidoRecambio) {
			pedidoRecambioRepository.save(pedidoRecambio);
			log.info("Pedido recambio creado");
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
			log.info("Pedido recambio con id " + pedidoRecambio.getId() + " borrado");
		}
		
		@Transactional(readOnly= true)
		public Optional<PedidoRecambio> findById(int id) {
			return pedidoRecambioRepository.findById(id);
		}

}
