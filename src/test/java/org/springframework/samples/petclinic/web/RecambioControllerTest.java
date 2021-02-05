package org.springframework.samples.petclinic.web;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
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
import org.springframework.samples.petclinic.model.Recambio;
import org.springframework.samples.petclinic.model.Reparacion;
import org.springframework.samples.petclinic.model.Solicitud;
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
includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = RecambioFormatter.class),
excludeAutoConfiguration= SecurityConfiguration.class)
class RecambioControllerTest {
	
	
	private static final int TEST_SOLICITUD_ID = 1;
	private static final int TEST_REPARACION_ID = 1;


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
		
		Solicitud s = new Solicitud();
		Recambio r = new Recambio();
		Reparacion rep = new Reparacion();
		s.setRecambio(r);
		given(this.reparacionService.findReparacionById(TEST_REPARACION_ID)).willReturn(Optional.of(rep));
		given(this.recambioService.findRecambioByName("Prueba")).willReturn(Optional.of(r));
		given(this.solicitudService.findById(TEST_SOLICITUD_ID)).willReturn(Optional.of(s));

	}
	
	@WithMockUser(value = "spring")
    @Test
    void testInitRecambioForm() throws Exception {
		mockMvc.perform(get("/recambios/solicitud/new")).andExpect(status().isOk()).andExpect(model().attributeExists("solicitud"))
			.andExpect(view().name("recambios/editSolicitud"));
}
	
	@WithMockUser(value = "spring")
    @Test
    void testProcessCreationFormSuccess() throws Exception {
		mockMvc.perform(post("/recambios/solicitud/save")
				.with(csrf())
				.param("terminada", "false")
				.param("recambio", "Prueba")
				.param("cantidad", "4")
				.param("empleado", "Prueba, Test")
				.param("reparacion", "1"))
				.andExpect(status().isOk());

	}
	
	
	
	@WithMockUser(value = "spring")
	@Test
	void testRecambioFormHasErrors() throws Exception {
		
		mockMvc.perform(post("/recambios/solicitud/save")
				.with(csrf())
				.param("terminada", "false")
				.param("recambio", "Prueba")
				.param("cantidad", "-4")
				.param("empleado", "Prueba, Test")
				.param("reparacion", "1"))
				.andExpect(status().isOk())
				.andExpect(model().attributeHasFieldErrors("solicitud", "cantidad"))
				.andExpect(view().name("recambios/editSolicitud"));

	}

}
