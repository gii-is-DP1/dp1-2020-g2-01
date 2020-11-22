package org.springframework.samples.petclinic.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Cliente;
import org.springframework.samples.petclinic.repository.ClienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClienteService {
	
	private ClienteRepository clienteRepository;	
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthoritiesService authoritiesService;
	
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
	}
	
	@Transactional(readOnly = true)
	public Collection<Cliente> findClientesByApellidos(String apellidos) throws DataAccessException {
		return clienteRepository.findByApellidos(apellidos);
	}
	
	@Transactional
	public void saveCliente(Cliente cliente) throws DataAccessException {
		//creating cliente
		clienteRepository.save(cliente);		
		//creating user
		userService.saveUser(cliente.getUser());
		//creating authorities
		authoritiesService.saveAuthorities(cliente.getUser().getUsername(), "cliente");
	}

	@Transactional(readOnly = true)
	public Cliente findClienteById(int id) throws DataAccessException {
		return clienteRepository.findById(id);
	}

}
