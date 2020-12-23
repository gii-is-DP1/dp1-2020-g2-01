package org.springframework.samples.petclinic.web;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.LineaFactura;
import org.springframework.samples.petclinic.service.LineaFacturaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/facturas")
public class FacturaController {

	
	@Autowired
	private LineaFacturaService facturaService;
	
	@GetMapping(value = { "/listadoFacturas" })
	public String listadoFacturas(ModelMap model) {
		String vista = "facturas/listadoFacturas";
		Iterable<LineaFactura> facturas = facturaService.findAll();
		model.put("facturas", facturas);
		return vista;
	}
	

	
}
