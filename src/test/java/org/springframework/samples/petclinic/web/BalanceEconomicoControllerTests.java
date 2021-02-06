package org.springframework.samples.petclinic.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.BalanceEconomicoMensual;
import org.springframework.samples.petclinic.model.Factura;
import org.springframework.samples.petclinic.model.FacturaRecambio;
import org.springframework.samples.petclinic.service.BalanceEconomicoMensualService;
import org.springframework.samples.petclinic.service.FacturaRecambioService;
import org.springframework.samples.petclinic.service.FacturaService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.any;


@WebMvcTest(controllers=BalanceEconomicoController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)
class BalanceEconomicoControllerTests {
	
	
	@MockBean
	private BalanceEconomicoMensualService balanceEconomicoService;
	
	@MockBean
	private FacturaService facturaService;
	
	@MockBean
	private FacturaRecambioService fRecambioService;
	
	@Autowired
	private MockMvc mockMvc;
	
	@BeforeEach
	void setup() {
		//testListadoBalancesTotales
		List<Month> meses = new ArrayList<>();
		List<Integer> years = new ArrayList<>();
		meses.add(Month.JANUARY);
		years.add(2020);
		
		given(this.balanceEconomicoService.getMesesConjuntos()).willReturn(meses);
		given(this.balanceEconomicoService.getAnyosConjuntos()).willReturn(years);
		
		Factura f1 = new Factura();
		f1.setFechaPago(LocalDate.now().minusDays(10));
		List<Factura> listaFactura = new ArrayList<>();
		listaFactura.add(f1);
		
		FacturaRecambio f2 = new FacturaRecambio();
		f2.setFechaPago(LocalDate.now().minusDays(10));
		List<FacturaRecambio> listaFacturaR = new ArrayList<>();
		listaFacturaR.add(f2);
		
		given(this.facturaService.findFacturasMesAnyo(any(Month.class), any(Integer.class))).willReturn(listaFactura);
		given(this.fRecambioService.findFacturasRecambioMesAnyo(any(Month.class), any(Integer.class))).willReturn(listaFacturaR);
		
		
		
		BalanceEconomicoMensual b = new BalanceEconomicoMensual();
		b.setId(1);
		b.setAnyo(2020);
		b.setMes(Month.JANUARY);
		b.setFactura(listaFactura);
		b.setFacturaRecambio(listaFacturaR);
		List<BalanceEconomicoMensual> listaBalance = new ArrayList<>();
		listaBalance.add(b);
		
		given(this.balanceEconomicoService.findAll()).willReturn(listaBalance);
		
		
		//testListadoBalanceIngresosFiltrado
		given(this.facturaService.getAnyosFactura()).willReturn(years);
		given(this.facturaService.getMesesFactura()).willReturn(meses);
		given(this.facturaService.findFacturasMesAnyo(any(Month.class), any(Integer.class))).willReturn(listaFactura);
		
		
		//testListadoBalanceGastosFiltrado
		given(this.fRecambioService.getAnyosFacturaRecambio()).willReturn(years);
		given(this.fRecambioService.getMesesFacturaRecambio()).willReturn(meses);
		given(this.fRecambioService.findFacturasRecambioMesAnyo(any(Month.class), any(Integer.class))).willReturn(listaFacturaR);
		
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testListadoBalancesTotales() throws Exception {
		mockMvc.perform(get("/balanceEconomico"))
		.andExpect(status().is2xxSuccessful())
		.andExpect(model().attributeExists("balances"))
		.andExpect(model().attributeExists("anyos"))
		.andExpect(view().name("/balance/balanceTotal"));
		
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testListadoBalanceIngresosFiltrado() throws Exception {
		mockMvc.perform(get("/balanceEconomico/filtradoIngresos?mes=JANUARY&anyo=2020"))
		.andExpect(status().is2xxSuccessful())
		.andExpect(model().attributeExists("facturas"))
		.andExpect(model().attributeExists("meses"))
		.andExpect(model().attributeExists("anyos"))
		.andExpect(model().attributeExists("mesElegido"))
		.andExpect(model().attributeExists("anyoElegido"))
		.andExpect(view().name("/balance/ingresosBalance"));
		
	}
	
	
	@WithMockUser(value = "spring")
	@Test
	void testListadoBalanceGastosFiltrado() throws Exception {
		mockMvc.perform(get("/balanceEconomico/filtradoGastos?mes=JANUARY&anyo=2020"))
		.andExpect(status().is2xxSuccessful())
		.andExpect(model().attributeExists("facturas"))
		.andExpect(model().attributeExists("meses"))
		.andExpect(model().attributeExists("anyos"))
		.andExpect(model().attributeExists("mesElegido"))
		.andExpect(model().attributeExists("anyoElegido"))
		.andExpect(view().name("/balance/gastosBalance"));
		
	}
	
	
	@WithMockUser(value = "spring")
	@Test
	void testListadoBalanceGastosFalse() throws Exception {
		mockMvc.perform(get("/balanceEconomico/sinFiltro?gastos=false"))
		.andExpect(status().is2xxSuccessful())
		.andExpect(model().attributeExists("facturas"))
		.andExpect(model().attributeExists("meses"))
		.andExpect(model().attributeExists("anyos"))
		.andExpect(view().name("/balance/ingresosBalance"));
		
	}
	
	
	@WithMockUser(value = "spring")
	@Test
	void testListadoBalanceGastosTrue() throws Exception {
		mockMvc.perform(get("/balanceEconomico/sinFiltro?gastos=true"))
		.andExpect(status().is2xxSuccessful())
		.andExpect(model().attributeExists("facturas"))
		.andExpect(model().attributeExists("meses"))
		.andExpect(model().attributeExists("anyos"))
		.andExpect(view().name("/balance/gastosBalance"));
		
	}
	
	
	@WithMockUser(value = "spring")
	@Test
	void testListadoBalanceTotalesFiltrados() throws Exception {
		mockMvc.perform(get("/balanceEconomico/balanceFiltradoAnyo?anyo=2020"))
		.andExpect(status().is2xxSuccessful())
		.andExpect(model().attributeExists("balances"))
		.andExpect(model().attributeExists("anyos"))
		.andExpect(view().name("/balance/balanceTotal"));
		
	}
	

}
