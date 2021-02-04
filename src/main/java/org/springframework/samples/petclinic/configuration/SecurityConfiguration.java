package org.springframework.samples.petclinic.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @author japarejo
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	DataSource dataSource;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/resources/**","/webjars/**","/h2-console/**").permitAll()
				.antMatchers(HttpMethod.GET, "/","/oups").permitAll()
				.antMatchers("/users/new").permitAll()
				.antMatchers("/reparaciones/**").authenticated()
				.antMatchers("/reparaciones/listadoReparaciones/**").hasAnyAuthority("admin", "cliente", "empleado")
				.antMatchers("/vehiculos/listadoVehiculos").hasAnyAuthority("admin", "cliente", "empleado")
				.antMatchers("/vehiculos/**").hasAnyAuthority("admin", "cliente", "empleado")
				.antMatchers("/empleados/listadoEmpleados").hasAnyAuthority("admin", "empleado")
				.antMatchers("/empleados/**").permitAll()
				.antMatchers("/talleres/listadoTalleres").permitAll()
				.antMatchers("/talleres/**").permitAll()
				.antMatchers("/pedidosRecambio/**").hasAnyAuthority("admin")
				.antMatchers("/proveedores/**").authenticated()
				.antMatchers("/proveedores/**").hasAnyAuthority("admin")
				.antMatchers("/clientes/listadoClientes").hasAnyAuthority("admin", "empleado")
				.antMatchers("/clientes/find").hasAnyAuthority("admin", "empleado")
				.antMatchers("/clientes/new").permitAll()
				.antMatchers("/clientes/delete/*").hasAnyAuthority("admin", "empleado")
				.antMatchers("/clientes/deleteCliente/*").hasAnyAuthority("admin", "empleado")
				.antMatchers("/clientes/clienteDetails/*").hasAnyAuthority("cliente","admin", "empleado")
				.antMatchers("/clientes/clienteDetails").hasAnyAuthority("cliente","admin", "empleado")
				.antMatchers("/clientes/update/*").hasAnyAuthority("cliente", "empleado", "admin")
				.antMatchers("/reparacionesComunes/**").permitAll()
				.antMatchers("/citas").hasAnyAuthority("cliente","admin", "empleado")
				.antMatchers("/citas/listadoCitas").hasAnyAuthority("cliente","admin", "empleado")
				.antMatchers("/citas/listadoCitas/**").hasAnyAuthority("cliente","admin", "empleado")
				.antMatchers("/citas/new").hasAnyAuthority("cliente", "empleado")
				.antMatchers("/citas/new/**").hasAnyAuthority("admin", "empleado")
				.antMatchers("/citas/**").hasAnyAuthority("cliente","admin", "empleado")
				.antMatchers("/facturas/**").permitAll()
				.antMatchers("/facturas/generarPDF/*").permitAll()
				.antMatchers("/reparacionesComunes/**").permitAll()
				.antMatchers("/recambios/**").hasAnyAuthority("admin", "empleado")
				.antMatchers("/listadoRecambios/**").hasAnyAuthority("admin", "empleado")
				.antMatchers("/solicitud/**").hasAnyAuthority("empleado", "admin")
				.antMatchers("/admin/**").hasAnyAuthority("admin")
				.anyRequest().denyAll()
				.and()
				 	.formLogin()
				 	/*.loginPage("/login")*/
				 	.failureUrl("/login-error")
				.and()
					.logout()
						.logoutSuccessUrl("/"); 
                // Configuración para que funcione la consola de administración 
                // de la BD H2 (deshabilitar las cabeceras de protección contra
                // ataques de tipo csrf y habilitar los framesets si su contenido
                // se sirve desde esta misma página.
                http.csrf().ignoringAntMatchers("/h2-console/**");
                http.headers().frameOptions().sameOrigin();
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication()
	      .dataSource(dataSource)
	      .usersByUsernameQuery(
	       "select username,password,enabled "
	        + "from users "
	        + "where username = ?")
	      .authoritiesByUsernameQuery(
	       "select username, authority "
	        + "from authorities "
	        + "where username = ?")	      	      
	      .passwordEncoder(passwordEncoder());	
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() 
	{
	    return new BCryptPasswordEncoder();
	}
	
}


