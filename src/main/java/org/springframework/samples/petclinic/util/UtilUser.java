package org.springframework.samples.petclinic.util;

import java.util.Optional;
import java.util.regex.Pattern;

import org.springframework.samples.petclinic.model.Persona;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedUsernameException;
import org.springframework.samples.petclinic.service.exceptions.InvalidPasswordException;

public class UtilUser {

	public static void compruebaRestricciones(Optional<User> user, Persona persona, String password) throws DuplicatedUsernameException, InvalidPasswordException {
		if(user.isPresent() && persona.getId() == null) {
			throw new DuplicatedUsernameException();
		}
		String regex = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,20}$";
		if (!Pattern.matches(regex, password)) {
			throw new InvalidPasswordException();
		}
	}
}
