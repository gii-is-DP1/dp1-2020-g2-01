package org.springframework.samples.petclinic.util;

import org.springframework.security.core.context.SecurityContextHolder;

public class LoggedUser {
	public static String getUsername() {
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}
}
