package org.springframework.samples.petclinic.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Cliente;
import org.springframework.samples.petclinic.repository.ClienteRepository;
import org.springframework.samples.petclinic.service.exceptions.InvalidPasswordException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ClienteService {
	
	private ClienteRepository clienteRepository;	
	
	@Autowired
	private AuthoritiesService authoritiesService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	public ClienteService(ClienteRepository clienteRepository) {
		this.clienteRepository = clienteRepository;
	}
	
	@Transactional(readOnly = true)
	public Iterable<Cliente> findAll() throws DataAccessException {
		return clienteRepository.findAll();
	}
	
	@Transactional
	public void delete(Cliente cliente) {
		clienteRepository.delete(cliente);
		log.info("Cliente con id " + cliente.getId() + " borrado");
	}
	
	@Transactional(readOnly = true)
	public Collection<Cliente> findClientesByApellidos(String apellidos) throws DataAccessException {
		return clienteRepository.findByApellidos(apellidos);
	}
	
	@Transactional(readOnly=true)
	public Optional<Cliente> findClienteByDNI(String dni) throws DataAccessException{
		return clienteRepository.findByDNI(dni);
	}
	
	@Transactional
	public void saveCliente(Cliente cliente) throws DataAccessException, InvalidPasswordException {
		cliente.getUser().setAuthorities(new ArrayList<>());
		clienteRepository.save(cliente);
		userService.saveUser(cliente.getUser());		
		authoritiesService.saveAuthorities(cliente.getUser().getUsername(), "cliente");
		log.info("Cliente y usuario con authorities de cliente creado");
		
	}

	@Transactional(readOnly = true)
	public Optional<Cliente> findClienteById(int id) throws DataAccessException {
		return clienteRepository.findById(id);
	}
	
	@Transactional(readOnly = true)
	public Optional<Cliente> findClientesByUsername(String username) throws DataAccessException {
		return clienteRepository.findByUsername(username);
	}

}
