package org.springframework.samples.petclinic.web;

import java.time.Month;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.BalanceEconomicoMensual;
import org.springframework.samples.petclinic.model.Factura;
import org.springframework.samples.petclinic.model.FacturaRecambio;
import org.springframework.samples.petclinic.service.BalanceEconomicoMensualService;
import org.springframework.samples.petclinic.service.FacturaRecambioService;
import org.springframework.samples.petclinic.service.FacturaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/balanceEconomico")
public class BalanceEconomicoController {
	
	
	@Autowired
	private FacturaService facturaService;
	
	@Autowired
	private FacturaRecambioService fRecambioService;
	
	@Autowired
	private BalanceEconomicoMensualService bemService;
	
	@GetMapping("/sinFiltro")
	public String listadoBalance(@RequestParam(required=false, name="gastos") Boolean gastos, ModelMap model) {
		String vista;		
		if(gastos==null || gastos==false) {
			List<Integer> anyos = facturaService.getAnyosFactura();
			model.addAttribute("anyos", anyos);		
			List<Month> meses = facturaService.getMesesFactura();
			model.addAttribute("meses", meses);
			List<Factura> facturasIngresos = this.facturaService.findAll();
			model.addAttribute("facturas", facturasIngresos);
			vista="/balance/ingresosBalance";
			
		}else {
			List<Integer> anyos = fRecambioService.getAnyosFacturaRecambio();
			model.addAttribute("anyos", anyos);		
			List<Month> meses = fRecambioService.getMesesFacturaRecambio();
			model.addAttribute("meses", meses);
			List<FacturaRecambio> facturasGastos =  this.fRecambioService.findAll();
			model.addAttribute("facturas", facturasGastos);
			vista="/balance/gastosBalance";
		}
		return vista;
	}
	
	@GetMapping(value="/filtradoIngresos")
	public String listadoBalanceIngresosFiltrado(@RequestParam(required=false) Month mes, @RequestParam(required=false) int anyo, ModelMap model) {
		String vista;
		List<Integer> anyos = facturaService.getAnyosFactura();
		model.addAttribute("anyos", anyos);		
		List<Month> meses = facturaService.getMesesFactura();
		model.addAttribute("meses", meses);
		model.addAttribute("mesElegido", mes);
		model.addAttribute("anyoElegido", anyo);
		List<Factura> facturasIngresosFiltradas = this.facturaService.findFacturasMesAnyo(mes, anyo);
		model.addAttribute("facturas", facturasIngresosFiltradas);
		vista="/balance/ingresosBalance";
		return vista;
	}
	
	@GetMapping(value="/filtradoGastos")
	public String listadoBalanceGastosFiltrado(@RequestParam(required=false) Month mes, @RequestParam(required=false) int anyo, ModelMap model) {
		String vista;
		List<Integer> anyos = fRecambioService.getAnyosFacturaRecambio();
		model.addAttribute("anyos", anyos);		
		List<Month> meses = fRecambioService.getMesesFacturaRecambio();
		model.addAttribute("meses", meses);
		model.addAttribute("mesElegido", mes);
		model.addAttribute("anyoElegido", anyo);
		List<FacturaRecambio> facturasGastosFiltradas = this.fRecambioService.findFacturasRecambioMesAnyo(mes, anyo);
		model.addAttribute("facturas", facturasGastosFiltradas);
		vista="/balance/gastosBalance";
		return vista;
	}
	
	@GetMapping()
	public String listadoBalancesTotales(ModelMap model) {
		String vista;
		bemService.cargaDatos();
		List<BalanceEconomicoMensual> balances = this.bemService.findAll();
		List<Integer> anyos = this.bemService.getAnyosConjuntos();
		model.addAttribute("anyos", anyos);
		model.addAttribute("balances", balances);
		vista="/balance/balanceTotal";
		return vista;
	}
	
	@GetMapping("/balanceFiltradoAnyo")
	public String listadoBalancesTotalesFiltrados(@RequestParam(required=false) int anyo, ModelMap model) {
		String vista;
		List<Integer> anyos = this.bemService.getAnyosConjuntos();
		model.addAttribute("anyos", anyos);
		List<BalanceEconomicoMensual> bemFiltrado=this.bemService.findByAnyo(anyo);
		model.addAttribute("balances", bemFiltrado);
		vista="/balance/balanceTotal";
		return vista;
	}
}
