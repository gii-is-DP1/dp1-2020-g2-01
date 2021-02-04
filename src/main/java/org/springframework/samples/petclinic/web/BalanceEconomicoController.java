package org.springframework.samples.petclinic.web;

import java.time.Month;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Factura;
import org.springframework.samples.petclinic.model.FacturaRecambio;
import org.springframework.samples.petclinic.service.FacturaRecambioService;
import org.springframework.samples.petclinic.service.FacturaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BalanceEconomicoController {
	
	
	@Autowired
	private FacturaService facturaService;
	
	@Autowired
	private FacturaRecambioService fRecambioService;
	
	
	@GetMapping(value="/balanceEconomico")
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
			List<FacturaRecambio> facturasGastos = (List<FacturaRecambio>) this.fRecambioService.findAll();
			model.addAttribute("facturas", facturasGastos);
			vista="/balance/gastosBalance";
		}
		return vista;
	}
	
	@GetMapping(value="/balanceEconomico/filtrado?mes={mes}&anyo={anyo}")
	public String listadoBalanceFiltrado(@RequestParam(required=false, name="gastos") Boolean gastos,
			@PathVariable("mes") Month mes, @PathVariable("anyo") int anyo, ModelMap model) {
		String vista;
		if(gastos==null || gastos==false) {
			List<Factura> facturasIngresosFiltradas = this.facturaService.findFacturasMesAnyo(mes, anyo);
			model.addAttribute("facturas", facturasIngresosFiltradas);
			vista="/balance/ingresosBalance";
		}
		else {
			List<FacturaRecambio> facturasGastosFiltradas =  this.fRecambioService.findFacturasRecambioMesAnyo(mes, anyo);
			model.addAttribute("facturas", facturasGastosFiltradas);
			vista="/balance/gastosBalance";
		}
		return vista;
	}

	
}
