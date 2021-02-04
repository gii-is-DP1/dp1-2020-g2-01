package org.springframework.samples.petclinic.web;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

	@GetMapping(value="/info/{facturaId}")
	public String getInfo(@PathVariable("facturaId") int id, ModelMap model) {
		String vista = "facturas/details";
		Optional<Factura> factura = facturaService.findFacturaById(id);
		
		if(!factura.isPresent()) {
			model.addAttribute("message", "Factura no encontrada");
			model.addAttribute("messageType", "warning");
			return listadoFacturas(model);
		}
		
		model.addAttribute("factura", factura.get());
		
		return vista;
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/generarPDF/{facturaId}")
	public ResponseEntity generarPDF(@PathVariable("facturaId") int id, ModelMap model) throws FileNotFoundException, IOException {
		Factura factura = facturaService.findFacturaById(id).get();
		
		String archivo = facturaService.generarPDF(factura);
		Path path = Paths.get(archivo);
		Resource resource = null;
		try {
			resource = new UrlResource(path.toUri());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		ResponseEntity res =  ResponseEntity.ok()
				.contentType(MediaType.parseMediaType(MediaType.APPLICATION_PDF_VALUE))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
//		new File(archivo).delete();
		return res;
	}
	
	


}