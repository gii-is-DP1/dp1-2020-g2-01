/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.service;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sun.tools.sjavac.Log;

import lombok.extern.slf4j.Slf4j;

/**
 * Mostly used as a facade for all Petclinic controllers Also a placeholder
 * for @Transactional and @Cacheable annotations
 *
 * @author Michael Isvy
 */
@Slf4j
@Service
public class UserService {

	//si se hace Autowired salta error en las pruebas porque no se importa SecurityConfiguration.java con DataJpaTest
	private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	private UserRepository userRepository;
	
	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Transactional
	public void saveUser(User user) throws DataAccessException {
		user.setEnabled(true);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepository.save(user);
		log.info("Usuario creado");
	}
	
	@Transactional(readOnly = true)
	public Optional<User> findUser(String username) {
		return userRepository.findById(username);
	}
	
	@Transactional
	public void delete(User user) {
		userRepository.delete(user);
		log.info("Usuario con nombre " + user.getUsername() + " borrado");
	}
}
