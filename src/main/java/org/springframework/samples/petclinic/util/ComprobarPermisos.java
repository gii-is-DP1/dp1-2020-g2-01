package org.springframework.samples.petclinic.util;


import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

public class ComprobarPermisos {
	
	public static Boolean tieneRol(String rol) {
		  Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();
		  Boolean tieneRol = false;
		  for (GrantedAuthority authority : authorities) {
		     tieneRol = authority.getAuthority().equals(rol);
		     if (tieneRol) {
		      break;
		     }
		  }
		  return tieneRol;
		}

}
