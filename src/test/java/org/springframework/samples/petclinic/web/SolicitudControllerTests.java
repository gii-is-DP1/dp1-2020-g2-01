package org.springframework.samples.petclinic.web;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Empleado;
import org.springframework.samples.petclinic.service.EmpleadoService;
import org.springframework.samples.petclinic.service.PedidoRecambioService;
import org.springframework.samples.petclinic.service.RecambioService;
import org.springframework.samples.petclinic.service.ReparacionService;
import org.springframework.samples.petclinic.service.SolicitudService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;



@WebMvcTest(controllers=RecambioController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)
class SolicitudControllerTests {
	
	@Autowired
	private RecambioController recambioController;
	
	@MockBean
	private SolicitudService solicitudService;
	
	@MockBean
	private EmpleadoService empleadoService;
	
	@MockBean
	private RecambioService recambioService;
	
	@MockBean
	private PedidoRecambioService pedidoRecambioService;
	
	@MockBean
	private ReparacionService reparacionService;
	
	@Autowired
	private MockMvc mockMvc;
		
	@BeforeEach
	void setup() {
		
		Empleado e = new Empleado();
		e.setNombre("spring");
		Optional<Empleado> opt = Optional.of(e);
		given(this.empleadoService.findEmpleadoByUsuarioUsername("spring")).willReturn(opt);

	}
	
	@WithMockUser(value = "spring")
	@Test
	void testImpedirAccesoEmpleado() throws Exception {
		mockMvc.perform(get("/recambios/listadoRecambiosSolicitados"))
		.andExpect(status().is2xxSuccessful()).andExpect(view().name("welcome"));
	}
	
	

}
