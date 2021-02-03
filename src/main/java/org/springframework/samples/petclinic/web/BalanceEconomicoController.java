package org.springframework.samples.petclinic.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Factura;
import org.springframework.samples.petclinic.model.FacturaRecambio;
import org.springframework.samples.petclinic.service.FacturaRecambioService;
import org.springframework.samples.petclinic.service.FacturaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
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
			List<Factura> facturasIngresos = (List<Factura>) this.facturaService.findAll();
			model.addAttribute("facturas", facturasIngresos);
			vista="/balance/ingresos";
//			int i = 0;
//			while(facturas.size()>i) {
//				Factura factura = facturas.get(i);
//				model.addAttribute("factura", factura);
//				i++;
//			}
		}else {
			List<FacturaRecambio> facturasGastos = (List<FacturaRecambio>) this.fRecambioService.findAll();
			model.addAttribute("facturas", facturasGastos);
			vista="/balance/gastos";
		}
		return vista;
	}

	
}
