package org.springframework.samples.petclinic.web;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.service.FacturaRecambioService;
import org.springframework.samples.petclinic.service.PedidoRecambioService;
import org.springframework.samples.petclinic.service.ProveedorService;
import org.springframework.samples.petclinic.service.RecambioService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers=PedidoRecambioController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = RecambioFormatter.class),
excludeAutoConfiguration= SecurityConfiguration.class)
class PedidoRecambioControllerTests {

	@MockBean
	private PedidoRecambioService pedidoRecambioService;
	
	@MockBean
	private ProveedorService proveedorService;
	
	@MockBean
	private RecambioService recambioService;
	
	@MockBean
	private FacturaRecambioService facturaRecambioService;
	
	@Autowired
	private MockMvc mockMvc;
	
	@WithMockUser(value = "spring")
    @Test
    void testInitPedidoRecambioForm() throws Exception {
		mockMvc.perform(get("/pedidosRecambio/new"))
		.andExpect(status().isOk()).andExpect(model()
		.attributeExists("pedidosRecambio"))
		.andExpect(view().name("pedidosRecambio/newPedidosRecambio"));
		
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormSuccess() throws Exception {
		mockMvc.perform(post("/pedidosRecambio/save")
				.with(csrf())
				.param("name", "Rueda")
				.param("precioPorUnidad", "10")
				.param("cantidad", "4")
				.param("proveedor", "Pepe"))
				.andExpect(status().isOk());
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormHasErrors() throws Exception {
		mockMvc.perform(post("/pedidosRecambio/save")
				.with(csrf())
				.param("name", "Rueda")
				.param("precioPorUnidad", "10")
				.param("cantidad", "-4")
				.param("proveedor", "Pepe"))
				.andExpect(status().isOk())
				.andExpect(model().attributeHasFieldErrors("pedidoRecambio", "cantidad"))
				.andExpect(view().name("pedidosRecambio/newPedidosRecambio"));
	}
}
