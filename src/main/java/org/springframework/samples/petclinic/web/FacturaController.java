package org.springframework.samples.petclinic.web;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Factura;
import org.springframework.samples.petclinic.model.LineaFactura;
import org.springframework.samples.petclinic.model.Reparacion;
import org.springframework.samples.petclinic.service.FacturaService;
import org.springframework.samples.petclinic.service.LineaFacturaService;
import org.springframework.samples.petclinic.service.ReparacionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/facturas")
public class FacturaController {


	@Autowired
	private FacturaService facturaService;
	
	@Autowired
	private ReparacionService reparacionService;
	
	@Autowired
	private LineaFacturaService lineaFacturaService;


	@GetMapping(value = { "/listadoFacturas" })
	public String listadoFacturas(ModelMap model) {
		String vista = "facturas/listadoFacturas";
		Iterable<Factura> facturas = facturaService.findAll();
		model.put("facturas", facturas);
		return vista;
	}
	
	@PostMapping(value="/generar/{reparacionId}")
	public String processFinalizarReparacion(@PathVariable("reparacionId") int id, @Valid Factura factura, ModelMap model) {
		String vista = "";
		try {
			facturaService.saveFactura(factura);

			for(LineaFactura l: factura.getLineaFactura()) {
				l.setFactura(factura);
				lineaFacturaService.saveLineaFactura(l);
			}
			model.addAttribute("message", "Factura "+factura.getId()+" generada correctamente");

		}catch(Exception e){
			log.warn("Excepción: error inesperado al finalizar la reparacion con id " + id );
			model.addAttribute("message", "Error inesperado");
			model.addAttribute("messageType", "danger");
		}
		
		vista=listadoFacturas(model);
	
		return vista;
	}
	
	@GetMapping(value="/generar/{reparacionId}")
	public String initFinalizarReparacion(@PathVariable("reparacionId") int id, ModelMap model) {
		Reparacion reparacion = reparacionService.findReparacionById(id).get();
		List<LineaFactura> lineaFactura = reparacion.getLineaFactura();
		Factura factura = new Factura();
		factura.setDescuento(0);
		factura.setFechaPago(LocalDate.now());
		factura.setLineaFactura(lineaFactura);
		model.addAttribute("factura",factura);
		model.addAttribute("reparacion", reparacion);
		return "facturas/generar_factura";
	}



}