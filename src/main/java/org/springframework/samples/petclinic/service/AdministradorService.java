package org.springframework.samples.petclinic.service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Administrador;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.repository.AdministradorRespository;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedUsernameException;
import org.springframework.samples.petclinic.service.exceptions.InvalidPasswordException;
import org.springframework.samples.petclinic.util.UtilUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdministradorService {
	
	@Autowired
	private AdministradorRespository adminRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthoritiesService authService;
	
	@Transactional
	public void saveAdministrador(Administrador admin) throws DataAccessException, InvalidPasswordException, DuplicatedUsernameException {
		
		Optional<User> user = userService.findUser(admin.getUsuario().getUsername());
		UtilUser.compruebaRestricciones(user, admin, admin.getUsuario().getPassword());
		
		admin.getUsuario().setAuthorities(new ArrayList<>());
		adminRepository.save(admin);
		userService.saveUser(admin.getUsuario());
		authService.saveAuthorities(admin.getUsuario().getUsername(), "admin");
	}
	
	@Transactional
	public Iterable<Administrador> findAll() {
		return adminRepository.findAll();
	}
	
	@Transactional
	public Optional<Administrador> findById(Integer id) {
		return adminRepository.findById(id);
	}
	
	@Transactional
	public void delete(Administrador admin) {
		adminRepository.delete(admin);
	}
	
	@Transactional(readOnly = true)
	public Optional<Administrador> findAdministradorByUsuarioUsername(String username) throws DataAccessException {
		return adminRepository.findAdministradorByUsuarioUsername(username);
	}
	
	
	@Transactional(readOnly = true)
	public Optional<Administrador> findAdministradorByDni(String dni) throws DataAccessException {
		return adminRepository.findByDNI(dni);
	}
	
}
